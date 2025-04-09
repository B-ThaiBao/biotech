package com.hcmut.assignment.biotech;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.BaseTransformableNode;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.SelectionVisualizer;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;
import com.hcmut.assignment.joystick.JoystickView;
import com.hcmut.assignment.scalejoystick.ScaleJoystickView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonitorModelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonitorModelFragment extends Fragment {
    int cur_position = -1;
    private SceneView monitorSceneView;

    public MonitorModelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            monitorSceneView.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        monitorSceneView.pause();
    }

    public static MonitorModelFragment newInstance(int position) {
        MonitorModelFragment fragment = new MonitorModelFragment();
        Bundle args = new Bundle();
        args.putInt("cur_position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cur_position = getArguments().getInt("cur_position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitor_model, container, false);

        JoystickView joystickView = view.findViewById(R.id.joystick_view);
        ScaleJoystickView scaleJoystickView = view.findViewById(R.id.scale_joystick_view);
        monitorSceneView = view.findViewById(R.id.monitor_scene_view);

        joystickView.setJoystickListener((xPercent, yPercent) -> {
            Vector3 pos = Database.modelNodes.get(cur_position).getLocalPosition();
            Database.modelNodes.get(cur_position).setLocalPosition(new Vector3(pos.x + xPercent * JoystickView.SPEED, pos.y - yPercent * JoystickView.SPEED, pos.z));
        });
        scaleJoystickView.setScaleJoystickListener(yPercent -> {
            float cur_scale = Database.modelNodes.get(cur_position).getLocalScale().x;
            float delta = (0.5f - yPercent) * 2f;
            float newScale = cur_scale + delta * 0.05f;

            newScale = Math.max(0.1f, Math.min(newScale, 0.9f));
            Database.modelNodes.get(cur_position).setLocalScale(new Vector3(newScale, newScale, newScale));
        });
        monitorSceneView.getScene().addChild(Database.modelNodes.get(cur_position));

        setupGestureControl(cur_position);
        autoUpdateRotation();
        return view;
    }

    private void autoUpdateRotation() {
        monitorSceneView.getScene().addOnUpdateListener(frameTime -> {
            Quaternion rotationY = Quaternion.axisAngle(new Vector3(0f, 1f, 0f), 10f * frameTime.getDeltaSeconds());

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
                    float rotationSpeed = 0.5f; // Điều chỉnh tốc độ xoay
                    rotationAngle += distanceX * rotationSpeed;

                    // Xoay mô hình theo trục Y khi vuốt ngang
                    Database.modelNodes.get(position).setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 1f, 0f), rotationAngle));
                }
                return true;
            }
        });

        monitorSceneView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }
}