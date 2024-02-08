package com.example.foodplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;
import com.example.foodplanner.views.auth.AuthFirebaseActivity;
import com.example.foodplanner.views.splash.SplashActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    NavController navController;
    TextView toolBarTitle;
    boolean isAuth;
    BottomNavigationView navView;
    AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences(SplashActivity.PREFERENCES_NAME,Context.MODE_PRIVATE);
        String uId =preferences.getString("uId","null");
        Log.i(TAG, "onCreate: MainActivity UID = "+uId);

    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAuth = getIntent().getBooleanExtra("auth",false);
        toolBarTitle = findViewById(R.id.tool_bar_title);
        navView = findViewById(R.id.nav_view);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.searchFragment, R.id.favoriteFragment, R.id.planFragment,R.id.profileFragment).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            toolBarTitle.setText(destination.getLabel());

            if (destination.getId() == R.id.mealDetailsFragment) {
                navView.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.displaySearchMealsFragment) {
                navView.setVisibility(View.GONE);
            } else {
                myToolbar.setVisibility(View.VISIBLE);
                navView.setVisibility(View.VISIBLE);
            }
        });

        if (AuthFirebaseRepoImplementation.getInstance().isAuthenticated()) {
            FavAndPlanRepoImplementation.getInstance(this.getApplicationContext()).refreshMeals();
            FavAndPlanRepoImplementation.getInstance(this.getApplicationContext()).refreshPlanner();
        }

        if (!AuthFirebaseRepoImplementation.getInstance().isAuthenticated()) {
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    if (navDestination.getId() == R.id.planFragment || navDestination.getId() == R.id.favoriteFragment || navDestination.getId() == R.id.profileFragment) {
                        Snackbar snackbar = Snackbar.make(navView, "Please Login to Start all Features", Snackbar.LENGTH_SHORT);
                        snackbar.setAction("login now", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, AuthFirebaseActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        snackbar.show();
                        navController.navigateUp();
                    }
                }
            });
        }
    }
}