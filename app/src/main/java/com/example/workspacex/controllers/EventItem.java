package com.example.workspacex.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Event;
import com.example.workspacex.data.model.Report;

public class EventItem {

    public static View getEventItem(Context context, Event event) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.event_item, null);
        TextView name = v.findViewById(R.id.event_name);
        TextView description = v.findViewById(R.id.event_description);
        TextView start = v.findViewById(R.id.start_date_tv);
        TextView end = v.findViewById(R.id.end_date_tv);
        TextView fees = v.findViewById(R.id.price_value_tv);

        name.setText(event.getName());
        description.setText(event.getDescription());
        start.setText(event.getStartsAt());
        end.setText(event.getEndsAt());
        fees.setText(event.getFees() + " EGP");
        return v;
    }
}
