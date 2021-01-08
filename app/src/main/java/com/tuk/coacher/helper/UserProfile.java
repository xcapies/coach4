package com.tuk.coacher.helper;

public class UserProfile {
    private String fname, lname, password, email, username, phone;

    public UserProfile(String fname, String lname, String password, String email, String username, String phone) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.email = email;
        this.username = username;
        this.phone = phone;
    }

    public UserProfile(){}

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
