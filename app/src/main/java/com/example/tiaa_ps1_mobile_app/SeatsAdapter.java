package com.example.tiaa_ps1_mobile_app;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.ViewHolder>{

    int numberOfSeats;
    List<Integer> available;
    public SeatsAdapter(int n,List<Integer> available){
        numberOfSeats=n;
        this.available=available;
    }

    @NonNull
    @Override
    public SeatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_seat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatsAdapter.ViewHolder holder, int position) {
        //holder.seat.setText(position+"");
        if(available.contains(holder.getAdapterPosition()))
            holder.seat.setBackgroundColor(Color.WHITE);
        else
            holder.seat.setBackgroundColor(Color.LTGRAY);

        holder.seat.setOnClickListener((View view) -> {
            if(!available.contains(holder.getAdapterPosition())){
                Snackbar.make(view,"Seat is already taken",Snackbar.LENGTH_LONG)
                        .show();
                return;
            }
            if(SeatSelectActivity.selected.contains(holder.getAdapterPosition())) {
                SeatSelectActivity.selected.remove((Integer) holder.getAdapterPosition());
                holder.seat.setBackgroundColor(Color.WHITE);
            } else {
                SeatSelectActivity.selected.add(holder.getAdapterPosition());
                holder.seat.setBackgroundColor(Color.GREEN);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numberOfSeats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button seat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seat = itemView.findViewById(R.id.seat);
        }
    }
}
