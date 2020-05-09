package com.example.workspacex.ui.workspace.contents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Workspace;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class BasicInfoFragment extends Fragment {
    static Workspace workspace;
    private MapView mMap;

    public BasicInfoFragment (Workspace ws) {
        workspace = ws;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_basic_info, container, false);
        TextView name = root.findViewById(R.id.workspace_name);
        mMap = root.findViewById(R.id.ws_map);
        mMap.onCreate(savedInstanceState);
        mMap.onResume();
        mMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                LatLng wsLocation = new LatLng(workspace.getLatitude(), workspace.getLongitude());
                mMap.addMarker(new MarkerOptions().position(wsLocation).title(workspace.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wsLocation, 15f));
            }
        });
        TextView address = root.findViewById(R.id.workspace_address);
        TextView phone = root.findViewById(R.id.workspace_phone);

        name.setText(workspace.getName());
        address.setText(workspace.getAddress());
        phone.setText(workspace.getPhoneNumber());
        return root;
    }
}
