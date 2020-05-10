package com.example.workspacex.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Amenity;

public class AmenityItem {
        public static View getAmenityItem(Context context, Amenity amenity) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.amenity_item, null);
            TextView roomName  = v.findViewById(R.id.room_name);
            TextView amenityDetails = v.findViewById(R.id.room_amenity_name);

            roomName.setText("Room " + amenity.getRoomId());
            amenityDetails.setText(amenity.getAmenityDetails());
            return v;
        }
}
