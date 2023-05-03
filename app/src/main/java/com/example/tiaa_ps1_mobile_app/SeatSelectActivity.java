package com.example.tiaa_ps1_mobile_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectActivity extends AppCompatActivity {

    private TextView time;
    private TextView cost;
    private TextView duration;
    private TextView operator;
    private TextView features;
    RecyclerView seats;
    private Button book;

    static List<Integer> selected = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_select);

        Intent intent = getIntent();
        String t = intent.getStringExtra("time");
        String c = intent.getStringExtra("cost");
        String d  = intent.getStringExtra("duration");
        String o = intent.getStringExtra("operator");
        String f = intent.getStringExtra("features");

        time = findViewById(R.id.time);
        time.setText(t);
        cost = findViewById(R.id.cost);
        cost.setText(c);
        duration = findViewById(R.id.duration);
        duration.setText(d);
        operator = findViewById(R.id.operator);
        operator.setText(o);
        features = findViewById(R.id.features);
        features.setText(f);
        seats = findViewById(R.id.seats);
        book = findViewById(R.id.book);

        //get number of seats, number of columns and available seats from backend
        List<Integer> available = new ArrayList<>();
        available.add(1);
        available.add(3);
        available.add(5);

        SeatsAdapter adapter = new SeatsAdapter(30,available);
        seats.setLayoutManager(new GridLayoutManager(getApplicationContext(),5));
        seats.setAdapter(adapter);

        book.setOnClickListener((View view) -> {
            Intent pay = new Intent(view.getContext(),PaymentActivity.class);
            pay.putExtra("time",t);
            pay.putExtra("cost",c);
            pay.putExtra("duration",d);
            pay.putExtra("operator",o);
            pay.putExtra("features",f);
            view.getContext().startActivity(pay);
        });
    }

}