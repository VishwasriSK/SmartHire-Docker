<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.Job" %>
<%@ page import="com.smarthire.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Jobs</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
            margin: 0;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 10px;
        }
        .logout {
            float: right;
            padding: 10px 20px;
            background-color: #dc3545;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .logout:hover {
            background-color: #c82333;
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .message {
            padding: 15px;
            border-radius: 4px;
            text-align: center;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .message-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .message-warning {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffeeba;
        }
        .message-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .job-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            background-color: #fafafa;
            transition: box-shadow 0.3s;
        }
        .job-card:hover {
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .job-title {
            color: #007bff;
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .job-description {
            color: #555;
            line-height: 1.6;
            margin-bottom: 15px;
            white-space: pre-wrap;
        }
        .apply-btn {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            display: inline-block;
            font-weight: bold;
        }
        .apply-btn:hover {
            background-color: #218838;
        }
        .no-jobs {
            text-align: center;
            color: #999;
            padding: 40px;
            font-size: 18px;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="logout" class="logout">Logout</a>
        <h1>Available Jobs</h1>
        <a href="candidate-dashboard.html" class="back-link">← Back to Dashboard</a>
        
        <!-- Success/Error Messages -->
        <%
            String success = request.getParameter("success");
            String error = request.getParameter("error");
            
            if ("1".equals(success)) {
        %>
            <div class="message message-success">
                ✅ Application submitted successfully!
            </div>
        <%
            } else if ("duplicate".equals(error)) {
        %>
            <div class="message message-warning">
                ⚠️ You have already applied for this job!
            </div>
        <%
            } else if ("failed".equals(error)) {
        %>
            <div class="message message-error">
                ❌ Failed to submit application. Please try again.
            </div>
        <%
            } else if ("invalid".equals(error)) {
        %>
            <div class="message message-error">
                ❌ Invalid job ID.
            </div>
        <%
            }
        %>
        
        <!-- Jobs List -->
        <%
            List<Job> jobs = (List<Job>) request.getAttribute("jobs");
            
            if (jobs == null || jobs.isEmpty()) {
        %>
            <div class="no-jobs">
                No jobs available at the moment. Check back later!
            </div>
        <%
            } else {
                for (Job job : jobs) {
        %>
            <div class="job-card">
                <div class="job-title"><%= job.getTitle() %></div>
                <div class="job-description"><%= job.getDescription() %></div>
                <a href="applyJob?jobId=<%= job.getJobId() %>" class="apply-btn">Apply Now</a>
            </div>
        <%
                }
            }
        %>
    </div>
</body>
</html>
