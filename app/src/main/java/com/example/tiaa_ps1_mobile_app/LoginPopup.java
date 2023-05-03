package com.example.tiaa_ps1_mobile_app;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class LoginPopup {
    EditText username;
    EditText password;
    TextView error;
    Button loginButton;
    View view;
    SharedPreferences sharedPreferences;

    public LoginPopup(View view,SharedPreferences preferences){
        this.view=view;
        this.sharedPreferences = preferences;
    }
    void createDialog(BusDetails bus) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.login);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        username = dialog.findViewById(R.id.username);
        password = dialog.findViewById(R.id.password);
        error = dialog.findViewById(R.id.error);
        loginButton = dialog.findViewById(R.id.loginButton);

        error.setVisibility(View.GONE);

        loginButton.setOnClickListener((View view) -> {
            //use backend to check if the credentials are valid if yes then
            HashMap<String, String> params = new HashMap<>();
            params.put("email", username.getText().toString());
            params.put("password", password.getText().toString());
            HTTPRequests.post(params, "login");
            //if incorrect then
//            error.setVisibility(View.VISIBLE);
            //based on response
//            error.setVisibility(View.GONE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("IS_LOGGED_IN", true);
            editor.commit();
            Intent intent = new Intent(view.getContext(), SeatSelectActivity.class);
            intent.putExtra("time", bus.getTime());
            intent.putExtra("cost", bus.getCost());
            intent.putExtra("duration", bus.getDuration());
            intent.putExtra("operator", bus.getOperator());
            intent.putExtra("features", bus.getFeatures());
            view.getContext().startActivity(intent);
        });
        dialog.show();
    }

        void createDialog(){
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.login);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);

            username = dialog.findViewById(R.id.username);
            password = dialog.findViewById(R.id.password);
            error = dialog.findViewById(R.id.error);
            loginButton = dialog.findViewById(R.id.loginButton);
            error.setVisibility(View.GONE);


            loginButton.setOnClickListener((View view) -> {
                //use backend to check if the credentials are valid if yes then
                HashMap<String,String> params = new HashMap<>();
                params.put("email",username.getText().toString());
                params.put("password",password.getText().toString());
                HTTPRequests.post(params,"login");
                //based on response
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("IS_LOGGED_IN", true);
                editor.commit();

//                Intent intent = new Intent(view.getContext(),BookingHistoryActivity.class);
//                view.getContext().startActivity(intent);

                dialog.dismiss();
            });

        dialog.show();
    }
}
