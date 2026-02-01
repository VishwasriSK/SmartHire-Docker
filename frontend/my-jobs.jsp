<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.Job" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Posted Jobs</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
            margin: 0;
        }
        .container {
            max-width: 1200px;
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
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
        }
        .message-success {
            background-color: #d4edda;
            color: #155724;
        }
        .message-error {
            background-color: #f8d7da;
            color: #721c24;
        }
        .job-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            background-color: #fafafa;
        }
        .job-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        .job-title {
            color: #007bff;
            font-size: 20px;
            font-weight: bold;
        }
        .job-description {
            color: #555;
            line-height: 1.6;
            margin-bottom: 15px;
            white-space: pre-wrap;
        }
        .delete-btn {
            padding: 8px 16px;
            background-color: #dc3545;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
            border: none;
            cursor: pointer;
        }
        .delete-btn:hover {
            background-color: #c82333;
        }
        .no-jobs {
            text-align: center;
            color: #999;
            padding: 60px;
            font-size: 18px;
        }
        .no-jobs a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }
        .no-jobs a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="logout" class="logout">Logout</a>
        <h1>My Posted Jobs</h1>
        <a href="recruiter-dashboard.html" class="back-link">← Back to Dashboard</a>
        
        <%
            String deleted = request.getParameter("deleted");
            String error = request.getParameter("error");
            
            if ("1".equals(deleted)) {
        %>
            <div class="message message-success">
                ✅ Job deleted successfully!
            </div>
        <%
            } else if ("failed".equals(error)) {
        %>
            <div class="message message-error">
                ❌ Failed to delete job. Please try again.
            </div>
        <%
            }
        %>
        
        <%
            List<Job> jobs = (List<Job>) request.getAttribute("jobs");
            
            if (jobs == null || jobs.isEmpty()) {
        %>
            <div class="no-jobs">
                📭 You haven't posted any jobs yet.<br><br>
                <a href="add-job.html">Post Your First Job</a>
            </div>
        <%
            } else {
                for (Job job : jobs) {
        %>
            <div class="job-card">
                <div class="job-header">
                    <div class="job-title"><%= job.getTitle() %></div>
                    <button class="delete-btn" onclick="confirmDelete(<%= job.getJobId() %>)">🗑️ Delete</button>
                </div>
                <div class="job-description"><%= job.getDescription() %></div>
            </div>
        <%
                }
            }
        %>
    </div>
    
    <script>
        function confirmDelete(jobId) {
            if (confirm('Are you sure you want to delete this job? This action cannot be undone.')) {
                window.location.href = 'deleteJob?jobId=' + jobId;
            }
        }
    </script>
</body>
</html>
