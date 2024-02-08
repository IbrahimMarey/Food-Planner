package com.example.foodplanner.views.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;
import com.example.foodplanner.model.repositories.mealplanrepo.RealTimeListener;

import java.util.List;

public class FavPresenterImplementation implements FavPresenter
{
    private static FavPresenterImplementation favoritePresenter;
    private FavAndPlanRepoImplementation favRepo;

    public static synchronized FavPresenterImplementation getInstance(FavAndPlanRepoImplementation favRepo){
        if(favoritePresenter == null){
            favoritePresenter = new FavPresenterImplementation(favRepo);
        }
        return favoritePresenter;
    }

    public FavPresenterImplementation(FavAndPlanRepoImplementation favRepo){
        this.favRepo = favRepo;
    }

    @Override
    public LiveData<List<MealDto>> getFavoriteMeals() {
        return favRepo.getFavMealsLiveData();
    }

    @Override
    public void deleteMealFromFav(MealDto mealDto, RealTimeListener realTimeListener) {
        favRepo.deleteMealFav(mealDto, realTimeListener);

    }

    @Override
    public void getMealDetailsById(String id) {

    }
}
