package com.example.foodplanner.views.plan.presenter;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.model.dtos.PlanDto;

import java.util.List;

public interface PlanPresenter {

    LiveData<List<PlanDto>> getMealPlanByDate(String date);

   void removeItemPlan(PlanDto planDto);
}
