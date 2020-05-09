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
import com.example.workspacex.controllers.ReportItem;
import com.example.workspacex.controllers.ReviewItem;
import com.example.workspacex.data.model.Report;
import com.example.workspacex.data.model.Review;
import com.example.workspacex.data.model.Workspace;
import com.example.workspacex.endpoints.Reports;
import com.example.workspacex.endpoints.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment {
    static Workspace workspace;
    public ReportsFragment(Workspace ws) {
        workspace = ws;
    }

    View root;


    public static void displayReviews(final LinearLayout linearLayout, final Context context) throws JSONException {
        Reports.getFromDB(workspace.getId(), new ServerCallback() {
            @Override
            public void onSuccess(final JSONArray response) {
                System.out.println(workspace.getId());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.optJSONObject(i);
                        Report r = new Report();
                        r.setReportDescription(jsonObject.getString("reportDescription"));
                        r.setReportsNum(jsonObject.getInt("count"));

                        View v = ReportItem.getReportItem(context, r);
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
        root = inflater.inflate(R.layout.fragment_reports, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.reports_content_ll);
        try {
            displayReviews(linearLayout, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  root;
    }
}
