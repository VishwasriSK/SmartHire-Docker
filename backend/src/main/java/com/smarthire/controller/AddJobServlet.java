package com.smarthire.controller;

import com.smarthire.dao.JobDAO;
import com.smarthire.model.Job;
import com.smarthire.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addJob")
public class AddJobServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Get the current session and check if user is logged in
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            // If not logged in, redirect to login page
            response.sendRedirect("login.html");
            return;
        }

        // 2. Get the logged-in user from session
        User recruiter = (User) session.getAttribute("user");

        // 3. Verify the user is actually a recruiter
        if (!"RECRUITER".equals(recruiter.getRole())) {
            // If not a recruiter, redirect to login
            response.sendRedirect("login.html");
            return;
        }

        // 4. Get form data (job title and description)
        String title = request.getParameter("title");
        String description = request.getParameter("description");

        // 5. Create a Job object
        Job job = new Job();
        job.setTitle(title);
        job.setDescription(description);
        job.setRecruiterId(recruiter.getUserId());

        // 6. Use JobDAO to insert into database
        JobDAO jobDAO = new JobDAO();
        boolean success = jobDAO.addJob(job);

        // 7. Redirect based on result
        if (success) {
            // Success: redirect back to form with success message
            response.sendRedirect("add-job.html?success=1");
        } else {
            // Failure: redirect back to form with error
            response.sendRedirect("add-job.html?error=1");
        }
    }
}
