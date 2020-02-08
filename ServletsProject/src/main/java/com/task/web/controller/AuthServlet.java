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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/auth")
public class AuthServlet  extends HttpServlet {

    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();
    JsonParser parser = new JsonParser();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        auth(request, response);
    }

    private void auth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject object = parser.parse(request.getReader().readLine()).getAsJsonObject();

        String login = object.get("login").getAsString();
        String password = object.get("password").getAsString();
        String status = object.get("status").getAsString();

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            if ("EMPLOYEE".equals(status.toUpperCase())) {
                MedicalEmployee employee = factory.getEmployeeDao().login(login, password);
                response.getWriter().write(gson.toJson(employee));
            }
            else {
                Patient patient = factory.getPatientDao().login(login, password);
                response.getWriter().write(gson.toJson(patient));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private boolean isAuth(HttpServletRequest request, HttpServletResponse response, String cookieName)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;

        if(cookies !=null) {
            for(Cookie c: cookies) {
                if(cookieName.equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }
        }

        return cookie != null;
    }
}
