package com.example.attendanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendanceapp.network.LoginRequest;
import com.example.attendanceapp.network.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Linking the Java variables to the XML IDs
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 2. Setting up the Click Listener
        btnLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            performLogin(email, password);
        } else {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void performLogin(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);

        // Use our Retrofit engine to make the call
        RetrofitClient.getApiService().login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();

                    // SAVE the token for later (The "Passport" storage)
                    saveToken(token);

                    Toast.makeText(MainActivity.this, "Login Successful! 🎉", Toast.LENGTH_SHORT).show();

                    // Move to the Scanner Screen
                    startActivity(new Intent(MainActivity.this, ScannerActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials ❌", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences pref = getSharedPreferences("AttendanceApp", MODE_PRIVATE);
        pref.edit().putString("JWT_TOKEN", token).apply();
    }
}