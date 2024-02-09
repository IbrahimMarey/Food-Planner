package com.example.foodplanner.views.auth.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.AuthCredential;

public interface LoginPresenter
{
    void login(String mail,String password);

    void loginByGoogle(AuthCredential credential );

    void loginByTwitter(Activity activity);
}
