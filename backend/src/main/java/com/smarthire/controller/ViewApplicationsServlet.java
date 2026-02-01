package com.smarthire.controller;

import com.smarthire.dao.ApplicationDAO;
import com.smarthire.model.ApplicationView;
import com.smarthire.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewApplications")
public class ViewApplicationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Check if user is logged in
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // 2. Get logged-in user and verify it's a recruiter
        User recruiter = (User) session.getAttribute("user");
        
        if (!"RECRUITER".equals(recruiter.getRole())) {
            response.sendRedirect("login.html");
            return;
        }

        // 3. Fetch all applications for this recruiter's jobs
        ApplicationDAO applicationDAO = new ApplicationDAO();
        List<ApplicationView> applications = applicationDAO.getApplicationViewsByRecruiter(recruiter.getUserId());

        // 4. Store in request scope
        request.setAttribute("applications", applications);

        // 5. Forward to JSP
        request.getRequestDispatcher("view-applications.jsp").forward(request, response);
    }
}
