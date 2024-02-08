package com.example.foodplanner.views.mealdetails.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.dtos.MealsListDto;
import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.repositories.mealplanrepo.RealTimeListener;

import io.reactivex.rxjava3.core.Single;

public interface MealDetailsPresenter
{
    Single<MealsListDto> getMeal(String id);


    void addToWeekPlanner(PlanDto planDto, RealTimeListener realTimeListener);

    public void addFavMeal(MealDto mealDto, RealTimeListener realTimeListener);
    public void delFavMeal(MealDto mealDto, RealTimeListener realTimeListener);
    public LiveData<MealDto> getMealFromFavById(String id);

    LiveData<PlanDto> getMealFromWeekPlanFavById(String id);
}
