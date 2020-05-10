package com.example.workspacex.endpoints;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.workspacex.callbacks.PhotosCallback;
import com.example.workspacex.callbacks.ReviewCallback;
import com.example.workspacex.config.ServerIP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Photos {
    public static void getWorkspacePhotos(String workspaceId, final PhotosCallback callback, Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url = ServerIP.getIP() + "/images/" + workspaceId;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response);
                    JSONArray result = response.getJSONArray("result");
                    JSONArray photos = response.getJSONArray("photos");
                    callback.onSuccess(result, photos); // call call back function here

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
