package com.hcmut.assignment.biotech;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.Sceneform;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArCameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArCameraFragment extends Fragment {
    private ArFragment arFragment;
    public int cur_position = -1;

    public ArCameraFragment() {
        // Required empty public constructor
    }
    public static ArCameraFragment newInstance(int position) {
        ArCameraFragment fragment = new ArCameraFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ar_camera, container, false);

        arFragment = (ArFragment) getChildFragmentManager().findFragmentById(R.id.ar_fragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();

            if (Database.modelNodes.get(cur_position) == null) {
                Toast.makeText(requireContext(), "Cannot load the model. Please download it !", Toast.LENGTH_SHORT).show();
            }

            AnchorNode anchorNode = new AnchorNode(anchor);

            // Create the transformable model and add it to the anchor.
            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
            node.setParent(anchorNode);
            node.setLocalPosition(Database.modelNodes.get(cur_position).getLocalPosition());
            node.setLocalRotation(Database.modelNodes.get(cur_position).getLocalRotation());
            node.setLocalScale(Database.modelNodes.get(cur_position).getLocalScale());
            node.setRenderable(Database.modelNodes.get(cur_position).getRenderable());
            arFragment.getArSceneView().getScene().addChild(anchorNode);
            node.select();
        });

        return view;
    }
}