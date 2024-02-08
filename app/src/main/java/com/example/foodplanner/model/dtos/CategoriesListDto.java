package com.example.foodplanner.model.dtos;

import java.util.ArrayList;

public class CategoriesListDto {

    public ArrayList<CategoryDto> categories;
    public CategoriesListDto(){

    }

    public ArrayList<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryDto> categories) {
        this.categories = categories;
    }
}
