package com.example.foodplanner.views.splash;

import static com.example.foodplanner.MainActivity.TAG;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.views.auth.AuthFirebaseActivity;

public class SplashActivity extends AppCompatActivity {

    public static final String PREFERENCES_NAME="PREFERENCES";
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
                String uId =preferences.getString("uId","null");
                Log.i(TAG, "onCreate: SplashActivity UID = "+uId);

                if (uId.equals("null"))
                {
                    intent = new Intent(SplashActivity.this, AuthFirebaseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }, 2000);

   }
}