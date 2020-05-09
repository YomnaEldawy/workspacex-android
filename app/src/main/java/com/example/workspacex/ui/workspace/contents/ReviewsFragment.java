package com.example.workspacex.ui.workspace.contents;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.controllers.ReviewItem;
import com.example.workspacex.data.model.Review;
import com.example.workspacex.data.model.Workspace;
import com.example.workspacex.endpoints.Reviews;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {
    static Workspace workspace;

    public ReviewsFragment(Workspace ws) {
        workspace = ws;
    }

    public static void displayReviews(final LinearLayout linearLayout, final Context context) throws JSONException {
        Reviews.getFromDB(workspace.getId(), new ServerCallback() {
            @Override
            public void onSuccess(final JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.optJSONObject(i);
                        Review r = new Review();
                        r.setFirstName(jsonObject.getString("firstName"));
                        r.setLastName(jsonObject.getString("lastName"));
                        r.setComment(jsonObject.getString("comment"));
                        r.setStarRating(jsonObject.getDouble("starRating"));
                        View v = ReviewItem.getReviewItem(context, r);
                        System.out.println(v);
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
        View root = inflater.inflate(R.layout.fragment_reviews, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.reviews_content_ll);
        try {
            displayReviews(linearLayout, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}
