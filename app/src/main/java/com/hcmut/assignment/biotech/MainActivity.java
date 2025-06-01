package com.hcmut.assignment.biotech;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_main);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});

		EditText emailText = findViewById(R.id.email_text);
		EditText passwordText = findViewById(R.id.password_text);

		Button btnLogin = findViewById(R.id.login_button);
		btnLogin.setOnClickListener(v -> {
			String email = emailText.getText().toString();
			String password = passwordText.getText().toString();
			SignInAuthentication signInAuthentication = new SignInFirebase(getApplicationContext(), FirebaseAuth.getInstance());
			signInAuthentication.checkUser(email, password, success -> {
				if (success) {
					Intent intent = new Intent(MainActivity.this, DisplayView.class);
					startActivity(intent);
				} else {
					// Done automatically
				}
			});
		});
	}
}