package com.example.workspacex.ui.workspace.contents;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.workspacex.R;
import com.example.workspacex.callbacks.ReviewCallback;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.controllers.ReviewItem;
import com.example.workspacex.data.model.LoggedInUser;
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

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {
    static Workspace workspace;
    View root;
    public ReviewsFragment(Workspace ws) {
        workspace = ws;
    }

    private static void displayReviews(final LinearLayout linearLayout, final Context context) throws JSONException {
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

    private void handleReviewSection(final RatingBar ratingBar, final RelativeLayout relativeLayout, final EditText editText, final Context context) throws JSONException {
        Reviews.getUserStatus(workspace.getId(), LoggedInUser.getUserId(), new ReviewCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                System.out.println(response);
                try {
                    if (response.getBoolean("success")) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        if (response.getInt("count") > 0) {
                            editText.setText(response.getString("comment"));
                            ratingBar.setRating((float)response.getDouble("starRating"));
                            handleSubmit(context, false);
                        } else {
                            editText.setText("");
                            editText.setHint("Write your review");
                            handleSubmit(context, true);
                        }
                    } else {
                        relativeLayout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, context);
    }

    void handleSubmit(final Context context, final boolean newReview) {
        Button button = root.findViewById(R.id.submit_review);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final EditText editText = root.findViewById(R.id.review_text_et);
                    final String comment = editText.getText().toString();
                    final RatingBar ratingBar = root.findViewById(R.id.workspace_input_rating);
                    Reviews.postReview(newReview, workspace.getId(), LoggedInUser.getUserId(), comment, (double) ratingBar.getRating(), new ReviewCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            System.out.print("[Submit review]: ");
                            try {
                                if (response.getBoolean("success")) {
                                    Toast.makeText(context, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "There was an error", Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e) {
                                Toast.makeText(context, "There was an error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, context);

                }catch (Exception e) {

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_reviews, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.reviews_content_ll);
        RelativeLayout relativeLayout = root.findViewById(R.id.review_input_rl);
        EditText editText = root.findViewById(R.id.review_text_et);
        RatingBar ratingBar = root.findViewById(R.id.workspace_input_rating);
        try {
            displayReviews(linearLayout, getContext());
            handleReviewSection(ratingBar, relativeLayout, editText, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}
