package com.example.foodplanner.helpers;

import com.example.foodplanner.model.dtos.MealDto;

public class ConvertMealToString
{
    public static String getMealString(MealDto mealDto)
    {
        String str="";
        str = mealDto.strMeal+"\n\n"+"Category : "+mealDto.strCategory+"\n\n"+"Country : "+mealDto.strArea+"\n\n"+
        "Instructions : "+"\n"+formatText(mealDto.strInstructions)+"\n\n"+"youtube Link : "+mealDto.strYoutube;
        return str;
    }

    private static String formatText(String strInstructions) {
        strInstructions = strInstructions.replace(". ", ".\n\n");
        return strInstructions;
    }
}
