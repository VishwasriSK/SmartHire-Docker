<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.ApplicationView" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Applications</title>
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
        .stats {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
        }
        .stat-card {
            flex: 1;
            padding: 20px;
            border-radius: 8px;
            color: white;
            text-align: center;
        }
        .stat-card h3 {
            margin: 0;
            font-size: 32px;
        }
        .stat-card p {
            margin: 5px 0 0 0;
            font-size: 14px;
        }
        .stat-total { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .stat-applied { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
        .stat-shortlisted { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }
        .stat-rejected { background: linear-gradient(135deg, #fa709a 0%, #fee140 100%); }
        
        .application-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            background-color: #fafafa;
            transition: all 0.3s;
            position: relative;
        }
        .application-card:hover {
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            transform: translateY(-2px);
        }
        .app-header {
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
        .status-badge {
            display: inline-block;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: bold;
            text-transform: uppercase;
        }
        .status-APPLIED {
            background-color: #cfe2ff;
            color: #084298;
        }
        .status-SHORTLISTED {
            background-color: #d1e7dd;
            color: #0f5132;
        }
        .status-REJECTED {
            background-color: #f8d7da;
            color: #842029;
        }
        .app-info {
            color: #666;
            font-size: 14px;
            margin-bottom: 10px;
        }
        .no-applications {
            text-align: center;
            padding: 60px;
            color: #999;
            font-size: 18px;
        }
        .no-applications a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }
        .no-applications a:hover {
            text-decoration: underline;
        }
        .status-message {
            margin-top: 10px;
            padding: 10px;
            border-radius: 4px;
            font-size: 14px;
        }
        .msg-applied {
            background-color: #e7f3ff;
            color: #004085;
        }
        .msg-shortlisted {
            background-color: #d4edda;
            color: #155724;
        }
        .msg-rejected {
            background-color: #f8d7da;
            color: #721c24;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="logout" class="logout">Logout</a>
        <h1>My Applications</h1>
        <a href="candidate-dashboard.html" class="back-link">← Back to Dashboard</a>
        
        <%
            List<ApplicationView> applications = (List<ApplicationView>) request.getAttribute("applications");
            
            // Calculate statistics
            int totalApps = 0;
            int appliedCount = 0;
            int shortlistedCount = 0;
            int rejectedCount = 0;
            
            if (applications != null) {
                totalApps = applications.size();
                for (ApplicationView app : applications) {
                    String status = app.getStatus();
                    if ("APPLIED".equals(status)) appliedCount++;
                    else if ("SHORTLISTED".equals(status)) shortlistedCount++;
                    else if ("REJECTED".equals(status)) rejectedCount++;
                }
            }
        %>
        
        <!-- Statistics Cards -->
        <div class="stats">
            <div class="stat-card stat-total">
                <h3><%= totalApps %></h3>
                <p>Total Applications</p>
            </div>
            <div class="stat-card stat-applied">
                <h3><%= appliedCount %></h3>
                <p>Under Review</p>
            </div>
            <div class="stat-card stat-shortlisted">
                <h3><%= shortlistedCount %></h3>
                <p>Shortlisted 🎉</p>
            </div>
            <div class="stat-card stat-rejected">
                <h3><%= rejectedCount %></h3>
                <p>Not Selected</p>
            </div>
        </div>
        
        <!-- Applications List -->
        <%
            if (applications == null || applications.isEmpty()) {
        %>
            <div class="no-applications">
                📭 You haven't applied to any jobs yet.<br><br>
                <a href="viewJobs">Browse Jobs</a> and start applying!
            </div>
        <%
            } else {
                for (ApplicationView app : applications) {
                    String statusClass = "status-" + app.getStatus();
                    String msgClass = "";
                    String statusMessage = "";
                    
                    if ("APPLIED".equals(app.getStatus())) {
                        msgClass = "msg-applied";
                        statusMessage = "Your application is being reviewed by the recruiter.";
                    } else if ("SHORTLISTED".equals(app.getStatus())) {
                        msgClass = "msg-shortlisted";
                        statusMessage = "🎉 Congratulations! You've been shortlisted for this position. The recruiter may contact you soon!";
                    } else if ("REJECTED".equals(app.getStatus())) {
                        msgClass = "msg-rejected";
                        statusMessage = "Unfortunately, you weren't selected for this position. Keep applying!";
                    }
        %>
            <div class="application-card">
                <div class="app-header">
                    <div class="job-title"><%= app.getJobTitle() %></div>
                    <span class="status-badge <%= statusClass %>">
                        <%= app.getStatus() %>
                    </span>
                </div>
                <div class="app-info">
                    Application ID: #<%= app.getAppId() %>
                </div>
                <div class="status-message <%= msgClass %>">
                    <%= statusMessage %>
                </div>
            </div>
        <%
                }
            }
        %>
    </div>
</body>
</html>
