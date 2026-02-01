package com.smarthire.controller;

import com.smarthire.dao.JobDAO;
import com.smarthire.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteJob")
public class DeleteJobServlet extends HttpServlet {
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

        String jobIdParam = request.getParameter("jobId");
        
        if (jobIdParam == null || jobIdParam.isEmpty()) {
            response.sendRedirect("viewMyJobs?error=invalid");
            return;
        }

        int jobId = Integer.parseInt(jobIdParam);

        JobDAO jobDAO = new JobDAO();
        boolean success = jobDAO.deleteJob(jobId, recruiter.getUserId());

        if (success) {
            response.sendRedirect("viewMyJobs?deleted=1");
        } else {
            response.sendRedirect("viewMyJobs?error=failed");
        }
    }
}
