package com.example.foodplanner.network.firebase;

import com.google.firebase.auth.FirebaseAuth;

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

    public void logOut()
    {
        firebaseAuth.signOut();
    }
}
