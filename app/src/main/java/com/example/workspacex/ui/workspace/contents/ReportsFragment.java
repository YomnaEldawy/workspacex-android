package com.example.workspacex.ui.workspace.contents;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workspacex.R;
import com.example.workspacex.callbacks.ReviewCallback;
import com.example.workspacex.callbacks.ServerCallback;
import com.example.workspacex.controllers.ReportItem;
import com.example.workspacex.controllers.ReviewItem;
import com.example.workspacex.data.model.LoggedInUser;
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


    private void displayReports(final LinearLayout linearLayout, final Context context) throws JSONException {
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

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(response);

            }
        }, context);
    }

    private void handleReportSection(final TextView textView, final Context context) throws JSONException {
        Reports.getUserStatus(workspace.getId(), LoggedInUser.getUserId(), new ReviewCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
            }
        }, context);
    }

    void handleSendReport(final String reportId, View v, final Context context) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Reports.sendReport(workspace.getId(), LoggedInUser.getUserId(), reportId, new ReviewCallback() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                if (response.getBoolean("success")) {
                                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "There was an error", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(context, "There was an error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void handleSubmitReport(final Context context) {
        TextView textView = root.findViewById(R.id.report_problem_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HorizontalScrollView horizontalScrollView = root.findViewById(R.id.reports_content_hsv);
                horizontalScrollView.setVisibility(View.VISIBLE);
                final LinearLayout linearLayout = root.findViewById(R.id.write_review_content_ll);
                Reports.getReportTypes(new ServerCallback() {
                    @Override
                    public void onSuccess(JSONArray response) {
                        System.out.println(response);
                        for (int i = 0; i < response.length(); i++) {
                            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = vi.inflate(R.layout.item_write_report, null);
                            TextView textView1 = v.findViewById(R.id.problem_text_tv);
                            try {
                                textView1.setText(response.getJSONObject(i).getString("reportDescription"));
                                handleSendReport(response.getJSONObject(i).getString("reportId"), v, context);
                                linearLayout.addView(v);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, context);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_reports, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.reports_content_ll);
        TextView textView = root.findViewById(R.id.report_problem_tv);
        try {
            handleReportSection(textView, getContext());
            displayReports(linearLayout, getContext());
            handleSubmitReport(getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}
