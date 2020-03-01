package main.java.com.task.web.filter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import main.java.com.task.model.person.MedicalEmployee;
import main.java.com.task.model.person.Patient;
import main.java.com.task.model.person.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginFilter implements Filter {
    BaseFactory factory = new MySqlDaoFactory();
    private final Gson gson = new Gson();
    JsonParser parser = new JsonParser();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;
        final HttpSession session = req.getSession();

        if (session != null &&
                session.getAttribute("login") != null &&
                session.getAttribute("password") != null) {
            final String role = (String) session.getAttribute("role");

            //navigate(req, res, role);
        } else {
            auth(req, res);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void navigate(HttpServletRequest req, HttpServletResponse res, String role) throws ServletException, IOException {
        String path = req.getContextPath() + "/" + role;
        res.sendRedirect(path);
    }

    @Override
    public void destroy() {

    }

    private void auth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject object = parser.parse(request.getReader().readLine()).getAsJsonObject();

        String login = object.get("login").getAsString();

        try {
            String password = encode(object.get("password").getAsString());
            User user = factory.getUserDao().authentication(login, password);

            if (user == null) {
                navigate(request, response, "/login");
            }
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("password", password);

            switch (user.getPosition().toString().toUpperCase()) {
                case "EMPLOYEE":
                    request.getSession().setAttribute("role", "employees");
                    MedicalEmployee employee = factory.getEmployeeDao().login(login, password);
                    response.getWriter().write(gson.toJson(employee));
                    break;
                case "PATIENT":
                    request.getSession().setAttribute("role", "patients");
                    Patient patient = factory.getPatientDao().login(login, password);
                    response.getWriter().write(gson.toJson(patient));
                    break;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private String encode(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
