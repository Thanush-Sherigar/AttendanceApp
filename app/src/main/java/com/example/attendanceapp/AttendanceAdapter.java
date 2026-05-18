package com.example.attendanceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendanceapp.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.HistoryViewHolder> {

    private List<AttendanceHistoryItem> historyList;

    public AttendanceAdapter(List<AttendanceHistoryItem> historyList) {
        this.historyList = historyList;
    }

    // 1. Inflates the XML row layout (creates the physical row view)
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance, parent, false);
        return new HistoryViewHolder(view);
    }

    // 2. Binds data to the XML elements inside that specific row
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        AttendanceHistoryItem item = historyList.get(position);
        holder.tvCourseName.setText(item.getCourseName());
        holder.tvTimestamp.setText(item.getTimestamp());
    }

    // 3. Tells the list how many items it needs to show
    @Override
    public int getItemCount() {
        return historyList.size();
    }

    // The "Holder" holds references to the views inside a single row item
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName, tvTimestamp;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        }
    }
}