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
import java.util.List;

@WebServlet("/viewJobs")
public class ViewJobsServlet extends HttpServlet {
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
        User user = (User) session.getAttribute("user");

        // 3. Fetch all jobs from database
        JobDAO jobDAO = new JobDAO();
        List<Job> jobs = jobDAO.getAllJobs();

        // 4. Store jobs in request scope so JSP can access them
        request.setAttribute("jobs", jobs);
        request.setAttribute("user", user);

        // 5. Forward to view-jobs.jsp
        request.getRequestDispatcher("view-jobs.jsp").forward(request, response);
    }
}
