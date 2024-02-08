package com.example.foodplanner.helpers;

import com.example.foodplanner.model.dtos.MealDto;

import java.util.ArrayList;

public class GetArrayFromMeal {


    public static ArrayList<IngredientWithMeasure> getArrayList(MealDto mealDto) {
        ArrayList<IngredientWithMeasure> list = new ArrayList<>();
        makeArrayFromMeal(mealDto, list);
        return list;
    }

    private static void makeArrayFromMeal(MealDto mealDto, ArrayList<IngredientWithMeasure> list) {
        addToArray(mealDto.strIngredient1, mealDto.strMeasure1,list);
        addToArray(mealDto.strIngredient2, mealDto.strMeasure2,list);
        addToArray(mealDto.strIngredient3, mealDto.strMeasure3,list);
        addToArray(mealDto.strIngredient4, mealDto.strMeasure4,list);
        addToArray(mealDto.strIngredient5, mealDto.strMeasure5,list);
        addToArray(mealDto.strIngredient6, mealDto.strMeasure6,list);
        addToArray(mealDto.strIngredient7, mealDto.strMeasure7,list);
        addToArray(mealDto.strIngredient8, mealDto.strMeasure8,list);
        addToArray(mealDto.strIngredient9, mealDto.strMeasure9,list);
        addToArray(mealDto.strIngredient10, mealDto.strMeasure10,list);
        addToArray(mealDto.strIngredient11, mealDto.strMeasure11,list);
        addToArray(mealDto.strIngredient12, mealDto.strMeasure12,list);
        addToArray(mealDto.strIngredient13, mealDto.strMeasure13,list);
        addToArray(mealDto.strIngredient14, mealDto.strMeasure14,list);
        addToArray(mealDto.strIngredient15, mealDto.strMeasure15,list);
        addToArray(mealDto.strIngredient16, mealDto.strMeasure16,list);
        addToArray(mealDto.strIngredient17, mealDto.strMeasure17,list);
        addToArray(mealDto.strIngredient18, mealDto.strMeasure18,list);
        addToArray(mealDto.strIngredient19, mealDto.strMeasure19,list);
        addToArray(mealDto.strIngredient20, mealDto.strMeasure20,list);
        }

    private static void addToArray(String strIngredient, String strMeasure, ArrayList<IngredientWithMeasure> list) {
        if (strIngredient != null && !strIngredient.isEmpty()) {
            String measure;
            if(strMeasure == null){
                measure = "";
            }else{
                measure = strMeasure;
            }

            list.add(new IngredientWithMeasure(strIngredient, measure));
        }
    }


}
