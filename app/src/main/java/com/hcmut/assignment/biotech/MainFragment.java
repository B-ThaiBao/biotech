package com.hcmut.assignment.biotech;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public MainFragment() {
        // Required empty public constructor
    }
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button practiceButton = view.findViewById(R.id.practice_button);
        practiceButton.setOnClickListener(v -> {
            FooterTitle.pushBack(getString(R.string.vie_practice));

            // To fragment_practice
            PracticeFragment practiceFragment = PracticeFragment.newInstance();
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, practiceFragment).addToBackStack(null).commit();
        });

        MaterialButton moreButton = view.findViewById(R.id.more_button);
        moreButton.setOnClickListener(v -> {
            if (moreButton.isChecked()) {
                GradientDrawable drawable = new GradientDrawable();
                int colorDarkGrey = ContextCompat.getColor(requireContext(), R.color.dark_grey);
                drawable.setColor(colorDarkGrey);
                view.findViewById(R.id.main_fragment_layout).setBackground(drawable);
            } else {
                GradientDrawable drawable = new GradientDrawable();
                int colorDarkGrey = ContextCompat.getColor(requireContext(), R.color.grey);
                drawable.setColor(colorDarkGrey);
                view.findViewById(R.id.main_fragment_layout).setBackground(drawable);
            }
        });
        return view;
    }
}