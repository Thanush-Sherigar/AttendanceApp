package com.example.attendanceapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private AttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rvHistory);
        // Tells the RecyclerView to arrange items in a standard vertical list block
        rvHistory.setLayoutManager(new LinearLayoutManager(this));

        fetchHistoryData();
    }

    private void fetchHistoryData() {
        SharedPreferences pref = getSharedPreferences("AttendanceApp", MODE_PRIVATE);
        String jwt = pref.getString("JWT_TOKEN", "");

        RetrofitClient.getApiService().getAttendanceHistory("Bearer " + jwt)
                .enqueue(new Callback<List<AttendanceHistoryItem>>() {
                    @Override
                    public void onResponse(Call<List<AttendanceHistoryItem>> call, Response<List<AttendanceHistoryItem>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<AttendanceHistoryItem> items = response.body();

                            // Link our data list to the middleman adapter and hook it to the view
                            adapter = new AttendanceAdapter(items);
                            rvHistory.setAdapter(adapter);
                        } else {
                            Toast.makeText(HistoryActivity.this, "Failed to load history", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AttendanceHistoryItem>> call, Throwable t) {
                        Toast.makeText(HistoryActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}