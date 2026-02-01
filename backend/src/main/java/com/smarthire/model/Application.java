package com.smarthire.model;

public class Application {
    private int appId;
    private int jobId;
    private int candidateId;
    private String status;

    // Constructors
    public Application() {
    }

    public Application(int jobId, int candidateId, String status) {
        this.jobId = jobId;
        this.candidateId = candidateId;
        this.status = status;
    }

    // Getters and Setters
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
