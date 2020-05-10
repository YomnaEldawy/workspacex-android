package com.example.workspacex.endpoints;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.workspacex.callbacks.ReviewCallback;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.config.ServerIP;
import com.example.workspacex.data.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Reviews {

    public static void postReview(boolean newReview, String workspaceId, String customerId, String comment, double starRating, final ReviewCallback callback, Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url;
        JSONObject jsonBody;
        jsonBody = new JSONObject();
        jsonBody.put("customerId", customerId);
        jsonBody.put("starRating", starRating);
        jsonBody.put("comment", comment);

        int method = Request.Method.POST;
        if (newReview) {
            url = ServerIP.getIP() + "/review/" + workspaceId;
        } else {
            url = ServerIP.getIP() + "/review/";
            jsonBody.put("workspaceId", workspaceId);
            method = Request.Method.PUT;
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,
                url, jsonBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response); // call call back function here
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(jsonObjReq);
    }


    public static void getUserStatus(String workspaceId, String customerId, final ReviewCallback callback, Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url = ServerIP.getIP() + "/review/customer-status/" + workspaceId + "/" + customerId;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response); // call call back function here
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(jsonObjReq);
    }

    public static void getFromDB(String workspaceId, final ServerCallback callback, Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url = ServerIP.getIP() + "/customer_view/reviews/" + workspaceId;
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, new JSONArray(), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("getting response");
                callback.onSuccess(response); // call call back function here

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(jsonObjReq);
    }


}
