package com.example.foodplanner.views.plan.view;


import com.example.foodplanner.model.dtos.PlanDto;

public interface OnRemoveClickListener {
    void onRemove(PlanDto planDto, int position);
}
