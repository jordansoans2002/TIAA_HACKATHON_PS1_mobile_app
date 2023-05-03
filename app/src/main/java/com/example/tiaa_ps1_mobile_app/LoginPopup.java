package com.example.tiaa_ps1_mobile_app;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoginPopup {
    EditText username;
    EditText password;
    Button loginButton;
    View view;
    SharedPreferences sharedPreferences;
    public LoginPopup(View view,SharedPreferences preferences){
        this.view=view;
        this.sharedPreferences = preferences;
    }
    void createDialog(BusDetails bus){
        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.login);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        username = dialog.findViewById(R.id.username);
        password = dialog.findViewById(R.id.password);
        loginButton = dialog.findViewById(R.id.loginButton);

        loginButton.setOnClickListener((View view) -> {
            //use backend to check if the credentials are valid if yes then
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("IS_LOGGED_IN", true);
            editor.commit();
        });

        dialog.show();
    }
}
