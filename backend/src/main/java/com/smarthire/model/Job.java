package com.smarthire.model;

public class Job {
    private int jobId;
    private String title;
    private String description;
    private int recruiterId;

    // Constructors
    public Job() {
    }

    public Job(String title, String description, int recruiterId) {
        this.title = title;
        this.description = description;
        this.recruiterId = recruiterId;
    }

    // Getters and Setters
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }
}
