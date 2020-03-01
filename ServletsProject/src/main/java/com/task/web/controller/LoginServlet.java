package main.java.com.task.web.controller;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean page = Boolean.parseBoolean(request.getParameter("click"));
        final HttpSession session = request.getSession();

        String role = (String) session.getAttribute("role");
        if (role != null) {
            if ("EMPLOYEES".equals(role.toUpperCase())) {
                request.getRequestDispatcher("/views/employee/employeeView.html").include(request, response);
            } else {
                request.getRequestDispatcher("/views/patient/patientView.html").include(request, response);
            }
        } else if (page) {
            request.getRequestDispatcher("/LoginPage.html").include(request, response);
        } else {
            request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }
}
