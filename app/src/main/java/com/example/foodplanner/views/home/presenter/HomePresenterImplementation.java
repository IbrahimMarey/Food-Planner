package com.example.foodplanner.views.home.presenter;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.MealsListDto;
import com.example.foodplanner.model.repositories.meal.MealRepo;

import io.reactivex.rxjava3.core.Single;

public class HomePresenterImplementation implements HomePresenter
{

    private static HomePresenterImplementation homeFragmentPresenter;
    private MealRepo mealsRepo;
    public static synchronized HomePresenterImplementation getInstance(MealRepo mealsRepo){
        if(homeFragmentPresenter == null){
            homeFragmentPresenter = new HomePresenterImplementation(mealsRepo);
        }
        return homeFragmentPresenter;
    }

    private  HomePresenterImplementation(MealRepo mealsRepo){
        this.mealsRepo = mealsRepo;
    }

    @Override
    public Single<MealsListDto> getRandomMeal() {
        return mealsRepo.getRandomMealObservable();
    }

    @Override
    public Single<CategoriesListDto> getCategory() {
        return mealsRepo.getListCategoryObservable();
    }
}
