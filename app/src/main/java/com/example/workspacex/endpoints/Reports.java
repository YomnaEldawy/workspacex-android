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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Reports {
    public static void sendReport(final String workspaceId, final String customerId, final String reportId, final ReviewCallback callback, final Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url = ServerIP.getIP() + "/report/";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("workspaceId", workspaceId);
        jsonBody.put("customerId", customerId);
        jsonBody.put("reportType", reportId);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
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

    public static void getUserStatus(final String workspaceId, final String customerId, final ReviewCallback callback, final Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url = ServerIP.getIP() + "/report/status";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("workspaceId", workspaceId);
        jsonBody.put("customerId", customerId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
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
    public static void getReportTypes(final ServerCallback callback, Context context) {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url = ServerIP.getIP() + "/report/types";
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, new JSONArray(), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("getting report types");
                System.out.println(response);
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
        String url = ServerIP.getIP() + "/customer_view/reviews-by-count/" + workspaceId;
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

