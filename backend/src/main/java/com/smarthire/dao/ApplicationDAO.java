package com.smarthire.dao;

import com.smarthire.model.Application;
import com.smarthire.model.ApplicationView;
import com.smarthire.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    // Check if candidate has already applied for this job
    public boolean hasAlreadyApplied(int jobId, int candidateId) {
        boolean exists = false;
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM applications WHERE job_id = ? AND candidate_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, jobId);
            ps.setInt(2, candidateId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                exists = true;
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return exists;
    }

    // Add a new application
    public boolean addApplication(Application application) {
        boolean success = false;
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO applications (job_id, candidate_id, status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, application.getJobId());
            ps.setInt(2, application.getCandidateId());
            ps.setString(3, application.getStatus());
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
    }

    // Get all applications for a specific job
    public List<Application> getApplicationsByJob(int jobId) {
        List<Application> applications = new ArrayList<>();
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM applications WHERE job_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, jobId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Application app = new Application();
                app.setAppId(rs.getInt("app_id"));
                app.setJobId(rs.getInt("job_id"));
                app.setCandidateId(rs.getInt("candidate_id"));
                app.setStatus(rs.getString("status"));
                applications.add(app);
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return applications;
    }

    // Get all applications by a specific candidate
    public List<Application> getApplicationsByCandidate(int candidateId) {
        List<Application> applications = new ArrayList<>();
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM applications WHERE candidate_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, candidateId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Application app = new Application();
                app.setAppId(rs.getInt("app_id"));
                app.setJobId(rs.getInt("job_id"));
                app.setCandidateId(rs.getInt("candidate_id"));
                app.setStatus(rs.getString("status"));
                applications.add(app);
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return applications;
    }

    // Get detailed view of applications for recruiter's jobs (with JOIN)
    public List<ApplicationView> getApplicationViewsByRecruiter(int recruiterId) {
        List<ApplicationView> applicationViews = new ArrayList<>();
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT a.app_id, a.job_id, a.candidate_id, a.status, " +
                         "j.title AS job_title, u.name AS candidate_name, u.email AS candidate_email " +
                         "FROM applications a " +
                         "JOIN jobs j ON a.job_id = j.job_id " +
                         "JOIN users u ON a.candidate_id = u.user_id " +
                         "WHERE j.recruiter_id = ? " +
                         "ORDER BY a.app_id DESC";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, recruiterId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ApplicationView view = new ApplicationView();
                view.setAppId(rs.getInt("app_id"));
                view.setJobId(rs.getInt("job_id"));
                view.setCandidateId(rs.getInt("candidate_id"));
                view.setStatus(rs.getString("status"));
                view.setJobTitle(rs.getString("job_title"));
                view.setCandidateName(rs.getString("candidate_name"));
                view.setCandidateEmail(rs.getString("candidate_email"));
                applicationViews.add(view);
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return applicationViews;
    }

    // Update application status
    public boolean updateApplicationStatus(int appId, String status) {
        boolean success = false;
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE applications SET status = ? WHERE app_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, appId);
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
    }

    // Get detailed view of candidate's applications (with JOIN) - NEW METHOD
    public List<ApplicationView> getApplicationViewsByCandidate(int candidateId) {
        List<ApplicationView> applicationViews = new ArrayList<>();
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT a.app_id, a.job_id, a.candidate_id, a.status, " +
                         "j.title AS job_title " +
                         "FROM applications a " +
                         "JOIN jobs j ON a.job_id = j.job_id " +
                         "WHERE a.candidate_id = ? " +
                         "ORDER BY a.app_id DESC";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, candidateId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                ApplicationView view = new ApplicationView();
                view.setAppId(rs.getInt("app_id"));
                view.setJobId(rs.getInt("job_id"));
                view.setCandidateId(rs.getInt("candidate_id"));
                view.setStatus(rs.getString("status"));
                view.setJobTitle(rs.getString("job_title"));
                applicationViews.add(view);
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return applicationViews;
    }
}
