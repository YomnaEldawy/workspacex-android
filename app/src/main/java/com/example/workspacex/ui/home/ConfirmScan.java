package com.example.workspacex.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.workspacex.HomeActivity;
import com.example.workspacex.R;
import com.example.workspacex.data.model.LoggedInUser;
import com.example.workspacex.ui.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmScan extends AppCompatActivity {

    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_scan);
        Button confirmButton = findViewById(R.id.confirm_checkin_btn);
        confirmButton.setVisibility(View.VISIBLE);
        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("QR_RESULT"));
            TextView wsNameTV = findViewById(R.id.ws_name_tv);
            wsNameTV.setText(jsonObject.get("name") + "");
            id = jsonObject.getString("id");
        } catch (Exception e) {
            TextView wsNameTV = findViewById(R.id.ws_name_tv);
            confirmButton.setVisibility(View.GONE);
            wsNameTV.setText("Failed to recognize :(");
            Toast.makeText(getApplicationContext(), "Could not recognize QR-code", Toast.LENGTH_LONG).show();
        }
    }

    public void confirmCheckIn(View view) {
        System.out.println("Confirmed");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {
            String userId = LoggedInUser.getUserId();

            String url = "http://192.168.1.108:5000/checkin/" + id;
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("customerId", userId);
            System.out.println("workspace id: " + id);
            System.out.println("user id: " + userId);
            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response);
                    Toast.makeText(getApplicationContext(), "Your request is successfully sent", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ConfirmScan.this, HomeActivity.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error checking in", Toast.LENGTH_LONG).show();
                    System.out.println(error.getMessage());
                    System.out.println(error);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    return headers;
                }
            };
            Volley.newRequestQueue(this).add(jsonObject);

        } catch (Exception e) {

        }

    }

    public void cancelScanning(View view) {
        System.out.println("Cancel");
        Intent intent = new Intent(ConfirmScan.this, HomeActivity.class);
        startActivity(intent);
    }

    public void retryScanning(View view) {
        Intent intent = new Intent(ConfirmScan.this, ScanActivity.class);
        startActivity(intent);
    }
}
