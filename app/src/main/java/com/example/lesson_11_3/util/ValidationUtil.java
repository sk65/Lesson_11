package com.example.lesson_11_3.util;

import android.content.Context;
import android.util.Patterns;

import com.example.lesson_11_3.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=\\S+$)" +
                    ".{6,20}" +
                    "$");

    public static boolean validatePassword(TextInputLayout password, Context context) {
        String passwordInput = getStringFromInputLayout(password);
        if (passwordInput.isEmpty()) {
            password.setError(context.getString(R.string.empty_field_explanation));
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError(context.getString(R.string.weak_password_explanation));
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public static boolean validateName(TextInputLayout name, Context context) {
        String nameInput = getStringFromInputLayout(name);
        if (nameInput.isEmpty()) {
            name.setError(context.getString(R.string.empty_field_explanation));
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    public static boolean validateConfirmPassword(TextInputLayout confirmPassword, String password, Context context) {
        String confirmPasswordInput = getStringFromInputLayout(confirmPassword);
        if (confirmPasswordInput.isEmpty()) {
            confirmPassword.setError(context.getString(R.string.empty_field_explanation));
            return false;
        } else if (!confirmPasswordInput.equals(password)) {
            confirmPassword.setError(context.getString(R.string.password_mismatch));
            return false;
        } else {
            confirmPassword.setError(null);
            return true;
        }
    }

    public static boolean validateLastName(TextInputLayout lastName, Context context) {
        String lastNameInput = getStringFromInputLayout(lastName);
        if (lastNameInput.isEmpty()) {
            lastName.setError(context.getString(R.string.empty_field_explanation));
            return false;
        } else {
            lastName.setError(null);
            return true;
        }
    }

    public static boolean validateEmail(TextInputLayout email, Context context) {
        String emailInput = getStringFromInputLayout(email);
        if (emailInput.isEmpty()) {
            email.setError(context.getString(R.string.empty_field_explanation));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError(context.getString(R.string.invalid_email_explanation));
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public static String getStringFromInputLayout(TextInputLayout inputLayout) {
        return inputLayout.getEditText().getText().toString().trim();
    }

}
