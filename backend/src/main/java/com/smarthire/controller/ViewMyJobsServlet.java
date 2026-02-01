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

@WebServlet("/viewMyJobs")
public class ViewMyJobsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html");
            return;
        }

        User recruiter = (User) session.getAttribute("user");
        
        if (!"RECRUITER".equals(recruiter.getRole())) {
            response.sendRedirect("login.html");
            return;
        }

        JobDAO jobDAO = new JobDAO();
        List<Job> myJobs = jobDAO.getJobsByRecruiter(recruiter.getUserId());

        request.setAttribute("jobs", myJobs);
        request.getRequestDispatcher("my-jobs.jsp").forward(request, response);
    }
}
