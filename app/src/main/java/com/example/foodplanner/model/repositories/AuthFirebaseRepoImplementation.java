package com.example.foodplanner.model.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplanner.network.firebase.AuthFirebaseDelegate;
import com.example.foodplanner.network.firebase.FirebaseAuthDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class AuthFirebaseRepoImplementation implements AuthFirebaseRepo
{
    FirebaseAuthDataSource myFireAuth;
    private static AuthFirebaseRepoImplementation authFirebaseRepoImplementation;
    private AuthFirebaseRepoImplementation()
    {
        myFireAuth = FirebaseAuthDataSource.getInstance();
    }
    public static AuthFirebaseRepoImplementation getInstance()
    {
        if (authFirebaseRepoImplementation == null)
            authFirebaseRepoImplementation= new AuthFirebaseRepoImplementation();
        return authFirebaseRepoImplementation;
    }

    @Override
    public void login(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate)
    {
        myFireAuth.getFirebaseAuth().signInWithEmailAndPassword(mail, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authFirebaseDelegate.authSuccessfully(authResult.getUser());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authFirebaseDelegate.authFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void register(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate)
    {
        myFireAuth.getFirebaseAuth().createUserWithEmailAndPassword(mail, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authFirebaseDelegate.authSuccessfully(authResult.getUser());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        authFirebaseDelegate.authFailure(e.getMessage());
                    }
                });
    }

    @Override
    public void logOut() {
        myFireAuth.logOut();
    }
}
