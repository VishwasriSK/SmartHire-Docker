package com.smarthire.controller;

import com.smarthire.dao.UserDAO;
import com.smarthire.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get email and password from form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Call DAO to check credentials
        UserDAO dao = new UserDAO();
        User user = dao.validateUser(email, password);

        // 3. Check if user exists
        if (user != null) {
            // Login success - create session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on role
            if ("RECRUITER".equals(user.getRole())) {
                response.sendRedirect("recruiter-dashboard.html");
            } else {
                response.sendRedirect("candidate-dashboard.html");
            }
        } else {
            // Login failed - redirect back with error
            response.sendRedirect("login.html?error=1");
        }
    }
}
