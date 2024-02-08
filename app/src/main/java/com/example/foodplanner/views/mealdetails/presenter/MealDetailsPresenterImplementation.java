package com.example.foodplanner.views.mealdetails.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.dtos.MealsListDto;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepo;
import com.example.foodplanner.model.repositories.mealplanrepo.RealTimeListener;
import com.example.foodplanner.model.repositories.meal.MealRepo;

import io.reactivex.rxjava3.core.Single;

public class MealDetailsPresenterImplementation implements MealDetailsPresenter{
    private static MealDetailsPresenterImplementation mealDetailsPresenterImplementation;
    private MealRepo mealsRepo;
    private FavAndPlanRepo dataBaseRepo;
    public static synchronized MealDetailsPresenterImplementation getInstance(MealRepo mealsRepo, FavAndPlanRepo dataBaseRepo){
        if(mealDetailsPresenterImplementation == null){
            mealDetailsPresenterImplementation = new MealDetailsPresenterImplementation(mealsRepo ,dataBaseRepo);
        }
        return mealDetailsPresenterImplementation;
    }

    private MealDetailsPresenterImplementation(MealRepo mealsRepo, FavAndPlanRepo dataBaseRepo ){
        this.mealsRepo = mealsRepo;
        this.dataBaseRepo = dataBaseRepo;
    }
    @Override
    public Single<MealsListDto> getMeal(String id) {
        return mealsRepo.getMealById(id);
    }

    @Override
    public void addFavMeal(MealDto mealDto, RealTimeListener realTimeListener) {
        dataBaseRepo.addToFav(mealDto, realTimeListener);
    }

    @Override
    public void delFavMeal(MealDto mealDto, RealTimeListener realTimeListener) {
        dataBaseRepo.deleteMealFav(mealDto,realTimeListener);
    }

    @Override
    public void addToWeekPlanner(PlanDto planDto, RealTimeListener realTimeListener) {
        dataBaseRepo.addToPlan(planDto,realTimeListener);
    }

    @Override
    public LiveData<MealDto> getMealFromFavById(String id){
        return dataBaseRepo.getMealFavById(id);
    }

    @Override
    public LiveData<PlanDto> getMealFromWeekPlanFavById(String id){
        return dataBaseRepo.getMealPlanById(id);
    }
}
