package com.example.foodplanner.views.auth.login.view;

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
import com.example.foodplanner.views.auth.login.presenter.LoginPresenter;
import com.example.foodplanner.views.auth.login.presenter.LoginPresenterImplementation;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment implements LoginView{

    Button loginBtn;
    LoginPresenter loginPresenter;

    TextView skipTV;
    TextView registerTV;
    EditText emailET;
    EditText passwordET;

    private static final String TAG = "LoginFragment";
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        skipTV = view.findViewById(R.id.tv_skip_login);
        loginBtn = view.findViewById(R.id.login_btn);
        emailET = view.findViewById(R.id.input_email_login);
        passwordET = view.findViewById(R.id.input_password_login);
        registerTV = view.findViewById(R.id.tv_go_register);
        // logic of views
        emailET.addTextChangedListener(getEmailWatcher(emailET));
        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        loginBtn.setOnClickListener(v->{
            if (checkValidation()&& emailValidation(emailET.getText().toString()))
            {
                loginPresenter = LoginPresenterImplementation.getInstance(this);
                loginPresenter.login(emailET.getText().toString(),passwordET.getText().toString());
            }
        });

    }

    @Override
    public void authSuccessfully(FirebaseUser user) {
        goToMainActivity();
        Log.i(TAG, "authSuccessfully: userID = "+user.getUid());
    }

    @Override
    public void authFailure(String errMsg) {
        Log.i(TAG, "authFailure: login failure "+ errMsg);
    }



    private boolean checkValidation() {
        if (emailET.getText() != null && !emailET.getText().toString().isEmpty() && passwordET.getText() != null && !passwordET.getText().toString().isEmpty())
            return true;
        else {
            Toast.makeText(getActivity(), R.string.please_enter_valid_data, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean emailValidation(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
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
                if(!emailValidation(s.toString())){
                    editText.setError("Please enter valid email");
                }else{
                    editText.setError(null);
                }
            }
        };
    }
}