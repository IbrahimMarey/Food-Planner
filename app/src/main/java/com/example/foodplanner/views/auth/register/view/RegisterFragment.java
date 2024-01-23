package com.example.foodplanner.views.auth.register.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment implements RegisterView{
    private static final String TAG = "LoginFragment";

    public RegisterFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void authSuccessfully(FirebaseUser user) {
        Log.i(TAG, "authSuccessfully: register user with id = "+user.getUid());
    }

    @Override
    public void authFailure(String errMsg) {
        Log.i(TAG, "authFailure: register failure "+errMsg);
    }
}