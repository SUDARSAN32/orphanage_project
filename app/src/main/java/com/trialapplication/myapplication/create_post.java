package com.trialapplication.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class create_post extends Fragment {

    private View rootView;
    private LinearLayout fragmentContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.create_post, container, false);
        fragmentContainer = rootView.findViewById(R.id.fragment_container);


        addPostFragment();

        return rootView;
    }

    public void addPostFragment() {

        View postFragmentView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_post_fragment, fragmentContainer, false);

        fragmentContainer.addView(postFragmentView);
    }
}
