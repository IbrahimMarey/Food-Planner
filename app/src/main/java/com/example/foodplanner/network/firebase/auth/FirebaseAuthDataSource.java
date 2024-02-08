package com.example.foodplanner.network.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthDataSource
{
    private FirebaseAuth firebaseAuth;
    private static FirebaseAuthDataSource firebaseAuthDataSource;
    private FirebaseAuthDataSource()
    {
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public static FirebaseAuthDataSource getInstance()
    {
        if (firebaseAuthDataSource ==null)
            firebaseAuthDataSource = new FirebaseAuthDataSource();
        return firebaseAuthDataSource;
    }

    public FirebaseAuth getFirebaseAuth()
    {
        return firebaseAuth;
    }
    public FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }

    public void logOut()
    {
        firebaseAuth.signOut();
    }
}
