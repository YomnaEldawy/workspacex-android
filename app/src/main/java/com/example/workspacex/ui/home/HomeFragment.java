package com.example.workspacex.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.workspacex.HomeActivity;
import com.example.workspacex.R;
import com.example.workspacex.controllers.Workspace;

import org.json.JSONException;

import java.util.concurrent.Semaphore;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        HomeActivity.getInstance().clear();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout linearLayout = root.findViewById(R.id.workspaces_container);
        System.out.println("Creating fragment..");
        com.example.workspacex.data.model.Workspace w = new com.example.workspacex.data.model.Workspace();
        try {
            com.example.workspacex.data.model.Workspace.displayWorkspaces("", getContext(), linearLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
}
