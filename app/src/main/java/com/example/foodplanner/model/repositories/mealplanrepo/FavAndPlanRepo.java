package com.example.foodplanner.model.repositories.mealplanrepo;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;

import java.util.List;

public interface FavAndPlanRepo {

    void refreshPlanner();
    void refreshMeals();

   LiveData<List<MealDto>> getFavMealsLiveData();

    LiveData<List<PlanDto>> getAllMealsPlanAtDate(String date);

    void addToFav(MealDto mealDto, RealTimeListener realTimeListener);

    void addToPlan(PlanDto planDto, RealTimeListener realTimeListener);

    void deleteMealPlan(PlanDto planDto);

    void deleteMealFav(MealDto mealDto, RealTimeListener realTimeListener);
    void deleteAllFav();
    void deleteAllPlan();

    LiveData<MealDto> getMealFavById(String idMeal);

    LiveData<PlanDto> getMealPlanById(String id);
}
