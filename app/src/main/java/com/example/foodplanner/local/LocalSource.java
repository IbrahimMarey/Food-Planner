package com.example.foodplanner.local;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;

import java.util.List;

public interface LocalSource {

    LiveData<List<MealDto>> getAllFavMeals();

    void insertMealFav(MealDto mealDto);

    void deleteMealFav(MealDto product);

    LiveData<List<PlanDto>> getAllMealsPlanAtDate(String date);

//    void deleteMealPlan(String date,String mealId);

    @Delete
    void deleteMealPlan(PlanDto planDto);

    @Insert
    void insertMealPlan(PlanDto planDto);
}
