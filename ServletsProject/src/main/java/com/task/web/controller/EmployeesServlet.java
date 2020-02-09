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
import org.json.JSONObject;

import java.io.IOException;

@WebServlet("/employees")
public class EmployeesServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean page = Boolean.parseBoolean(request.getParameter("click"));
        if (page) {
            request.getRequestDispatcher("/views/employee/EmployeeList.html").include(request, response);
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String line = request.getReader().readLine();;
        if (line != null && line.length() > 0) {
            JsonObject object = JsonParser.parseString(line).getAsJsonObject();
            JsonElement idString = object.get("id");
            if (idString != null) {
                try {
                    int id = idString.getAsInt();
                    response.getWriter().write(gson.toJson(factory.getEmployeeDao().getById(id)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            response.getWriter().write(gson.toJson(factory.getEmployeeDao().select()));
        }
    }
}
