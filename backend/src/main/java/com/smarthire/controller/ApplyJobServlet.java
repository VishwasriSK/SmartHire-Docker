package com.smarthire.controller;

import com.smarthire.dao.ApplicationDAO;
import com.smarthire.model.Application;
import com.smarthire.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/applyJob")
public class ApplyJobServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Check if user is logged in
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // 2. Get logged-in user
        User candidate = (User) session.getAttribute("user");

        // 3. Verify the user is actually a candidate (not recruiter)
        if (!"CANDIDATE".equals(candidate.getRole())) {
            response.sendRedirect("login.html");
            return;
        }

        // 4. Get job ID from request parameter
        String jobIdParam = request.getParameter("jobId");
        
        if (jobIdParam == null || jobIdParam.isEmpty()) {
            response.sendRedirect("viewJobs?error=invalid");
            return;
        }

        int jobId = Integer.parseInt(jobIdParam);
        int candidateId = candidate.getUserId();

        // 5. Check if candidate has already applied for this job
        ApplicationDAO applicationDAO = new ApplicationDAO();
        
        if (applicationDAO.hasAlreadyApplied(jobId, candidateId)) {
            // Already applied - redirect with error message
            response.sendRedirect("viewJobs?error=duplicate");
            return;
        }

        // 6. Create new application
        Application application = new Application();
        application.setJobId(jobId);
        application.setCandidateId(candidateId);
        application.setStatus("APPLIED");

        // 7. Insert application into database
        boolean success = applicationDAO.addApplication(application);

        // 8. Redirect based on result
        if (success) {
            response.sendRedirect("viewJobs?success=1");
        } else {
            response.sendRedirect("viewJobs?error=failed");
        }
    }
}
