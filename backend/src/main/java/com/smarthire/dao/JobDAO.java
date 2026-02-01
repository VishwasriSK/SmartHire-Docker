package com.smarthire.dao;

import com.smarthire.model.Job;
import com.smarthire.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    // Add a new job
    public boolean addJob(Job job) {
        boolean success = false;
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO jobs(title, description, recruiter_id) VALUES(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, job.getTitle());
            ps.setString(2, job.getDescription());
            ps.setInt(3, job.getRecruiterId());
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM jobs ORDER BY job_id DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getInt("job_id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setRecruiterId(rs.getInt("recruiter_id"));
                jobs.add(job);
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return jobs;
    }

    // Get jobs by specific recruiter
    public List<Job> getJobsByRecruiter(int recruiterId) {
        List<Job> jobs = new ArrayList<>();
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM jobs WHERE recruiter_id = ? ORDER BY job_id DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, recruiterId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getInt("job_id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setRecruiterId(rs.getInt("recruiter_id"));
                jobs.add(job);
            }
            
            rs.close();
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return jobs;
    }

    // Delete a job (only by the recruiter who posted it)
    public boolean deleteJob(int jobId, int recruiterId) {
        boolean success = false;
        
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM jobs WHERE job_id = ? AND recruiter_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, jobId);
            ps.setInt(2, recruiterId);
            
            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
            
            ps.close();
            con.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
    }
}
