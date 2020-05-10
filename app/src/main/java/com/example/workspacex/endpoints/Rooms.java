package com.example.workspacex.endpoints;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.config.ServerIP;

import org.json.JSONArray;
import org.json.JSONException;

public class Rooms {
    public static void getFromDB(String workspaceId, final ServerCallback callback, Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url = ServerIP.getIP() + "/customer_view/room/" + workspaceId  ;
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
