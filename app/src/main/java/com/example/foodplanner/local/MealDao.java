package com.example.foodplanner.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM favorite")
    LiveData<List<MealDto>> getAllFavMeals();
    @Query("SELECT * FROM favorite")
    List<MealDto> getFavMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealToFav(MealDto mealDto);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllMealsToFav(List<MealDto> mealsDto);

    @Delete
    void deleteMealFav(MealDto mealDto);
    @Query("delete From favorite")
    void deleteAllMealsFav();

    @Query("SELECT * FROM favorite where idMeal =:idMeal")
    LiveData<MealDto> getMealFromFavById(String idMeal);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertMealPlan(PlanDto planDto);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllMealsPlan(List<PlanDto> plansDto);

    @Delete
    void deleteMealPlan(PlanDto planDto);
    @Query("delete From plan")
    void deleteAllMealsPlan();

    @Query("select * from plan  where date = :date")
    LiveData<List<PlanDto>> getAllMealsPlanAtDate(String date);

    @Query("select * from plan  where date = :date")
    List<PlanDto> getListMealsPlanAtDate(String date);

    @Query("delete from plan where date = :date & idMeal = :mealId")
    void deleteMealPlan(String date, String mealId);

    @Query("SELECT * FROM plan where id =:id")
    LiveData<PlanDto> getMealPlanById(String id);

}
