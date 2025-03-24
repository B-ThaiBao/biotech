package com.hcmut.assignment.biotech;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.findViewById(R.id.find_space_layout).setOnClickListener(v -> {
            FooterTitle.pushBack(getString(R.string.main_screen));
            MainFragment mainFragment = MainFragment.newInstance();
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).addToBackStack(null).commit();
        });

        view.findViewById(R.id.find_img_layout).setOnClickListener(v -> {
            FooterTitle.pushBack(getString(R.string.main_screen));
            MainFragment mainFragment = MainFragment.newInstance();
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).addToBackStack(null).commit();
        });
        return view;
    }
}