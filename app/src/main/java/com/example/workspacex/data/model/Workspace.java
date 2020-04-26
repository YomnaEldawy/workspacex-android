package com.example.workspacex.data.model;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.workspacex.HomeActivity;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.ui.workspace.WorkspaceFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Workspace {

    public Workspace() {

    }

    public static void displayWorkspaces(String id, final Context context, final LinearLayout l) throws JSONException, InterruptedException {
        getFromDB(id, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                        JSONObject cur = response.optJSONObject(i);
                        final Workspace w = new Workspace();
                        w.setName((String) (cur.get("name") + ""));
                        w.setStreetName((String) cur.get("streetName") + "");
                        w.setStreetNumber((String) cur.get("streetNumber") + "");
                        w.setCity((String) cur.get("city") +"");
                        w.setPhoneNumber((String) cur.get("phone")+"");
                        View wsView = com.example.workspacex.controllers.Workspace.getView(context, w);
                        wsView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HomeActivity.getInstance().setFragment(new WorkspaceFragment(w));
                            }
                        });
                        l.addView(wsView);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }

                System.out.println(response);
            }
        }, context);
    }

    private static void getFromDB (String id, final ServerCallback callback, Context context) throws JSONException {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(context);
        String url ="http://192.168.1.108:5000/workspace/" + id;
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url, new JSONArray(), new Response.Listener<JSONArray>()
        {

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

    public Workspace(int id) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getAddress() {
        return streetNumber + " " + streetName + ", " + city;
    }

    String id;
    private String name;
    private String city, district, streetName, streetNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

}
