<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.smarthire.model.ApplicationView" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Applications</title>
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
        
        .app-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .app-table th {
            background-color: #f8f9fa;
            padding: 12px;
            text-align: left;
            border-bottom: 2px solid #dee2e6;
            font-weight: bold;
            color: #495057;
        }
        .app-table td {
            padding: 12px;
            border-bottom: 1px solid #dee2e6;
        }
        .app-table tr:hover {
            background-color: #f8f9fa;
        }
        .status-badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
        }
        .status-applied {
            background-color: #cfe2ff;
            color: #084298;
        }
        .status-shortlisted {
            background-color: #d1e7dd;
            color: #0f5132;
        }
        .status-rejected {
            background-color: #f8d7da;
            color: #842029;
        }
        .action-btn {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 13px;
            font-weight: bold;
            text-decoration: none;
            display: inline-block;
            margin-right: 5px;
        }
        .btn-shortlist {
            background-color: #28a745;
            color: white;
        }
        .btn-shortlist:hover {
            background-color: #218838;
        }
        .btn-reject {
            background-color: #dc3545;
            color: white;
        }
        .btn-reject:hover {
            background-color: #c82333;
        }
        .no-applications {
            text-align: center;
            padding: 60px;
            color: #999;
            font-size: 18px;
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
    </style>
</head>
<body>
    <div class="container">
        <a href="logout" class="logout">Logout</a>
        <h1>Manage Applications</h1>
        <a href="recruiter-dashboard.html" class="back-link">← Back to Dashboard</a>
        
        <!-- Success Message -->
        <%
            String success = request.getParameter("success");
            if ("1".equals(success)) {
        %>
            <div class="message message-success">
                ✅ Application status updated successfully!
            </div>
        <%
            }
        %>
        
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
                <p>Pending Review</p>
            </div>
            <div class="stat-card stat-shortlisted">
                <h3><%= shortlistedCount %></h3>
                <p>Shortlisted</p>
            </div>
            <div class="stat-card stat-rejected">
                <h3><%= rejectedCount %></h3>
                <p>Rejected</p>
            </div>
        </div>
        
        <!-- Applications Table -->
        <%
            if (applications == null || applications.isEmpty()) {
        %>
            <div class="no-applications">
                📭 No applications received yet.<br>
                Candidates will see jobs you post and apply!
            </div>
        <%
            } else {
        %>
            <table class="app-table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Candidate Name</th>
                        <th>Email</th>
                        <th>Job Title</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (ApplicationView app : applications) {
                        String statusClass = "";
                        if ("APPLIED".equals(app.getStatus())) statusClass = "status-applied";
                        else if ("SHORTLISTED".equals(app.getStatus())) statusClass = "status-shortlisted";
                        else if ("REJECTED".equals(app.getStatus())) statusClass = "status-rejected";
                %>
                    <tr>
                        <td><%= app.getAppId() %></td>
                        <td><%= app.getCandidateName() %></td>
                        <td><%= app.getCandidateEmail() %></td>
                        <td><%= app.getJobTitle() %></td>
                        <td>
                            <span class="status-badge <%= statusClass %>">
                                <%= app.getStatus() %>
                            </span>
                        </td>
                        <td>
                            <% if (!"SHORTLISTED".equals(app.getStatus())) { %>
                                <a href="updateApplicationStatus?appId=<%= app.getAppId() %>&status=SHORTLISTED" 
                                   class="action-btn btn-shortlist">Shortlist</a>
                            <% } %>
                            <% if (!"REJECTED".equals(app.getStatus())) { %>
                                <a href="updateApplicationStatus?appId=<%= app.getAppId() %>&status=REJECTED" 
                                   class="action-btn btn-reject">Reject</a>
                            <% } %>
                        </td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        <%
            }
        %>
    </div>
</body>
</html>
