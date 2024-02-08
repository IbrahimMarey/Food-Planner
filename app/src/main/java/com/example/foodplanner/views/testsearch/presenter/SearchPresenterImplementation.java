package com.example.foodplanner.views.testsearch.presenter;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.dtos.MealsListDto;
import com.example.foodplanner.model.repositories.meal.MealRepo;

import io.reactivex.rxjava3.core.Single;

public class SearchPresenterImplementation implements SearchPresenter {

    private static SearchPresenterImplementation searchPresenter;
    private MealRepo mealsRepo;

    public static synchronized SearchPresenterImplementation getInstance(MealRepo mealsRepo){
        if(searchPresenter == null){
            searchPresenter = new SearchPresenterImplementation(mealsRepo);
        }
        return searchPresenter;
    }

    public SearchPresenterImplementation(MealRepo mealsRepo){
        this.mealsRepo = mealsRepo;
    }

    @Override
    public Single<IngredientsListDto> getIngredients() {
        return mealsRepo.getListIngredientObservable();
    }

    @Override
    public Single<CategoriesListDto> getCategory() {
        return mealsRepo.getListCategoryObservable();
    }

    @Override
    public Single<CuisinesListDto> getCuisines() {
        return mealsRepo.getListCuisineObservable();
    }

    @Override
    public Single<MealsListDto> searchMealByName(String name) {
        return mealsRepo.searchMealByName(name);
    }

    @Override
    public Single<MealsInfoListDto> getMealByIngredient(String id) {
        return mealsRepo.getListInfoMealByIngredient(id);
    }

    @Override
    public Single<MealsInfoListDto> getMealByCountry(String id) {
        return mealsRepo.getListInfoMealByCuisine(id);
    }

    @Override
    public Single<MealsInfoListDto> getMealByCategory(String id) {
        return mealsRepo.getListInfoMealByCategory(id);
    }
}

