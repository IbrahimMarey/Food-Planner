package com.example.foodplanner.views.testsearch.presenter;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.dtos.MealsListDto;

import io.reactivex.rxjava3.core.Single;
public interface SearchPresenter {

    Single<IngredientsListDto> getIngredients();
    Single<CategoriesListDto> getCategory();
    Single<CuisinesListDto> getCuisines();
    public Single<MealsListDto> searchMealByName(String name);

    Single<MealsInfoListDto> getMealByIngredient(String id);
    Single<MealsInfoListDto> getMealByCountry(String id);
    Single<MealsInfoListDto> getMealByCategory(String id);
}

