package com.hcmut.assignment.biotech;

import android.util.Patterns;

public abstract class SignInAuthentication {
    public boolean isValidateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public abstract void checkUser(String username, String password, AuthCallback callback);

    public interface AuthCallback {
        void onResult(boolean success);
    }
}
