package com.example.foodplanner.views.auth.login.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.R;
import com.example.foodplanner.views.auth.login.presenter.LoginPresenter;
import com.example.foodplanner.views.auth.login.presenter.LoginPresenterImplementation;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment implements LoginView{

    Button loginBtn;
    LoginPresenter loginPresenter;
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
        loginBtn = view.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v->{
            loginPresenter = LoginPresenterImplementation.getInstance(this);
            loginPresenter.login("hima@mar3i.com","123456");
        });
        return view;
    }

    @Override
    public void authSuccessfully(FirebaseUser user) {
        Log.i(TAG, "authSuccessfully: userID = "+user.getUid());
    }

    @Override
    public void authFailure(String errMsg) {
        Log.i(TAG, "authFailure: login failure "+ errMsg);
    }
}