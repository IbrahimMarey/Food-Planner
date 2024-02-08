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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepo;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;
import com.example.foodplanner.views.auth.AuthFirebaseActivity;

public class ProfileFragment extends Fragment {

    Button logOutBtn;
    AuthFirebaseRepo authFirebaseRepo;
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logOutBtn = view.findViewById(R.id.logout_btn);
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
}