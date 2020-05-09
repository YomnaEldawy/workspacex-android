package com.example.workspacex.ui.workspace.contents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workspacex.R;
import com.example.workspacex.data.model.Workspace;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    Workspace workspace;
    public EventsFragment (Workspace ws) {
        workspace = ws;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }
}
