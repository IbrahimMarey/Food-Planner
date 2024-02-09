package com.example.foodplanner.views.profile;

import static com.example.foodplanner.views.splash.SplashActivity.PREFERENCES_NAME;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.local.AppDatabase;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.dtos.UserDto;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepo;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;
import com.example.foodplanner.network.firebase.realtime.RealTimeWrapper;
import com.example.foodplanner.network.firebase.realtime.UserDelegate;
import com.example.foodplanner.views.auth.AuthFirebaseActivity;

import java.util.List;

public class ProfileFragment extends Fragment implements UserDelegate {

    Button logOutBtn;
    AuthFirebaseRepo authFirebaseRepo;
    EditText userName,userEmail;
    TextView tvFav;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        authFirebaseRepo = AuthFirebaseRepoImplementation.getInstance();
        new RealTimeWrapper().getUser(ProfileFragment.this);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFav = view.findViewById(R.id.fav_profile);
        logOutBtn = view.findViewById(R.id.logout_btn);
        userName = view.findViewById(R.id.name_profile);
        userEmail = view.findViewById(R.id.email_profile);
        userName.setEnabled(false);
        userEmail.setEnabled(false);
        userName.setVisibility(View.GONE);
        userEmail.setVisibility(View.GONE);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                FavAndPlanRepoImplementation.getInstance(requireContext().getApplicationContext()).deleteAllPlan();
                                FavAndPlanRepoImplementation.getInstance(requireContext().getApplicationContext()).deleteAllFav();
                                authFirebaseRepo.logOut();

                                SharedPreferences preferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("uId","null");
                                editor.commit();
                                Toast.makeText(requireContext(), "LoggedOut successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), AuthFirebaseActivity.class);
//                                getActivity().finish();
                                startActivity(intent);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }

    @Override
    public void userSuccess(UserDto userDto) {
        if (userDto != null) {
            userName.setVisibility(View.VISIBLE);
            userEmail.setVisibility(View.VISIBLE);
            userName.setText(userDto.getName());
            userEmail.setText(userDto.getEmail());
        }
        AppDatabase.getInstance(getActivity()).mealDao().getAllFavMeals().observe(getViewLifecycleOwner(), new Observer<List<MealDto>>() {
            @Override
            public void onChanged(List<MealDto> mealDtos) {
                if (mealDtos.size()>0)
                    tvFav.setText(String.valueOf(mealDtos.size()));
                else
                    tvFav.setText("0");
            }
        });
    }

    @Override
    public void userFailure(String err) {

    }
}