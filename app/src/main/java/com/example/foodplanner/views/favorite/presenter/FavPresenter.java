package com.example.foodplanner.views.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.repositories.mealplanrepo.RealTimeListener;

import java.util.List;

public interface FavPresenter
{
    LiveData<List<MealDto>> getFavoriteMeals();
    void deleteMealFromFav(MealDto mealDto, RealTimeListener realTimeListener);
    void getMealDetailsById(String id);
}
