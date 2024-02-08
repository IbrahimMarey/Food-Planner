package com.example.foodplanner.model.repositories.mealplanrepo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.local.AppDatabase;
import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.network.firebase.realtime.RealAddDelegate;
import com.example.foodplanner.network.firebase.realtime.RealFavDelegate;
import com.example.foodplanner.network.firebase.realtime.RealPlanDelegate;
import com.example.foodplanner.network.firebase.realtime.RealTimeWrapper;
import com.example.foodplanner.network.firebase.realtime.RealRemoveDelegate;

import java.util.List;

public class FavAndPlanRepoImplementation implements FavAndPlanRepo {

    private static final String TAG = "TAG";

    private AppDatabase dataBase;
    private static RealTimeWrapper firebase;
    private static FavAndPlanRepoImplementation repo;

    public static synchronized FavAndPlanRepoImplementation getInstance(Context context) {


        if (repo == null) {
            repo = new FavAndPlanRepoImplementation(context);
        }
        return repo;
    }

    private FavAndPlanRepoImplementation(Context context) {
        firebase = new RealTimeWrapper();
        dataBase = AppDatabase.getInstance(context);
    }

    @Override
    public void refreshPlanner() {
        firebase.getPlan(new RealPlanDelegate() {
            @Override
            public void onSuccess(List<PlanDto> planDtos) {

                Log.d(TAG, "onSuccess: planner");
                new Thread(() -> {
                    dataBase.mealDao().insertAllMealsPlan(planDtos);
                }).start();
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure: " + message);
            }
        });
    }

    @Override
    public void refreshMeals() {
        firebase.getFavMeals(new RealFavDelegate() {
            @Override
            public void onSuccess(List<MealDto> mealDtos) {
                new Thread(() -> {
                    new Thread(() -> dataBase.mealDao().insertAllMealsToFav(mealDtos)).start();
                    Log.d(TAG, "onSuccess: meals");
                }).start();
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure: " + message);
            }
        });
    }

    @Override
    public LiveData<List<MealDto>> getFavMealsLiveData() {
        return dataBase.mealDao().getAllFavMeals();
    }

    @Override
    public LiveData<List<PlanDto>> getAllMealsPlanAtDate(String date) {
        return dataBase.mealDao().getAllMealsPlanAtDate(date);

    }


    @Override
    public void deleteMealFav(MealDto mealDto, RealTimeListener realTimeListener) {
        firebase.removeMealFav(mealDto.idMeal, new RealRemoveDelegate() {
            @Override
            public void onSuccess() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dataBase.mealDao().deleteMealFav(mealDto);
                    }
                }).start();
                realTimeListener.onSuccess();
            }
        });
    }
    @Override
    public void addToFav(MealDto mealDto, RealTimeListener realTimeListener) {
        firebase.addToFav(mealDto, new RealAddDelegate() {
            @Override
            public void onSuccess() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dataBase.mealDao().insertMealToFav(mealDto);
                    }
                }).start();
                realTimeListener.onSuccess();
            }

            @Override
            public void onFailure(String message) {
                realTimeListener.onFailure(message);
            }
        });
    }

    @Override
    public void addToPlan(PlanDto planDto, RealTimeListener realTimeListener) {

        new Thread(()->{if (dataBase.mealDao().getListMealsPlanAtDate(planDto.date).size()>=3)
        {
            realTimeListener.onFailure("Can't add More than Three Meals in Day");
        }
        else{
            firebase.addToPlan(planDto, new RealAddDelegate() {
                @Override
                public void onSuccess() {
                    new Thread(() -> dataBase.mealDao().insertMealPlan(planDto)).start();
                    realTimeListener.onSuccess();
                }

                @Override
                public void onFailure(String message) {
                    realTimeListener.onFailure(message);
                }
            });
        }}).start();

    }

    @Override
    public void deleteMealPlan(PlanDto planDto) {
        firebase.removeMealPlan(planDto.id, () -> new Thread(() -> dataBase.mealDao().deleteMealPlan(planDto)).start());
    }


    @Override
    public void deleteAllFav() {
        new Thread(() -> {
            dataBase.mealDao().deleteAllMealsFav();
        }).start();
    }

    @Override
    public void deleteAllPlan() {
        new Thread(() -> {
            dataBase.mealDao().deleteAllMealsPlan();
        }).start();
    }

    @Override
    public LiveData<MealDto> getMealFavById(String idMeal) {
        return dataBase.mealDao().getMealFromFavById(idMeal);
    }

    @Override
    public LiveData<PlanDto> getMealPlanById(String id){
        return dataBase.mealDao().getMealPlanById(id);
    }

}
