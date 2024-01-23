package com.example.foodplanner.model.repositories;

import com.example.foodplanner.network.firebase.AuthFirebaseDelegate;

public interface AuthFirebaseRepo
{
    void login(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate);
    void register(String mail, String password, AuthFirebaseDelegate authFirebaseDelegate);
    void logOut();
}
