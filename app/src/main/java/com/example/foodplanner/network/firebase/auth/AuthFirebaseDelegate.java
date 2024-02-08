package com.example.foodplanner.network.firebase.auth;

import com.google.firebase.auth.FirebaseUser;

public interface AuthFirebaseDelegate
{
    void authSuccessfully(FirebaseUser user);
    void authFailure(String errMsg);
}
