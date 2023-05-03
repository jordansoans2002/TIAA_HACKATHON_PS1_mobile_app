package com.example.tiaa_ps1_mobile_app;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    RecyclerView history;
    String MY_SHARED_PREFERENCES = "SwiftTravelsSharedPreferences";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        sharedPreferences = getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);


        history = findViewById(R.id.history);

        String[] features = {"A/C", "Sleeper"};
        BusDetails bus = new BusDetails("1500-0920", 4000, 18, 1, "Canara Pinto", features);
        List<BusDetails> buses = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            buses.add(bus);
        BusDetailsAdapter adapter = new BusDetailsAdapter(sharedPreferences, buses);
        history.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        history.setAdapter(adapter);
        history.setClickable(false);
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
                LoginPopup dialog = new LoginPopup(history.getRootView(),sharedPreferences);
                dialog.createDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}