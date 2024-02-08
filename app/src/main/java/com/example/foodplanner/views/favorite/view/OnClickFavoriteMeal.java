package com.example.foodplanner.views.favorite.view;

import com.example.foodplanner.model.dtos.MealDto;

public interface OnClickFavoriteMeal {

    void onClickItem(String id);

    void onClickDeleteItem(MealDto mealDto);
}
