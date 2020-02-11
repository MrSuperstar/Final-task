package main.java.com.task.web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean page = Boolean.parseBoolean(request.getParameter("click"));
        if (page) {
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
            if (idString != null) {
                try {
                    int id = idString.getAsInt();
                    response.getWriter().write(gson.toJson(factory.getPatientDao().getById(id)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            response.getWriter().write(gson.toJson(factory.getPatientDao().select()));
        }
    }
/*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String idString = request.getParameter("id");
        String status = request.getParameter("status");
        int id = Integer.parseInt(idString);

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

    }*/
}