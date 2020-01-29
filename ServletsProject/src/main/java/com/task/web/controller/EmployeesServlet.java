package main.java.com.task.web.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import com.google.gson.Gson;

import java.io.IOException;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String idString = request.getParameter("id");

        if (idString != null) {
            try {
                int id = Integer.parseInt(idString);
                response.getWriter().write(gson.toJson(factory.getEmployeeDao().getById(id)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            response.getWriter().write(gson.toJson(factory.getEmployeeDao().select()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
