package com.example.licenceapp.Model;

public class UserRegister {

    private String usernameRegister;
    private String emailRegister;
    private String passwordRegister;
    private String secondPasswordRegister;

    public UserRegister() {
    }

    public UserRegister(String usernameRegister, String emailRegister, String passwordRegister, String secondPasswordRegister) {
        this.usernameRegister = usernameRegister;
        this.emailRegister = emailRegister;
        this.passwordRegister = passwordRegister;
        this.secondPasswordRegister = secondPasswordRegister;
    }

    public String getUsernameRegister() {
        return usernameRegister;
    }

    public void setUsernameRegister(String usernameRegister) {
        this.usernameRegister = usernameRegister;
    }

    public String getEmailRegister() {
        return emailRegister;
    }

    public void setEmailRegister(String emailRegister) {
        this.emailRegister = emailRegister;
    }

    public String getPasswordRegister() {
        return passwordRegister;
    }

    public void setPasswordRegister(String passwordRegister) {
        this.passwordRegister = passwordRegister;
    }

    public String getSecondPasswordRegister() {
        return secondPasswordRegister;
    }

    public void setSecondPasswordRegister(String secondPasswordRegister) {
        this.secondPasswordRegister = secondPasswordRegister;
    }
}
