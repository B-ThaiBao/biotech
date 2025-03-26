package com.hcmut.assignment.biotech;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private List<ImageData> dataList;
    private ImageView imageView;
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

        imageView = view.findViewById(R.id.image_view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        MaterialButton moreButton = view.findViewById(R.id.more_button);
        moreButton.setOnClickListener(v -> {
            if (moreButton.isChecked()) {
                view.findViewById(R.id.main_fragment_layout).setBackground(colorDrawable(ContextCompat.getColor(requireContext(), R.color.dark_grey)));
                moreButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.light_blue));

                recyclerView.setVisibility(ViewGroup.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                dataList = parseJsonData();
                ButtonAdapter adapter = new ButtonAdapter(getContext(), dataList, this::loadImageFromAssets);
                recyclerView.setAdapter(adapter);
            } else {
                view.findViewById(R.id.main_fragment_layout).setBackground(colorDrawable(ContextCompat.getColor(requireContext(), R.color.grey)));
                moreButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white));
                recyclerView.setVisibility(ViewGroup.GONE);
            }
        });
        return view;
    }

    private void loadImageFromAssets(String fileName) {
        try {
            InputStream is = getContext().getAssets().open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            imageView.setVisibility(ViewGroup.VISIBLE);
            imageView.setImageBitmap(bitmap);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = requireContext().getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private List<ImageData> parseJsonData() {
        List<ImageData> dataList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ImageData>>() {}.getType();
            dataList = gson.fromJson(loadJSONFromAsset(), listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private GradientDrawable colorDrawable(int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        return drawable;
    }
}