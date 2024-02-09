package com.example.foodplanner.network.firebase.firestore;

import static com.example.foodplanner.MainActivity.TAG;

import android.util.Log;

import com.example.foodplanner.model.dtos.UserDto;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStore
{
    private FirebaseFirestore firebaseFirestore;
    private AuthFirebaseRepoImplementation authenticationFireBaseRepo;
    private static FireStore instance;

    private FireStore()
    {
        firebaseFirestore = FirebaseFirestore.getInstance();
//        authenticationFireBaseRepo = AuthFirebaseRepoImplementation.getInstance();
    }
    public static synchronized FireStore getInstance()
    {
        if (instance  ==null)
            instance = new FireStore();
        return instance;
    }


    public void addUser(UserDto userDto)
    {
            firebaseFirestore.collection("users").document(userDto.getuId()).set(userDto);
    }

    public void getUser()
    {

    }
}
