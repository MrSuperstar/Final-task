package main.java.com.task.web.controller;

import com.google.gson.Gson;
import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import main.java.com.task.model.person.MedicalEmployee;
import main.java.com.task.model.person.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet  extends HttpServlet {

    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String status = request.getParameter("status");

        if ("EMPLOYEE".equals(status.toUpperCase())) {
            MedicalEmployee employee = factory.getEmployeeDao().login(login, password);
            response.getWriter().write(gson.toJson(employee));
        }
        else {
            Patient patient = factory.getPatientDao().login(login, password);
            response.getWriter().write(gson.toJson(patient));
        }
    }
}
