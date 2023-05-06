package com.example.recruitmentmanager.Model;

public class User {
    String name, phone_number, address, email, company_name;
    int gender;

    public User(String name, String phone_number, String address, String email, int gender) {
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }

    public User(String email, String company_name, String phone_number, String address) {
        this.phone_number = phone_number;
        this.address = address;
        this.email = email;
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
