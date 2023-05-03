package com.example.tiaa_ps1_mobile_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BusDetailsAdapter extends RecyclerView.Adapter<BusDetailsAdapter.ViewHolder> {

    SharedPreferences sharedPreferences;
    List<BusDetails> buses;

    public BusDetailsAdapter(SharedPreferences sharedPreferences,List<BusDetails> buses){
        this.sharedPreferences = sharedPreferences;
        this.buses=buses;
    }

    @NonNull
    @Override
    public BusDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusDetailsAdapter.ViewHolder holder, int position) {
        holder.time.setText(buses.get(position).getTime());
        holder.cost.setText(buses.get(position).getCost());
        holder.duration.setText(buses.get(position).getDuration());
        holder.availability.setText(buses.get(position).getAvailability());
        holder.operator.setText(buses.get(position).getOperator());
        holder.features.setText(buses.get(position).getFeatures());

        holder.detailsCard.setOnClickListener((View view) -> {
            Log.e("check signin",sharedPreferences.getBoolean("IS_LOGGED_IN",Boolean.FALSE)+"");
            //in seat selector activity on resume check for login
            //if not logged in create dialog box
            if(sharedPreferences.getBoolean("IS_LOGGED_IN",Boolean.FALSE)) {
                //if logged in start seat selector activity
                Intent intent = new Intent(view.getContext(),SeatSelectActivity.class);
                BusDetails bus = buses.get(holder.getAdapterPosition());
                intent.putExtra("time",bus.getTime());
                intent.putExtra("cost",bus.getCost());
                intent.putExtra("duration",bus.getDuration());
                intent.putExtra("operator",bus.getOperator());
                intent.putExtra("features",bus.getFeatures());
                view.getContext().startActivity(intent);

            } else {
                LoginPopup dialog = new LoginPopup(view, sharedPreferences);
                dialog.createDialog(buses.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return buses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView detailsCard;
        private TextView time;
        private TextView cost;
        private TextView duration;
        private TextView availability;
        private TextView operator;
        private TextView features;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            detailsCard = itemView.findViewById(R.id.detailsCard);
            time = itemView.findViewById(R.id.time);
            cost = itemView.findViewById(R.id.cost);
            duration = itemView.findViewById(R.id.duration);
            availability = itemView.findViewById(R.id.availability);
            operator = itemView.findViewById(R.id.operator);
            features = itemView.findViewById(R.id.features);
        }
    }
}
