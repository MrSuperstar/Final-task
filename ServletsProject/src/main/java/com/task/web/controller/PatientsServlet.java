package main.java.com.task.web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoCrudEmployee;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import com.google.gson.Gson;
import main.java.com.task.model.person.Patient;
import main.java.com.task.model.therapy.Diagnose;
import main.java.com.task.model.therapy.Medicament;
import main.java.com.task.model.therapy.Operation;
import main.java.com.task.model.therapy.Procedure;
import org.apache.log4j.Logger;

import java.io.IOException;

@WebServlet("/patients")
public class PatientsServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(PatientsServlet.class);
    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean page = Boolean.parseBoolean(request.getParameter("click"));
        final HttpSession session = request.getSession();

        if (page || session.getAttribute("role") != null) {
            request.getRequestDispatcher("/views/patient/patientView.html").include(request, response);
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String line = request.getReader().readLine();
        if (line != null && line.length() > 0) {
            JsonObject object = JsonParser.parseString(line).getAsJsonObject();
            JsonElement idString = object.get("id");
            JsonElement userIdString = object.get("userId");
            JsonElement statusString = object.get("status");
            JsonElement healing = object.get("healing");

            if (statusString != null && idString != null) {
                discharge(idString, response);
            } else if (healing != null) {
                treatment(object, idString.getAsInt(), response);
            } else if (idString != null) {
                initPatient(idString, response);
            } else if (userIdString != null) {
                initUser(userIdString, response);

            }
        } else {
            response.getWriter().write(gson.toJson(factory.getPatientDao().select()));
        }
    }

    private void discharge(JsonElement idString, HttpServletResponse response) {
        try {
            int id = idString.getAsInt();
            response.getWriter().write(gson.toJson(factory.getPatientDao().delete(id)));
        } catch (NumberFormatException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void initPatient(JsonElement idString, HttpServletResponse response) {
        try {
            int id = idString.getAsInt();
            response.getWriter().write(gson.toJson(factory.getPatientDao().getById(id)));
        } catch (NumberFormatException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void initUser(JsonElement idString, HttpServletResponse response) {
        try {
            int id = idString.getAsInt();
            response.getWriter().write(gson.toJson(factory.getPatientDao().getPatientByUserId(id)));
        } catch (NumberFormatException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void treatment(JsonObject object, int id, HttpServletResponse response) throws IOException {
        JsonElement operation = object.get("operation");
        JsonElement diagnose = object.get("diagnose");
        JsonElement medicament =  object.get("medicament");
        JsonElement procedure = object.get("procedure");

        Patient patient = factory.getPatientDao().getById(id);
        patient.setOperation((Operation) factory.getTherapyDao().getById(operation.getAsInt(), "operation"));
        patient.setProcedure((Procedure) factory.getTherapyDao().getById(procedure.getAsInt(), "procedure"));
        patient.setDiagnose((Diagnose) factory.getTherapyDao().getById(diagnose.getAsInt(), "diagnose"));
        patient.setMedicament((Medicament) factory.getTherapyDao().getById(medicament.getAsInt(), "medicament"));

        response.getWriter().write(gson.toJson(factory.getPatientDao().update(patient)));
    }
}