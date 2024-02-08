package com.example.foodplanner.network.firebase.realtime;


import com.example.foodplanner.model.dtos.PlanDto;

import java.util.List;

public interface RealPlanDelegate {


    public void onSuccess(List<PlanDto> planDtos);

    public void onFailure(String message);
}
