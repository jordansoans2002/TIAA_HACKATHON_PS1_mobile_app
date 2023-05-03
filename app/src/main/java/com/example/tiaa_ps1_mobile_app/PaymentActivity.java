package com.example.tiaa_ps1_mobile_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {
    private TextView time;
    private TextView cost;
    private TextView duration;
    private TextView operator;
    private TextView features;
    private EditText card_name;
    private EditText card_number;
    private EditText card_expiry;
    private EditText card_cvv;
    private Button pay;

    String MY_SHARED_PREFERENCES = "SwiftTravelsSharedPreferences";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sharedPreferences = getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);

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

        card_name = findViewById(R.id.card_name);
        card_number= findViewById(R.id.card_number);
        card_expiry= findViewById(R.id.card_expiry);
        card_cvv = findViewById(R.id.card_cvv);
        pay = findViewById(R.id.pay);

        pay.setOnClickListener((View view) -> {
            String name = card_name.getText().toString();
            String number = card_number.getText().toString();
            String expiry = card_expiry.getText().toString();
            String cvv = card_cvv.getText().toString();

            //send data for payment
            Intent goBack = new Intent(PaymentActivity.this,MainActivity.class);
            //if failed make sure to put false
            goBack.putExtra("success","true");
            this.startActivity(goBack);
        });

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
                LoginPopup dialog = new LoginPopup(pay.getRootView(),sharedPreferences);
                dialog.createDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}