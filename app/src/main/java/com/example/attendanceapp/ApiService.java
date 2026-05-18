package com.example.attendanceapp;
import com.example.attendanceapp.network.LoginRequest;
import com.example.attendanceapp.network.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    // Fetch the logged-in student's attendance history
    @GET("api/attendance/history")
    Call<List<AttendanceHistoryItem>> getAttendanceHistory(@Header("Authorization") String authHeader);
    @POST("api/attendance/mark")
    Call<String> markAttendance(@Header("Authorization") String authHeader, @Query("token") String qrtoken);
}
