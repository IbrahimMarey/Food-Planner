package com.example.foodplanner.views.home.presenter;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.MealsListDto;

import io.reactivex.rxjava3.core.Single;

public interface HomePresenter
{

    Single<MealsListDto> getRandomMeal();

    public Single<CategoriesListDto> getCategory();
}
