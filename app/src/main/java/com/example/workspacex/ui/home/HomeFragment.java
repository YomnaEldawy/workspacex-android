package com.example.workspacex.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.workspacex.HomeActivity;
import com.example.workspacex.R;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.controllers.Workspace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Semaphore;

public class HomeFragment extends Fragment {
    private MapView mMap;

    private HomeViewModel homeViewModel;
    public static void displayWorkspacesOnMap(final MapView map, final Context context) throws JSONException {
        com.example.workspacex.data.model.Workspace.getFromDB("", new ServerCallback() {
            @Override
            public void onSuccess(final JSONArray response) {
                map.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        double totalLat = 0, totalLng = 0;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject cur = response.optJSONObject(i);
                                float lat = Float.parseFloat(cur.get("latitude") + "");
                                float lng = Float.parseFloat(cur.get("longitude") + "");
                                String name = cur.get("name") + "";
                                totalLat += lat;
                                totalLng += lng;
                                LatLng wsLocation = new LatLng(lat, lng);
                                googleMap.addMarker(new MarkerOptions().position(wsLocation).title(name));
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                        totalLat /= response.length();
                        totalLng /= response.length();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(totalLat, totalLng), 12f));
                    }
                });

            }
        }, context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        HomeActivity.getInstance().clear();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mMap = root.findViewById(R.id.all_ws_map);
        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                LatLng wsLocation = new LatLng(29.0, 31.1);
            }
        });
        try {
            displayWorkspacesOnMap(mMap, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayout linearLayout = root.findViewById(R.id.workspaces_container);
        System.out.println("Creating fragment..");
        com.example.workspacex.data.model.Workspace w = new com.example.workspacex.data.model.Workspace();
        try {
            com.example.workspacex.data.model.Workspace.displayWorkspaces("", getContext(), linearLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
}
