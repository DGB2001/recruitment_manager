package com.example.recruitmentmanager.Model;

public class RecruitmentInfo {
    private String title, description, expired_at;
    private int id,salary, quantity;
    private EmployerInfo employer;
    private LevelInfo master_level;
    private TechnicalInfo master_technical;

    private String order_by;

    public RecruitmentInfo(String order_by) {
        this.order_by = order_by;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public EmployerInfo getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerInfo employer) {
        this.employer = employer;
    }

    public LevelInfo getMaster_level() {
        return master_level;
    }

    public void setMaster_level(LevelInfo master_level) {
        this.master_level = master_level;
    }

    public TechnicalInfo getMaster_technical() {
        return master_technical;
    }

    public void setMaster_technical(TechnicalInfo master_technical) {
        this.master_technical = master_technical;
    }
}