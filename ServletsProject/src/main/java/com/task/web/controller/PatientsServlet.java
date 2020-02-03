package main.java.com.task.web.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonParser;
import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import com.google.gson.Gson;
import main.java.com.task.model.person.Patient;
import main.java.com.task.model.therapy.Diagnose;
import main.java.com.task.model.therapy.Medicament;
import main.java.com.task.model.therapy.Operation;
import main.java.com.task.model.therapy.Procedure;

import java.io.IOException;

@WebServlet("/patients")
public class PatientsServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String idString = request.getParameter("id");
        if (idString != null) {
            try {
                int id = Integer.parseInt(idString);
                Patient p = factory.getPatientDao().getById(id);
                response.getWriter().write(gson.toJson(p));
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.getWriter().write(gson.toJson(factory.getPatientDao().select()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String idString = request.getParameter("id");
        String status = request.getParameter("status");
        int id = Integer.parseInt(idString);
        /*
        JsonParser.parseString("").getAsJsonObject()
        .get("").getAsBoolean();*/

        if (status != null) {
            response.getWriter().write(gson.toJson(factory.getPatientDao().delete(id)));
        } else {
            int operation = Integer.parseInt(request.getParameter("operation"));
            int diagnose = Integer.parseInt(request.getParameter("diagnose"));
            int medicament = Integer.parseInt(request.getParameter("medicament"));
            int procedure = Integer.parseInt(request.getParameter("procedure"));

            Patient patient = factory.getPatientDao().getById(id);
            patient.setOperation((Operation) factory.getTherapyDao().getById(operation, "operation"));
            patient.setProcedure((Procedure) factory.getTherapyDao().getById(procedure, "procedure"));
            patient.setDiagnose((Diagnose) factory.getTherapyDao().getById(diagnose, "diagnose"));
            patient.setMedicament((Medicament) factory.getTherapyDao().getById(medicament, "medicament"));

            response.getWriter().write(gson.toJson(factory.getPatientDao().update(patient)));
        }

    }
}