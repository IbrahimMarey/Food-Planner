package com.example.foodplanner.views.auth.login.view;

import com.google.firebase.auth.FirebaseUser;

public interface LoginView
{
    void authSuccessfully(FirebaseUser user);
    void authFailure(String errMsg);
}
