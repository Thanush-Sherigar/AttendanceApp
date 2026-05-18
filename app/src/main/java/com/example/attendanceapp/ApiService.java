package com.example.attendanceapp;
import com.example.attendanceapp.network.LoginRequest;
import com.example.attendanceapp.network.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("api/attendance/mark")
    Call<String> markAttendance(@Header("Authorization") String authHeader, @Query("token") String qrtoken);
}
