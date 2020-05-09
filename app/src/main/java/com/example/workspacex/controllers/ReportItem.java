package com.example.workspacex.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Report;

public class ReportItem {
    public static View getReportItem(Context context, Report report) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.report_item, null);
        TextView problem = v.findViewById(R.id.report_description_tv);
        TextView count = v.findViewById(R.id.reports_num);
        problem.setText(report.getReportDescription());
        count.setText(report.getReportsNum() + "");
        return v;
    }

}
