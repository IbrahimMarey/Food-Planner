package com.example.foodplanner.network.api;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.dtos.MealsListDto;

import io.reactivex.rxjava3.core.Single;

public interface ClientConnection
{
    Single<IngredientsListDto> getIngredients();
    Single<CategoriesListDto> getAllCategories();
    Single<CuisinesListDto> getAllCuisines();
    Single<MealsInfoListDto> getMealsByIngredient(String id);
    Single<MealsInfoListDto> getMealsByCategory(String id);
    Single<MealsInfoListDto> getMealsByCountry(String id);
    Single<MealsListDto> getRandomMeal();
    Single<MealsListDto> searchMealByName(String name);
    Single<MealsListDto> getMealById(String id);
}
