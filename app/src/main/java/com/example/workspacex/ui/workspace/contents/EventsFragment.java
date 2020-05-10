package com.example.workspacex.ui.workspace.contents;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.workspacex.R;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.controllers.EventItem;
import com.example.workspacex.controllers.ReviewItem;
import com.example.workspacex.data.model.Event;
import com.example.workspacex.data.model.Review;
import com.example.workspacex.data.model.Room;
import com.example.workspacex.data.model.Workspace;
import com.example.workspacex.endpoints.Events;
import com.example.workspacex.endpoints.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    static Workspace workspace;
    View root;
    public EventsFragment (Workspace ws) {
        workspace = ws;
    }

    private static void displayReviews(final LinearLayout linearLayout, final Context context) throws JSONException {
        Events.getFromDB(workspace.getId(), new ServerCallback() {
            @Override
            public void onSuccess(final JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.optJSONObject(i);
                        Event e = new Event();
                        e.setName(jsonObject.getString("eventName"));
                        e.setDescription(jsonObject.getString("description"));
                        e.setFees(jsonObject.getString("fees"));
                        e.setStartsAt(jsonObject.getString("startsAt"));
                        e.setEndsAt(jsonObject.getString("endsAt"));
                        e.setVersion(jsonObject.getString("version"));
                        View v = EventItem.getEventItem(context, e);
                        linearLayout.addView(v);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(response);

            }
        }, context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_events, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.events_content_ll);
        try {
            displayReviews(linearLayout, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}

