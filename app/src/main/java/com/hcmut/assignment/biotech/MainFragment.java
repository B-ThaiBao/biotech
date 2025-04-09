package com.hcmut.assignment.biotech;

import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import android.provider.ContactsContract;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    int cur_position = -1;
    private SceneView sceneView;

//    private ImageView imageView;
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
            getParentFragmentManager().beginTransaction().add(R.id.fragment_container, practiceFragment).addToBackStack(null).commit();
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        MaterialButton moreButton = view.findViewById(R.id.more_button);
        if (Database.dataList == null) {
            Database.dataList = parseJsonData();
            Database.modelNodes = new ArrayList<>(Collections.nCopies(Database.dataList.size(), null));
            Database.titleNodes = new ArrayList<>(Collections.nCopies(Database.dataList.size(), null));
        }
        moreButton.setOnClickListener(v -> {
            if (moreButton.isChecked()) {
//                view.findViewById(R.id.main_fragment_layout).setBackground(colorDrawable(ContextCompat.getColor(requireContext(), R.color.dark_grey)));
                moreButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white_yellow));

                recyclerView.setVisibility(ViewGroup.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                ButtonAdapter adapter = new ButtonAdapter(getContext(), this::loadModelFromAssets);
                recyclerView.setAdapter(adapter);
            } else {
//                view.findViewById(R.id.main_fragment_layout).setBackground(colorDrawable(ContextCompat.getColor(requireContext(), R.color.grey)));
                moreButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white));
                recyclerView.setVisibility(ViewGroup.GONE);
            }
        });

        sceneView = view.findViewById(R.id.scene_view);

        view.findViewById(R.id.big_ar).setOnClickListener(v -> {

            // To ArCameraFragment
            if (cur_position == -1) {
                Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_SHORT).show();
            } else {
                FooterTitle.pushBack(getString(R.string.reality));
                ArCameraFragment arCameraFragment = ArCameraFragment.newInstance(cur_position);
                getParentFragmentManager().beginTransaction().add(R.id.fragment_container, arCameraFragment).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.circle_ar).setOnClickListener(v -> {

            // To ArCameraFragment
            if (cur_position == -1) {
                Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_SHORT).show();
            } else {
                FooterTitle.pushBack(getString(R.string.reality));
                ArCameraFragment arCameraFragment = ArCameraFragment.newInstance(cur_position);
                getParentFragmentManager().beginTransaction().add(R.id.fragment_container, arCameraFragment).addToBackStack(null).commit();
            }
        });

        view.findViewById(R.id.change_pos_button).setOnClickListener(v -> {

            // To MonitorModelFragment
            if (cur_position == -1) {
                Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_SHORT).show();
            } else {
                FooterTitle.pushBack(getString(R.string.change_pos));
                MonitorModelFragment monitorModelFragment = MonitorModelFragment.newInstance(cur_position);
                getParentFragmentManager().beginTransaction().add(R.id.fragment_container, monitorModelFragment).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.circle_change_pos_button).setOnClickListener(v -> {

            // To MonitorModelFragment
            if (cur_position == -1) {
                Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_SHORT).show();
            } else {
                FooterTitle.pushBack(getString(R.string.change_pos));
                MonitorModelFragment monitorModelFragment = MonitorModelFragment.newInstance(cur_position);
                getParentFragmentManager().beginTransaction().add(R.id.fragment_container, monitorModelFragment).addToBackStack(null).commit();
            }
        });

        view.findViewById(R.id.fast_change_pos_button).setOnClickListener(v -> {

            // To MonitorModelFragment
            if (cur_position == -1) {
                Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_SHORT).show();
            } else {
                FooterTitle.pushBack(getString(R.string.change_pos));
                MonitorModelFragment monitorModelFragment = MonitorModelFragment.newInstance(cur_position);
                getParentFragmentManager().beginTransaction().add(R.id.fragment_container, monitorModelFragment).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.circle_fast_change_pos_button).setOnClickListener(v -> {

            // To MonitorModelFragment
            if (cur_position == -1) {
                Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_SHORT).show();
            } else {
                FooterTitle.pushBack(getString(R.string.change_pos));
                MonitorModelFragment monitorModelFragment = MonitorModelFragment.newInstance(cur_position);
                getParentFragmentManager().beginTransaction().add(R.id.fragment_container, monitorModelFragment).addToBackStack(null).commit();
            }
        });

        MaterialButton infoButton = view.findViewById(R.id.info_button);
        infoButton.setOnClickListener(v -> {
            if (infoButton.isChecked()) {
                infoButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white_yellow));

                if (cur_position == -1 || Database.modelNodes.get(cur_position) == null) {
                    Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_LONG).show();
                } else {
                    ViewRenderable.builder()
                            .setView(requireContext(), R.layout.view_model_title)
                            .build()
                            .thenAccept(viewRenderable -> {
                                TextView textView = viewRenderable.getView().findViewById(R.id.title_textview);
                                textView.setText(Database.dataList.get(cur_position).title);
                                Database.titleNodes.set(cur_position, new Node());
                                Database.titleNodes.get(cur_position).setParent(Database.modelNodes.get(cur_position));
                                Database.titleNodes.get(cur_position).setEnabled(false);
                                Database.titleNodes.get(cur_position).setLocalPosition(new Vector3(0.0f, 1.0f, 0.0f));
                                Database.titleNodes.get(cur_position).setRenderable(viewRenderable);
                                Database.titleNodes.get(cur_position).setEnabled(true);
                            })
                            .exceptionally(throwable -> {
                                Toast.makeText(requireContext(), "Please choose the model", Toast.LENGTH_LONG).show();
                                return null;
                            });
                }
            } else {
                infoButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white));
                Node titleNode = Database.titleNodes.get(cur_position);
                if (titleNode != null) {
                    titleNode.setParent(null);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            sceneView.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sceneView.pause();
    }

    private void loadModelFromAssets(int position, String fileName) {
        sceneView.setVisibility(View.VISIBLE);
        if (Database.modelNodes.get(position) == null) {
            CompletableFuture<ModelRenderable> model = ModelRenderable
                    .builder()
                    .setSource(requireContext(), Uri.parse("models/" + fileName))
                    .setIsFilamentGltf(true)
                    .setAsyncLoadEnabled(true)
                    .build();

            CompletableFuture.allOf(model).handle((ok, ex) -> {
                try {
                    Database.modelNodes.set(position, new Node());
                    Database.modelNodes.get(position).setRenderable(model.get());
                    Database.modelNodes.get(position).setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
                    Database.modelNodes.get(position).setLocalRotation(Quaternion.multiply(
                            Quaternion.axisAngle(new Vector3(1f, 0f, 0f), 45),
                            Quaternion.axisAngle(new Vector3(0f, 1f, 0f), 75)));
                    Database.modelNodes.get(position).setLocalPosition(new Vector3(0f, 0f, -1.0f));

                    // Add model to current scene
                    if (cur_position != -1) {
                        sceneView.getScene().removeChild(Database.modelNodes.get(cur_position));
                    }
                    cur_position = position;
                    sceneView.getScene().addChild(Database.modelNodes.get(position));

                    setupGestureControl(position);
                    autoUpdateRotation();
                } catch (InterruptedException | ExecutionException ignore) {

                }
                return null;
            });
        } else {
            // This model already download before, just load it
            if (cur_position != -1) {
                sceneView.getScene().removeChild(Database.modelNodes.get(cur_position));
            }
            cur_position = position;
            sceneView.getScene().addChild(Database.modelNodes.get(position));

            setupGestureControl(position);
            autoUpdateRotation();
        }
    }

    private void autoUpdateRotation() {
        sceneView.getScene().addOnUpdateListener(frameTime -> {
            Quaternion rotationY = Quaternion.axisAngle(new Vector3(0f, 1f, 0f), 15f * frameTime.getDeltaSeconds());

            Quaternion rotationX = Database.modelNodes.get(cur_position).getLocalRotation();
            Quaternion combinedRotation = Quaternion.multiply(rotationX, rotationY);

            Database.modelNodes.get(cur_position).setLocalRotation(combinedRotation);
        });
    }

    private void setupGestureControl(int position) {
        GestureDetector gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
            private float rotationAngle = 0f;

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (Database.modelNodes.get(position) != null) {
                    float rotationSpeed = 0.5f;
                    rotationAngle += distanceX * rotationSpeed;
                    Database.modelNodes.get(position).setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0f), rotationAngle));
                }
                return true;
            }
        });

        sceneView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
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