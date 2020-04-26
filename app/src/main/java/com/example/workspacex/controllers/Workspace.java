package com.example.workspacex.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.workspacex.R;

public class Workspace {
    public static View getView(Context context, com.example.workspacex.data.model.Workspace workspace) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.workspace_overview_item, null);
        TextView workspaceName = v.findViewById(R.id.workspace_name);
        TextView workspaceAddress = v.findViewById(R.id.workspace_address);
        TextView workspacePhone = v.findViewById(R.id.workspace_phone);

        workspaceName.setText(workspace.getName());
        workspaceAddress.setText(workspace.getAddress());
        workspacePhone.setText(workspace.getPhoneNumber());


        return v;
    }
}
