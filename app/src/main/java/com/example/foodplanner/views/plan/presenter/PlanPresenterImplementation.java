package com.example.foodplanner.views.plan.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;

import java.util.List;

public class PlanPresenterImplementation implements PlanPresenter {
    private FavAndPlanRepoImplementation favAndPlanRepoImplementation;

    private static PlanPresenterImplementation planPresenterImplementation;


    public static synchronized PlanPresenterImplementation getInstance(FavAndPlanRepoImplementation favAndPlanRepoImplementation){
        if(planPresenterImplementation == null){
            planPresenterImplementation = new PlanPresenterImplementation(favAndPlanRepoImplementation);
        }
        return planPresenterImplementation;
    }

    private PlanPresenterImplementation(FavAndPlanRepoImplementation favAndPlanRepoImplementation) {
        this.favAndPlanRepoImplementation = favAndPlanRepoImplementation;
    }

    @Override
    public LiveData<List<PlanDto>> getMealPlanByDate(String date) {
        return favAndPlanRepoImplementation.getAllMealsPlanAtDate(date);
    }

    @Override
    public void removeItemPlan(PlanDto planDto) {
        favAndPlanRepoImplementation.deleteMealPlan(planDto);
    }
}
