package com.example.workspacex.ui.workspace.contents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workspacex.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteReportFragment extends Fragment {
    View root;
    public WriteReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_write_report, container, false);

        return root;
    }
}
