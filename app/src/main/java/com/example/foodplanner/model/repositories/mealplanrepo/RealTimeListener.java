package com.example.foodplanner.model.repositories.mealplanrepo;

public interface RealTimeListener {
    void onSuccess();
    void onFailure(String message);
}
