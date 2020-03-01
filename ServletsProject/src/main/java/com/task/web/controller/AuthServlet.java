package main.java.com.task.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import main.java.com.task.model.person.MedicalEmployee;
import main.java.com.task.model.person.Patient;
import main.java.com.task.model.person.User;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();
    JsonParser parser = new JsonParser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //auth(request, response);
    }
/*
    private void auth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject object = parser.parse(request.getReader().readLine()).getAsJsonObject();

        String login = object.get("login").getAsString();
        String password = object.get("password").getAsString();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            password = sb.toString();
            User user = factory.getUserDao().authentication(login, password);

            switch (user.getPosition().toString().toUpperCase()) {
                case "EMPLOYEE":
                    MedicalEmployee employee = factory.getEmployeeDao().login(login, password);
                    response.getWriter().write(gson.toJson(employee));
                    break;
                case "PATIENT":
                    Patient patient = factory.getPatientDao().login(login, password);
                    response.getWriter().write(gson.toJson(patient));
                    break;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }*/
}
