package com.example.attendanceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v -> startScanning());
    }

    private void startScanning() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan the Professor's QR Code");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    // This method runs AFTER you scan the QR code
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                // SUCCESS! 'result.getContents()' is the Token from the QR code
                sendAttendanceToServer(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void sendAttendanceToServer(String qrToken) {
        // 1. Get our saved JWT from storage
        SharedPreferences pref = getSharedPreferences("AttendanceApp", MODE_PRIVATE);
        String jwt = pref.getString("JWT_TOKEN", "");

        // 2. Call the API (Don't forget the "Bearer " prefix!)
        RetrofitClient.getApiService().markAttendance("Bearer " + jwt, qrToken)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ScannerActivity.this, "Attendance Marked! ✅", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ScannerActivity.this, "Failed: " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(ScannerActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}