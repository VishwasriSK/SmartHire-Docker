package com.smarthire.controller;

import com.smarthire.dao.UserDAO;
import com.smarthire.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // 2. Validate input
        if (name == null || email == null || password == null || role == null ||
            name.trim().isEmpty() || email.trim().isEmpty() || 
            password.trim().isEmpty() || role.trim().isEmpty()) {
            response.sendRedirect("register.html?error=invalid");
            return;
        }

        // 3. Validate role (security check)
        if (!("CANDIDATE".equals(role) || "RECRUITER".equals(role))) {
            response.sendRedirect("register.html?error=invalid");
            return;
        }

        // 4. Check if email already exists
        UserDAO userDAO = new UserDAO();
        if (userDAO.emailExists(email)) {
            response.sendRedirect("register.html?error=duplicate");
            return;
        }

        // 5. Create new user
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);

        // 6. Register user in database
        boolean success = userDAO.registerUser(newUser);

        // 7. Redirect based on result
        if (success) {
            response.sendRedirect("login.html?registered=1");
        } else {
            response.sendRedirect("register.html?error=failed");
        }
    }
}
