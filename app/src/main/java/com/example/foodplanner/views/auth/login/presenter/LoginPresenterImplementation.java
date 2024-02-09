package com.example.foodplanner.views.auth.login.presenter;

import android.app.Activity;

import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepo;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.example.foodplanner.network.firebase.auth.AuthFirebaseDelegate;
import com.example.foodplanner.views.auth.login.view.LoginView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenterImplementation implements LoginPresenter, AuthFirebaseDelegate
{
    private AuthFirebaseRepo authFirebaseRepo;
    private LoginView loginView;
    private static LoginPresenterImplementation loginPresenter;
    private LoginPresenterImplementation(LoginView loginView)
    {
        authFirebaseRepo = AuthFirebaseRepoImplementation.getInstance();
        this.loginView =loginView;
    }
    public static LoginPresenterImplementation getInstance(LoginView loginView)
    {
        if (loginPresenter==null)
            loginPresenter = new LoginPresenterImplementation(loginView);
        return loginPresenter;
    }
    @Override
    public void login(String mail, String password) {
        loginPresenter.authFirebaseRepo.login(mail,password,this);
    }

    @Override
    public void authSuccessfully(FirebaseUser user) {
        loginView.authSuccessfully(user);
    }

    @Override
    public void authFailure(String errMsg) {
        loginView.authFailure(errMsg);
    }
    @Override
    public void loginByGoogle(AuthCredential credential) {
        authFirebaseRepo.loginByGoogle(credential,this);
    }

    @Override
    public void loginByTwitter(Activity activity) {
        authFirebaseRepo.loginByTwitter(this,activity);
    }
}
