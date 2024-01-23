package com.example.foodplanner.views.auth.register.view;

import com.google.firebase.auth.FirebaseUser;

public interface RegisterView
{
    void authSuccessfully(FirebaseUser user);
    void authFailure(String errMsg);
}
