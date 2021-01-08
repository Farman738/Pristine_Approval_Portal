package com.example.pristine_approval_portal.ui.all_cardview_fragment.indent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.globalization.LoadingDialog;
import com.example.pristine_approval_portal.sessionManagement.SessionManagement;

import java.util.List;

public class IndentHeaderFragment extends Fragment {

    SessionManagement sessionManagement;
    LoadingDialog loadingDialog;
    ListView listview_indent_header;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indent_header, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        //todo set initial values for indnet
        setinitialvalues(view);


    }

    private void setinitialvalues(View view) {
        //todo listview
        listview_indent_header = view.findViewById(R.id.listview_indent_header);

    }
}