package com.hcmut.assignment.biotech;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class DisplayView extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_display_view);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.display_view), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		FooterTitle.value = findViewById(R.id.footer_title);
		showHome();
		findViewById(R.id.go_back_button).setOnClickListener(v -> onBackPressed());
	}

	@Override
	@Deprecated
	public void onBackPressed() {
		super.onBackPressed();
		FooterTitle.popBack();
	}

	private void changeFragment(Fragment fragment, String title) {
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
		FooterTitle.pushBack(title);
	}
	public void showHome() {
		changeFragment(new HomeFragment(),  getString(R.string.display_opt));
	}
}