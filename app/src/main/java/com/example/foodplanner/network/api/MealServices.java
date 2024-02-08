package com.example.foodplanner.network.api;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.dtos.MealsListDto;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealServices
{
    @GET("list.php?i=list")
    public Single<IngredientsListDto> getIngredients();
    @GET("categories.php")
    public Single<CategoriesListDto> getCategories();
    @GET("list.php?a=list")
    public Single<CuisinesListDto> getCuisines();
    @GET("filter.php")
    public Single<MealsInfoListDto> getMealsByIngredient(@Query("i") String ingredient);
    @GET("filter.php")
    public Single<MealsInfoListDto> getMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    public Single<MealsInfoListDto> getMealsByCuisine(@Query("a") String cuisine);

    @GET("search.php")
    public Single<MealsListDto>searchByName(@Query("s") String mealName);
    @GET("lookup.php")
    public Single<MealsListDto> getMealById(@Query("i") String id);
    @GET("random.php")
    public Single<MealsListDto> getRandomMeal();

}
