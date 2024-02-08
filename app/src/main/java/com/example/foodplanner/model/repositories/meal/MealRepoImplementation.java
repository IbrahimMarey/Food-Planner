package com.example.foodplanner.model.repositories.meal;

import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.dtos.MealsListDto;
import com.example.foodplanner.network.api.ClientConnection;
import com.example.foodplanner.network.api.ClientConnectionImplementation;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepoImplementation implements MealRepo
{

    private Single<IngredientsListDto> listIngredientSingle;
    private Single<CategoriesListDto> listCategorySingle;
    private Single<CuisinesListDto> listCuisineSingle;
    private Single<MealsInfoListDto> listMealInfoCuisineSingle;
    private Single<MealsInfoListDto> listMealInfoIngredientSingle;
    private Single<MealsInfoListDto> listMealInfoCategorySingle;
    private Single<MealsListDto> randomMealSingle;

    private static MealRepoImplementation instance;

    ClientConnection clientConnection;
    private MealRepoImplementation() {
        this.clientConnection = ClientConnectionImplementation.getInstance();
    }

    public static synchronized MealRepoImplementation getInstance() {
        if (instance == null) {
            instance = new MealRepoImplementation();
        }
        return instance;
    }


    @Override
    public Single<IngredientsListDto> getListIngredientObservable() {
        if (listIngredientSingle == null) {
            listIngredientSingle = clientConnection.getIngredients().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return listIngredientSingle;
    }

    @Override
    public Single<CategoriesListDto> getListCategoryObservable() {
        if (listCategorySingle == null) {
            listCategorySingle = clientConnection.getAllCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return listCategorySingle;    }

    @Override
    public Single<CuisinesListDto> getListCuisineObservable() {
        if (listCuisineSingle == null) {
            listCuisineSingle = clientConnection.getAllCuisines().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return listCuisineSingle;
    }

    @Override
    public Single<MealsInfoListDto> getListInfoMealByIngredient(String id) {
        listMealInfoIngredientSingle = clientConnection.getMealsByIngredient(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return listMealInfoIngredientSingle;
    }

    @Override
    public Single<MealsInfoListDto> getListInfoMealByCategory(String id) {
        listMealInfoCategorySingle = clientConnection.getMealsByCategory(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return listMealInfoCategorySingle;
    }

    @Override
    public Single<MealsInfoListDto> getListInfoMealByCuisine(String id) {
        listMealInfoCuisineSingle = clientConnection.getMealsByCountry(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return listMealInfoCuisineSingle;
    }

    public Single<MealsListDto> getRandomMealObservable() {
        if (randomMealSingle == null) {
            randomMealSingle = clientConnection.getRandomMeal().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return randomMealSingle;
    }

    @Override
    public Single<MealsListDto> searchMealByName(String name) {
        return clientConnection.searchMealByName(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<MealsListDto> getMealById(String id) {
        return clientConnection.getMealById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
