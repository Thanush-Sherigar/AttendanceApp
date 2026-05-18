package com.example.attendanceapp;

public class AttendanceHistoryItem {
    private String courseName;
    private String timestamp;

    // Retrofit uses these getters to populate the list
    public String getCourseName() { return courseName; }
    public String getTimestamp() { return timestamp; }
}