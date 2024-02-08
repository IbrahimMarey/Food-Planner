package com.example.foodplanner.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;

import java.util.List;

public class LocalSourceRoom implements LocalSource{

    private MealDao dao;
    private static LocalSourceRoom localSource = null;
    private LiveData<List<MealDto>> favoriteMeals;


    private LocalSourceRoom(Context context){
        AppDatabase appDataBase = AppDatabase.getInstance(context.getApplicationContext());
        dao = appDataBase.mealDao();
        favoriteMeals = dao.getAllFavMeals();
    }

    public static LocalSourceRoom getInstance(Context context){
        if(localSource == null){
            localSource = new LocalSourceRoom(context);
        }
        return localSource;
    }

    @Override
    public LiveData<List<MealDto>> getAllFavMeals() {
        return favoriteMeals;
    }

    @Override
    public void insertMealFav(MealDto mealDto) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMealToFav(mealDto);
            }
        }).start();

    }

    @Override
    public void deleteMealFav(MealDto mealDto) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMealFav(mealDto);
            }
        }).start();

    }

    @Override
    public LiveData<List<PlanDto>> getAllMealsPlanAtDate(String date) {
        return dao.getAllMealsPlanAtDate(date);
    }



    @Override
    public void deleteMealPlan(PlanDto planDto) {
        new Thread(() -> dao.deleteMealPlan(planDto)).start();
    }

    @Override
    public void insertMealPlan(PlanDto planDto) {
        new Thread(() -> dao.insertMealPlan(planDto)).start();
    }
}
