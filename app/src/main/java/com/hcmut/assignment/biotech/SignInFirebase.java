package com.hcmut.assignment.biotech;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignInFirebase extends SignInAuthentication {
	private Context context;
	private FirebaseAuth mAuth;

	public SignInFirebase(Context context, FirebaseAuth mAuth) {
		this.context = context;
		this.mAuth = mAuth;
	}

	@Override
	public void checkUser(String email, String password, AuthCallback callback) {
		if (email.isEmpty() || password.isEmpty()) {
			Toast.makeText(context, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
			callback.onResult(false);
			return;
		}
		if (!isValidateEmail(email)) {
			Toast.makeText(context, "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
			callback.onResult(false);
			return;
		}
		mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(v -> {
			if (v.isSuccessful()) callback.onResult(true);
			else {
				Toast.makeText(context, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
				callback.onResult(false);
			}
		});
	}
}
