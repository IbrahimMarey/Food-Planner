package com.example.foodplanner.views.auth.register.presenter;

import android.app.Activity;

import com.google.firebase.auth.AuthCredential;

public interface RegisterPresenter
{
    void register(String mail,String password);

    void loginByGoogle(AuthCredential credential );

    void loginByTwitter(Activity activity);
}
