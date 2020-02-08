package main.java.com.task.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import main.java.com.task.model.person.MedicalEmployee;
import main.java.com.task.model.person.Patient;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {

    private static boolean is = true;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (is) {
            is = false;
            BaseFactory factory = new MySqlDaoFactory();

            for (Patient patient : factory.getPatientDao().select()) {
                pa
            }

        }

        boolean page = Boolean.parseBoolean(request.getParameter("click"));
        if (page) {
            request.getRequestDispatcher("/LoginPage.html").include(request, response);
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }
}
