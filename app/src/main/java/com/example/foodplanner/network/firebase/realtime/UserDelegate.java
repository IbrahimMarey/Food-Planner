package com.example.foodplanner.network.firebase.realtime;

import com.example.foodplanner.model.dtos.UserDto;

public interface UserDelegate
{
    public void userSuccess(UserDto userDto);
    public void userFailure(String err);
}
