package com.example.foodplanner.views.auth.register.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.views.auth.login.presenter.LoginPresenterImplementation;
import com.example.foodplanner.views.auth.register.presenter.RegisterPresenter;
import com.example.foodplanner.views.auth.register.presenter.RegisterPresenterImplementation;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment implements RegisterView{
    private static final String TAG = "LoginFragment";

    RegisterPresenter registerPresenter;
    TextView skipTV;
    EditText nameET;
    EditText emailET;
    EditText passwordET;
    EditText confirmPasswordET;

    Button registerBtn;
    TextView loginTV;
    View view;
    public RegisterFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        skipTV = view.findViewById(R.id.tv_skip_register);
        nameET = view.findViewById(R.id.input_name_register);
        emailET = view.findViewById(R.id.input_email_register);
        passwordET = view.findViewById(R.id.input_password_register);
        confirmPasswordET = view.findViewById(R.id.input_confirm_password_register);
        registerBtn = view.findViewById(R.id.register_btn);
        loginTV = view.findViewById(R.id.tv_go_to_login);
        // Register Logic
        emailET.addTextChangedListener(getEmailWatcher(emailET));
        passwordET.addTextChangedListener(getPasswordWatcher(passwordET));
        confirmPasswordET.addTextChangedListener(getPasswordWatcher(confirmPasswordET));
        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment2);
            }
        });
        registerBtn.setOnClickListener(v->{
            if (isAllDataFilled() && checkValidation(emailET.getText().toString(), passwordET.getText().toString())) {
                registerPresenter = RegisterPresenterImplementation.getInstance(this);
                registerPresenter.register(emailET.getText().toString(),passwordET.getText().toString());
            }
        });

    }

    @Override
    public void authSuccessfully(FirebaseUser user) {
        Toast.makeText(getActivity(), "Register Successfully please login", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "authSuccessfully: register user with id = "+user.getUid());
        goToMainActivity();
       // Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment2);
    }

    @Override
    public void authFailure(String errMsg) {
        Log.i(TAG, "authFailure: register failure "+errMsg);
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    private boolean checkValidation(String userName, String pass) {
        return emailValidation(userName) && isPassLengthGT8(pass) && isPassContainNumber(pass) && isPassContainSpecialChar(pass) && isPassContainUpperCase(pass) && checkPassEquality();
    }

    private boolean checkPassEquality() {
        return passwordET.getText().toString().equals(confirmPasswordET.getText().toString());
    }
    private boolean emailValidation(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private TextWatcher getEmailWatcher(EditText editText)
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!emailValidation(s.toString())) {
                    editText.setError("please enter valid email");
                } else {
                    editText.setError(null);
                }
            }
        };
    }

    private TextWatcher getPasswordWatcher(EditText editText)
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPassContainNumber(s.toString())) {
                    editText.setError("one number required");
                } else if (!isPassContainSpecialChar(s.toString())) {
                    editText.setError("one special symbol required");
                } else if (!isPassContainUpperCase(s.toString())) {
                    editText.setError("one uppercase required");
                } else if (!isPassLengthGT8(s.toString())) {
                    editText.setError("at least 8 char");
                } else {
                    editText.setError(null);
                }
            }
        };
    }

    private TextWatcher getConfirmPasswordWatcher(EditText editText)
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!checkPassEquality()) {
                    editText.setError("check your password");
                } else {
                    editText.setError(null);
                }
            }
        };
    }

    public boolean isPassContainSpecialChar(String pass) {
        String specialCharRegex = ".*[@#!$%^&+=].*";
        String UpperCaseRegex = ".*[A-Z].*";
        String NumberRegex = ".*[0-9].*";
        return pass.matches(specialCharRegex);
    }

    public boolean isPassContainUpperCase(String pass) {
        String UpperCaseRegex = ".*[A-Z].*";
        return pass.matches(UpperCaseRegex);
    }

    public boolean isPassContainNumber(String pass) {
        String NumberRegex = ".*[0-9].*";
        return pass.matches(NumberRegex);
    }
    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
    public boolean isPassLengthGT8(String pass) {
        return pass.length() >= 8;
    }
    private boolean isAllDataFilled() {
        Log.d(TAG, "isAllDataFilled: ");
        if (emailET.getText() != null && !emailET.getText().toString().isEmpty() && passwordET.getText() != null && !passwordET.getText().toString().isEmpty() && confirmPasswordET.getText() != null && !confirmPasswordET.getText().toString().isEmpty())
            return true;
        else {
            Log.d(TAG, "isAllDataFilled: false ");
            //binding.textViewMessage.setText(R.string.please_fill_all_data);
            return false;
        }
    }
}