package com.example.foodplanner.model.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CuisinesListDto {
    @SerializedName("meals")
    public ArrayList<CuisineDto> cuisines;

    public CuisinesListDto(){

    }

    public ArrayList<CuisineDto> getCuisines() {
        return cuisines;
    }

    public void setCuisines(ArrayList<CuisineDto> cuisines) {
        this.cuisines = cuisines;
    }
}
