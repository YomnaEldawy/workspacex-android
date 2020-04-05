package com.example.workspacex.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.workspacex.HomeActivity;
import com.example.workspacex.R;
import com.example.workspacex.ui.login.LoginViewModel;
import com.example.workspacex.ui.login.LoginViewModelFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void handleSignUp (View view) {
        EditText emailET = findViewById(R.id.editTextEmail);
        String email = emailET.getText().toString();
        EditText pwdET = findViewById(R.id.editTextPassword);
        String pwd = pwdET.getText().toString();
        EditText firstNameET = findViewById(R.id.editTextFirstName);
        String firstName = firstNameET.getText().toString();
        EditText lastNameET = findViewById(R.id.editTextLastName);
        String lastName = lastNameET.getText().toString();
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.43.102:5000/customer/signup";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("pwd", pwd);
        params.put("fn", firstName);
        params.put("ln", lastName);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params), new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response is: " + response);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            public String getBodyContentType()
            {
                return "application/json";
            }
        };
        requestQueue.add(jsonObjReq);
    }

    public  void switchToSignup (View view) {
        View l = findViewById(R.id.login_layout);
        l.setVisibility(View.GONE);
        View reg = findViewById(R.id.register_layout);
        reg.setVisibility(View.VISIBLE);    }

    public void switchToLogin(View view) {
        View l = findViewById(R.id.register_layout);
        l.setVisibility(View.GONE);
        View reg = findViewById(R.id.login_layout);
        reg.setVisibility(View.VISIBLE);
    }
    public void handleSignIn(View view) throws IOException {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.43.102:5000/staff";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
            }
        });

// Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        // Instantiate the RequestQueue




    }
}
