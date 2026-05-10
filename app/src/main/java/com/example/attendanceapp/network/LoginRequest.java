package com.example.attendanceapp.network;

public class LoginRequest {
    private String email;
    private String password;
    public LoginRequest(String email,String password){
        this.email=email;
        this.password=password;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String password(){
        return password;
    }
    public void getPassword(String password){
        this.password=password;
    }

}
