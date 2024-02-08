package com.example.foodplanner.network.firebase.realtime;


import com.example.foodplanner.model.dtos.MealDto;

import java.util.List;

public interface RealFavDelegate {

    public void onSuccess(List<MealDto> mealDtos);

    public void onFailure(String message);

}
