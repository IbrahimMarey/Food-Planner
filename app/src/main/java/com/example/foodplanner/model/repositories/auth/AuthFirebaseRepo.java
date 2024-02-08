package com.example.foodplanner.model.repositories.auth;

import android.app.Activity;

import com.example.foodplanner.network.firebase.auth.AuthFirebaseDelegate;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public interface AuthFirebaseRepo
{
    void login(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate);
    void register(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate);

    void loginByGoogle(AuthCredential credential ,AuthFirebaseDelegate authFirebaseDelegate);

    void loginByTwitter(AuthFirebaseDelegate authFirebaseDelegate, Activity activity);
    FirebaseUser getUser();
    void logOut();
}
