package com.example.foodplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.dtos.PlanDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TestFuteresActivity extends AppCompatActivity {
    public static final String TAG="TAG";
    Button addBtn;
    Button delBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    int i ;

    List<MealDto> mList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i=0;
        mList = new ArrayList<>();
        setContentView(R.layout.activity_test_futeres);
        addBtn = findViewById(R.id.add_btn);
        delBtn = findViewById(R.id.del_btn);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference().child("test_user");
        MealDto mealDto = new MealDto();
        mealDto.strMeal="foul";
        mealDto.idMeal="foul";
        mealDto.strCategory="breakfast";
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(String.valueOf(i)).setValue(mealDto);
                i++;
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        List<MealDto> mealDtoList= new ArrayList<>();
                        for (DataSnapshot db :snapshot.getChildren())
                        {
                            mealDtoList.add(db.getValue(MealDto.class));
                        }
                        Log.i(TAG, "onDataChange: "+mealDtoList.size());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                /*databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Log.i(TAG, "onComplete: "+task.getResult().getValue().toString());

                    }
                });*/
//                databaseReference.child(String.valueOf(i)).removeValue();
//                i--;
            }
        });
    }
}