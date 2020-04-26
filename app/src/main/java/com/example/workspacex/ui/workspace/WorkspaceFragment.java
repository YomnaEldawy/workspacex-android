package com.example.workspacex.ui.workspace;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Workspace;

public class WorkspaceFragment extends Fragment {

    private WorkspaceViewModel mViewModel;
    static Workspace workspace;

    public WorkspaceFragment (Workspace ws) {
        workspace = ws;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.workspace_fragment, container, false);
        TextView name = root.findViewById(R.id.workspace_name);
        TextView address = root.findViewById(R.id.workspace_address);
        TextView phone = root.findViewById(R.id.workspace_phone);
        TextView workspaceLatitude = root.findViewById(R.id.workspace_latitude);
        TextView workspaceLongitude = root.findViewById(R.id.workspace_longitude);

        name.setText(workspace.getName());
        address.setText(workspace.getAddress());
        phone.setText(workspace.getPhoneNumber());
        workspaceLatitude.setText(workspace.getLatitude() + "");
        workspaceLongitude.setText(workspace.getLongitude() + "");
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WorkspaceViewModel.class);
        // TODO: Use the ViewModel
    }

}
