package com.smarthire.controller;

import com.smarthire.dao.ApplicationDAO;
import com.smarthire.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/updateApplicationStatus")
public class UpdateApplicationStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Check if user is logged in
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // 2. Verify user is a recruiter
        User recruiter = (User) session.getAttribute("user");
        
        if (!"RECRUITER".equals(recruiter.getRole())) {
            response.sendRedirect("login.html");
            return;
        }

        // 3. Get parameters from URL
        String appIdParam = request.getParameter("appId");
        String status = request.getParameter("status");

        // 4. Validate parameters
        if (appIdParam == null || status == null || appIdParam.isEmpty() || status.isEmpty()) {
            response.sendRedirect("viewApplications?error=invalid");
            return;
        }

        // 5. Validate status value (only allow SHORTLISTED or REJECTED)
        if (!("SHORTLISTED".equals(status) || "REJECTED".equals(status))) {
            response.sendRedirect("viewApplications?error=invalid");
            return;
        }

        int appId = Integer.parseInt(appIdParam);

        // 6. Update status in database
        ApplicationDAO applicationDAO = new ApplicationDAO();
        boolean success = applicationDAO.updateApplicationStatus(appId, status);

        // 7. Redirect back with result
        if (success) {
            response.sendRedirect("viewApplications?success=1");
        } else {
            response.sendRedirect("viewApplications?error=failed");
        }
    }
}
