package com.hcmut.assignment.biotech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeFragment extends Fragment {
	private PreviewView previewView;
	private ExecutorService cameraExecutor;

	public PracticeFragment() {
		// Required empty public constructor
	}
	public static PracticeFragment newInstance() {
		return new PracticeFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("MissingInflatedId")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_practice, container, false);

		previewView = view.findViewById(R.id.preview_view);
		cameraExecutor = Executors.newSingleThreadExecutor();

		Button endButton = view.findViewById(R.id.end_camera);
		endButton.setOnClickListener(v -> {
			FooterTitle.popBack();
			requireActivity().getOnBackPressedDispatcher().onBackPressed();
		});

		if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
				== android.content.pm.PackageManager.PERMISSION_GRANTED) {
			setupCamera();
		} else {
			ActivityCompat.requestPermissions((Activity) requireContext(), new String[]{android.Manifest.permission.CAMERA}, 101);
		}

		return view;
	}

	private void setupCamera() {
		ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
		cameraProviderFuture.addListener(() -> {
			try {
				ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
				cameraProvider.unbindAll();

				Preview preview = new Preview.Builder().build();
				preview.setSurfaceProvider(previewView.getSurfaceProvider());

				// Choose back camera
				CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

				// Bind to life cycle of this fragment
				Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview);
			} catch (Exception e) {
				Log.e("CameraX", "Lỗi khởi chạy camera: " + e.getMessage());
			}
		}, ContextCompat.getMainExecutor(requireContext()));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		cameraExecutor.shutdown();
	}
}