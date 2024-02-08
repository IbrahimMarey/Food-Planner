package com.example.foodplanner.model.repositories.meal;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.dtos.MealsListDto;

import io.reactivex.rxjava3.core.Single;

public interface MealRepo
{
    public Single<IngredientsListDto> getListIngredientObservable();
    public Single<CategoriesListDto> getListCategoryObservable();
    public Single<CuisinesListDto> getListCuisineObservable();
    Single<MealsInfoListDto> getListInfoMealByIngredient(String id);
    Single<MealsInfoListDto> getListInfoMealByCategory(String id);
    Single<MealsInfoListDto> getListInfoMealByCuisine(String id);
    public Single<MealsListDto> getRandomMealObservable();
    public Single<MealsListDto> searchMealByName(String name);
    public Single<MealsListDto> getMealById(String id);
}
