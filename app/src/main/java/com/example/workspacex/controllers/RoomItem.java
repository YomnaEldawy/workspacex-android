package com.example.workspacex.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Room;

public class RoomItem {
    public static View getRoomItem(Context context, Room room) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.room_item, null);
        TextView roomName  = v.findViewById(R.id.room_name);
        TextView description = v.findViewById(R.id.room_description);
        TextView SeatsNumber = v.findViewById(R.id.seats_num);
        TextView pricePerHour = v.findViewById(R.id.price_per_hour_value_tv);
        TextView pricePerDay = v.findViewById(R.id.price_per_day_value_tv);

        roomName.setText("Room " + room.getRoomId());
        description.setText(room.getDescription());
        SeatsNumber.setText(room.getNumberOfSeats()+ "");
        pricePerHour.setText((int) room.getPricePerHour()+ "EGP/Hour");
        pricePerDay.setText((int) room.getPricePerDay()+ "EGP/Day");
        return v;
    }
}
