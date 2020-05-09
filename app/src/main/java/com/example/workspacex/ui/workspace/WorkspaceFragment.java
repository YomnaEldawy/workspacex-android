package com.example.workspacex.ui.workspace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Workspace;
import com.example.workspacex.ui.workspace.contents.BasicInfoFragment;
import com.example.workspacex.ui.workspace.contents.ReportsFragment;
import com.example.workspacex.ui.workspace.contents.ReviewsFragment;
import com.google.android.gms.maps.MapView;


public class WorkspaceFragment extends Fragment{
    private MapView mMap;
    static Workspace workspace;
    View root;

    public WorkspaceFragment (Workspace ws) {
        workspace = ws;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.workspace_fragment, container, false);
        setFragment(new BasicInfoFragment(workspace));
        setTabs();
        return root;
    }

    private void setTabs() {
        TextView basicInfo = root.findViewById(R.id.basic_info_tab);
        TextView reviews = root.findViewById(R.id.reviews_tab);
        TextView reports = root.findViewById(R.id.reports_tab);

        connectTabToFragment(basicInfo, new BasicInfoFragment(workspace));
        connectTabToFragment(reviews, new ReviewsFragment(workspace));
        connectTabToFragment(reports, new ReportsFragment(workspace));
    }

    private void connectTabToFragment(View v,final Fragment f){

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(f);
            }
        });
    }

    public void setFragment(Fragment f) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.workspace_profile_content, f);
        fragmentTransaction.commit();
    }
}
