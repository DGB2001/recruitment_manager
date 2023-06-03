package com.example.recruitmentmanager.Model;

public class AuthLoginResponse {
    String email;
    int id, candidate_id, employer_id;
    String role, status;

    public AuthLoginResponse(int id, String email, String role, String status, int candidate_id, int employer_id) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
        this.candidate_id = candidate_id;
        this.employer_id = employer_id;
    }

    public int getEmployer_id() {
        return employer_id;
    }

    public void setEmployer_id(int employer_id) {
        this.employer_id = employer_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(int candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
