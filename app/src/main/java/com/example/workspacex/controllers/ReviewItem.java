package com.example.workspacex.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Review;


public class ReviewItem {

    public static View getReviewItem(Context context, Review review) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.item_review, null);
        TextView customerName = v.findViewById(R.id.customer_name);
        TextView comment = v.findViewById(R.id.comment_tv);
        TextView customerThumbnail = v.findViewById(R.id.customer_thumbnail);
        RatingBar ratingBar = v.findViewById(R.id.workspace_star_rating);

        customerName.setText(review.getFirstName() + " " + review.getLastName());
        comment.setText(review.getComment());

        customerThumbnail.setText((review.getFirstName().charAt(0) + "").toUpperCase());
        ratingBar.setNumStars((int) review.getStarRating());
        return v;
    }

}
