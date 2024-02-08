package com.example.foodplanner.network.api;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.dtos.MealsListDto;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientConnectionImplementation implements ClientConnection
{
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static ClientConnectionImplementation clientConnectionImplementation;
    private MealServices mealServices;

    public static synchronized ClientConnectionImplementation getInstance() {
        if (clientConnectionImplementation == null) {
            clientConnectionImplementation = new ClientConnectionImplementation();
        }
        return clientConnectionImplementation;
    }

    private ClientConnectionImplementation() {
        Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava3CallAdapterFactory.create()).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        mealServices = retrofit.create(MealServices.class);
    }

    @Override
    public Single<IngredientsListDto> getIngredients() {
        return mealServices.getIngredients();
    }

    @Override
    public Single<CategoriesListDto> getAllCategories() {
        return mealServices.getCategories();
    }

    @Override
    public Single<CuisinesListDto> getAllCuisines() {
        return mealServices.getCuisines();
    }

    @Override
    public Single<MealsInfoListDto> getMealsByIngredient(String id) {
        return mealServices.getMealsByIngredient(id);
    }

    @Override
    public Single<MealsInfoListDto> getMealsByCategory(String id) {
        return mealServices.getMealsByCategory(id);
    }

    @Override
    public Single<MealsInfoListDto> getMealsByCountry(String id) {
        return mealServices.getMealsByCuisine(id);
    }

    @Override
    public Single<MealsListDto> getRandomMeal() {
        return mealServices.getRandomMeal();
    }

    @Override
    public Single<MealsListDto> searchMealByName(String name) {
        return mealServices.searchByName(name);
    }

    public Single<MealsListDto> getMealById(String id){
        return mealServices.getMealById(id);
    }
}
