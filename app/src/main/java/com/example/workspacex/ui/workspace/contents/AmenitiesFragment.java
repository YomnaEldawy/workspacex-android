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
import com.example.workspacex.controllers.AmenityItem;
import com.example.workspacex.data.model.Amenity;
import com.example.workspacex.data.model.Workspace;
import com.example.workspacex.endpoints.Amenities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmenitiesFragment extends Fragment {

    static Workspace workspace;
    View root;

    public AmenitiesFragment(Workspace ws) {
        workspace = ws;
    }

    private static void displayRooms(final LinearLayout linearLayout, final Context context) throws JSONException {
        Amenities.getFromDB(workspace.getId(), new ServerCallback() {
            @Override
            public void onSuccess(final JSONArray response) {
                try{
                    int []rooms = new int[response.length()];
                    String []AmenitiesName = new String[response.length()];
                    int []NumberOfAmenities = new int[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.optJSONObject(i);
                        rooms[i] = jsonObject.getInt("roomId");
                        AmenitiesName[i] = jsonObject.getString("amenityName");
                        NumberOfAmenities[i] = jsonObject.getInt("numberOfAmenities");
                    }
                    for (int i = 0; i < response.length(); i++) {
                        Amenity r = new Amenity();
                        if(rooms[i] != -1) {
                            r.setRoomId(rooms[i]);
                            String temp = AmenitiesName[i] + " (" + NumberOfAmenities[i] + ")\n";
                            for (int j = i + 1; j < response.length(); j++) {
                                if ((rooms[i] == rooms[j]) && (rooms[i] != -1)) {
                                    temp = temp + AmenitiesName[j] + " (" + NumberOfAmenities[j] + ")\n";
                                    rooms[j] = -1;
                                }
                            }
                            r.setAmenityDetails(temp);
                            View v = AmenityItem.getAmenityItem(context, r);
                            linearLayout.addView(v);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }
        }, context);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_amenities, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.amenities_content_ll);
        try {
            displayRooms(linearLayout, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}
