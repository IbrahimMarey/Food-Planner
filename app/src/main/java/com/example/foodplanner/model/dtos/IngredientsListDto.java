package com.example.foodplanner.model.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IngredientsListDto {
    @SerializedName("meals")
    public ArrayList<IngredientDto> ingredients;

    public ArrayList<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }
}
