package com.example.foodplanner.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.dtos.PlanDto;

@Database(entities = {MealDto.class , PlanDto.class} , version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;
    public abstract MealDao mealDao();
    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"meal.db").build();
        }
        return instance;
    }
}
