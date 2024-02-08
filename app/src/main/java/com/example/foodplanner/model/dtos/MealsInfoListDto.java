package com.example.foodplanner.model.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MealsInfoListDto {
    @SerializedName("meals")
    public ArrayList<MealInfoDto> mealInfoList;
}
