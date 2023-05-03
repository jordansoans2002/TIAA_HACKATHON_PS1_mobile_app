package com.example.tiaa_ps1_mobile_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "main";

    EditText source;
    EditText destination;
    ImageView swap;
    TextView date;
    CalendarView calendar;
    Button search;
    RecyclerView busList;

    String MY_SHARED_PREFERENCES = "SwiftTravelsSharedPreferences";
    SharedPreferences sharedPreferences;
    String[] months = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new HTTPRequests(this);
        sharedPreferences = getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);

        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        swap = findViewById(R.id.swap);
        date = findViewById(R.id.date);
        calendar = findViewById(R.id.calendar);
        calendar.setVisibility(View.GONE);
        search = findViewById(R.id.search);
        busList = findViewById(R.id.busList);

        swap.setOnClickListener((View view) -> {
            String s = source.getText().toString();
            String d = destination.getText().toString();
            source.setText(d);
            destination.setText(s);
        });

        date.setOnClickListener((View view) -> {
            if (calendar.getVisibility() == View.GONE)
                calendar.setVisibility(View.VISIBLE);
            else
                calendar.setVisibility(View.GONE);

        });

        Calendar now = Calendar.getInstance();
        calendar.setMinDate(now.getTimeInMillis());
        calendar.setDate(now.getTimeInMillis());
        date.setText(now.get(3) + " " + months[now.get(2)]);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date.setText(String.valueOf(i2) + " " + months[i1]);
                calendar.setVisibility(View.GONE);
            }
        });


        String[] features = {"A/C", "Sleeper"};
        BusDetails bus = new BusDetails("1500-0920", 4000, 18, 1, "Canara Pinto", features);
        List<BusDetails> buses = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            buses.add(bus);
        BusDetailsAdapter adapter = new BusDetailsAdapter(sharedPreferences, buses);
        busList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        busList.setAdapter(adapter);

        search.setOnClickListener((View view) -> {
            String s = source.getText().toString();
            String d = destination.getText().toString();
            //update buses from backend
//            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String success = intent.getStringExtra("success");
        if (success != null) {
            if (Boolean.parseBoolean(success))
                Snackbar.make(busList.getRootView(), "Seat booking successful", Snackbar.LENGTH_LONG)
                        .show();
            else
                Snackbar.make(busList.getRootView(), "Seat booking failed, please try again", Snackbar.LENGTH_LONG)
                        .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.view_history) {
            Log.e("signed in before check history",sharedPreferences.getBoolean("IS_LOGGED_IN",Boolean.FALSE)+"");
            if(sharedPreferences.getBoolean("IS_LOGGED_IN",Boolean.FALSE)) {
                Intent intent = new Intent(this,BookingHistoryActivity.class);
                this.startActivity(intent);

            } else {
                LoginPopup dialog = new LoginPopup(busList.getRootView(),sharedPreferences);
                dialog.createDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}