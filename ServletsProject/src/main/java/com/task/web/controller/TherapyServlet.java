package main.java.com.task.web.controller;

import com.google.gson.Gson;
import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.mysqldao.MySqlDaoFactory;
import main.java.com.task.model.therapy.Therapy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/therapies")
public class TherapyServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final BaseFactory factory = new MySqlDaoFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String therapyName = request.getParameter("therapy");
        if (therapyName != null) {
            Collection<Therapy> therapies = factory.getTherapyDao().select(therapyName);
            response.getWriter().write(gson.toJson(therapies));
        }
    }
}
