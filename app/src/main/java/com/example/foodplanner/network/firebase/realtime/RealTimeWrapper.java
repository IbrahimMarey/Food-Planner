package com.example.foodplanner.network.firebase.realtime;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.dtos.UserDto;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RealTimeWrapper {
    private static final String TAG = "TAG";
    private FirebaseDatabase database;
    private DatabaseReference referencePlan;
    private DatabaseReference referenceFav;
    private DatabaseReference userReference;
    private AuthFirebaseRepoImplementation authFirebaseRepoImplementation;

    public RealTimeWrapper() {
        this.database = FirebaseDatabase.getInstance();
        authFirebaseRepoImplementation = AuthFirebaseRepoImplementation.getInstance();
        if (authFirebaseRepoImplementation.isAuthenticated()) {
            Log.i(TAG, "FireBaseRealTimeWrapper: auth ID = "+ authFirebaseRepoImplementation.getUser().getUid() );
            this.referenceFav = database.getReference().child("users").child(authFirebaseRepoImplementation.getUser().getUid()).child("favoritesMeal");
            this.referencePlan = database.getReference().child("users").child(authFirebaseRepoImplementation.getUser().getUid()).child("PlanMeal");
            this.userReference = database.getReference().child("users").child(authFirebaseRepoImplementation.getUser().getUid()).child("info");
        }
    }

    public void addToFav(MealDto mealDto, RealAddDelegate realAddDelegate) {
            referenceFav.child(mealDto.idMeal).setValue(mealDto)
                    .addOnCompleteListener(task -> {
                        realAddDelegate.onSuccess();
                    })
                    .addOnFailureListener(e -> {
                        realAddDelegate.onFailure(e.toString());
                    });
        }

    public void addToPlan(PlanDto planDto, RealAddDelegate realAddDelegate) {
        if (authFirebaseRepoImplementation.isAuthenticated()) {
            referencePlan.child(planDto.id).setValue(planDto).addOnCompleteListener(
                    task -> realAddDelegate.onSuccess())
                    .addOnFailureListener(e -> realAddDelegate.onFailure(e.toString()));

        }
    }

    public void removeMealFav(String mealId, RealRemoveDelegate realRemoveDelegate) {
        referenceFav.child(mealId).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        realRemoveDelegate.onSuccess();
            }
        });
    }

    public void removeMealPlan(String id, RealRemoveDelegate realRemoveDelegate) {
        referencePlan.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                realRemoveDelegate.onSuccess();
            }
        });
    }

    public void getFavMeals(RealFavDelegate realFavDelegate) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MealDto> mealDtos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mealDtos.add(snapshot.getValue(MealDto.class));
                }
                realFavDelegate.onSuccess(mealDtos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                realFavDelegate.onFailure(databaseError.toException().toString());
            }
        };
        Log.d(TAG, "getFavMeals: " + database.getReference().child("users").child(authFirebaseRepoImplementation.getUser().getUid()).child("favoritesMeal").getKey());
        referenceFav.addValueEventListener(postListener);
    }

    public void getPlan(RealPlanDelegate realPlanDelegate) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PlanDto> planDtos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    planDtos.add(snapshot.getValue(PlanDto.class));
                }
                realPlanDelegate.onSuccess(planDtos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                realPlanDelegate.onFailure(databaseError.toException().toString());
            }
        };
        referencePlan.addValueEventListener(postListener);
    }

    public void addUserData(UserDto userDto)
    {

        if (authFirebaseRepoImplementation.isAuthenticated()) {
            userReference.setValue(userDto).addOnCompleteListener(
                            task -> Log.i(TAG, "addUserData: done"))
                    .addOnFailureListener(e -> Log.i(TAG, "addUserData: err "+e.getMessage()));

        }
    }

    public void getUser(UserDelegate userDelegate) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDto userDto = dataSnapshot.getValue(UserDto.class);
                userDelegate.userSuccess(userDto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userDelegate.userFailure(databaseError.getMessage());
            }
        };
        userReference.addValueEventListener(postListener);
    }

}
