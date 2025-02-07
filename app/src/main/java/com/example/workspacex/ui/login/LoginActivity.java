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

import com.android.volley.AuthFailureError;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

    public void handleSignUp (View view) throws JSONException {
        EditText emailET = findViewById(R.id.editTextEmail_r);
        final String email = emailET.getText().toString();
        EditText pwdET = findViewById(R.id.editTextPassword_r);
        final String pwd = pwdET.getText().toString();
        EditText firstNameET = findViewById(R.id.editTextFirstName);
        final String firstName = firstNameET.getText().toString();
        EditText lastNameET = findViewById(R.id.editTextLastName);
        final String lastName = lastNameET.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.1.108:5000/customer/signup";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("password", pwd);
        jsonBody.put("fn", firstName);
        jsonBody.put("ln", lastName);

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if ((boolean) response.get("success") == true) {
                        Toast.makeText(getApplicationContext(), "Successfully registered. please sign in", Toast.LENGTH_LONG).show();
                        switchToLogin(findViewById(R.id.editTextEmail_r));
                    } else {
                        Toast.makeText(getApplicationContext(), "Error signing up", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(jsonOblect);

        // Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }


    public void switchToSignup (View view) {
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
    public void handleSignIn(View view) throws IOException, JSONException {
        EditText emailET = findViewById(R.id.editTextEmail);
        final String email = emailET.getText().toString();
        EditText pwdET = findViewById(R.id.editTextPassword);
        final String pwd = pwdET.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.1.108:5000/customer/login";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("password", pwd);
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
//        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    if ((boolean) response.get("success") == true) {
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error);
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                final Map<String, String> headers = new HashMap<>();
//                return headers;
//            }
//        };
//        Volley.newRequestQueue(this).add(jsonOblect);


        // Instantiate the RequestQueue




    }
}
