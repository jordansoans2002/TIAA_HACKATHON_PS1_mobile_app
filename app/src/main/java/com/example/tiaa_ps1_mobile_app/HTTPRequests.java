package com.example.tiaa_ps1_mobile_app;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HTTPRequests {
    static final String url = "http://localhost:4040/"; // your URL
    static Context context;

    public HTTPRequests(Context context){
        this.context=context;
    }

    static void post(HashMap<String,String> params,String path){
        final RequestQueue queue = Volley.newRequestQueue(context);

        queue.start();
        final boolean[] success = {false};

        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                (url+path),
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response.getString("message");
                            //if valid credentials then write to sharedpreferences
                            success[0] = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               //show error
            }
        });
        queue.add(jsObjRequest);
    }
}
