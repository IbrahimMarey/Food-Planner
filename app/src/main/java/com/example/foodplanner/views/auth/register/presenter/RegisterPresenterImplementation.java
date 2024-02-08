package com.example.foodplanner.views.auth.register.presenter;

import android.app.Activity;

import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepo;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.example.foodplanner.network.firebase.auth.AuthFirebaseDelegate;
import com.example.foodplanner.views.auth.register.view.RegisterView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPresenterImplementation implements RegisterPresenter, AuthFirebaseDelegate
{
    private AuthFirebaseRepo authFirebaseRepo;
    private RegisterView registerView;
    private static RegisterPresenterImplementation instance;
    private RegisterPresenterImplementation(RegisterView registerView)
    {
        authFirebaseRepo = AuthFirebaseRepoImplementation.getInstance();
        this.registerView= registerView;
    }

    public static RegisterPresenterImplementation getInstance(RegisterView registerView)
    {
        if (instance ==null)
            instance = new RegisterPresenterImplementation(registerView);
        return instance;
    }
    @Override
    public void register(String mail, String password) {
        authFirebaseRepo.register(mail,password,this);
    }

    @Override
    public void loginByGoogle(AuthCredential credential) {
        authFirebaseRepo.loginByGoogle(credential,this);
    }

    @Override
    public void loginByTwitter(Activity activity) {
        authFirebaseRepo.loginByTwitter(this,activity);
    }

    @Override
    public void authSuccessfully(FirebaseUser user) {
        registerView.authSuccessfully(user);
    }

    @Override
    public void authFailure(String errMsg) {
        registerView.authFailure(errMsg);
    }
}
