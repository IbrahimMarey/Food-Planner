package com.example.foodplanner.views.auth.onboarding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.views.splash.SplashActivity;

public class OnBoardingFragment extends Fragment {
    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    TextView backTV, nextTV, skipTV;

    TextView[] dots;
    OnBoardingAdapter onBoardingAdapter;
    public OnBoardingFragment() {
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
        return inflater.inflate(R.layout.fragment_on_boarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // check if boarding open before /////////////////////
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SplashActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean isBoarding = sharedPreferences.getBoolean("boarding",false);
        Log.i(MainActivity.TAG, "onCreateView: "+isBoarding+" "+sharedPreferences.getBoolean("boarding",false));
        if (isBoarding)
        {
            Log.i(MainActivity.TAG, "isBoarding: ="+ isBoarding);
            Navigation.findNavController(requireView()).navigate(R.id.action_onBoardingFragment_to_loginFragment);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("boarding",true);
        editor.commit();
        ///////////////////////////////////////////////////
        mSLideViewPager = (ViewPager) view.findViewById(R.id.slider_viewPager_onBoarding);
        mDotLayout = (LinearLayout) view.findViewById(R.id.indicator_layout);

        onBoardingAdapter = new OnBoardingAdapter(getActivity());

        mSLideViewPager.setAdapter(onBoardingAdapter);

        setUpBoardingSlider(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
        backTV = view.findViewById(R.id.back_textView_onboarding);
        nextTV = view.findViewById(R.id.next_textView_onboarding);
        skipTV = view.findViewById(R.id.skip_textView_onboarding);
        backTV.setVisibility(View.GONE);
        backTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "=============", Toast.LENGTH_SHORT).show();
                if (getBoardingItem(0) > 0){
                    mSLideViewPager.setCurrentItem(getBoardingItem(-1),true);

                }

            }
        });

        nextTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getBoardingItem(0) < 1)
                    mSLideViewPager.setCurrentItem(getBoardingItem(1),true);
                else {
                    Navigation.findNavController(v).navigate(R.id.action_onBoardingFragment_to_loginFragment);
                }

            }
        });

        skipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_onBoardingFragment_to_loginFragment);
            }
        });
    }

    public void setUpBoardingSlider(int position){

        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getActivity().getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active,getActivity().getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpBoardingSlider(position);

            if (position > 0){
                backTV.setVisibility(View.VISIBLE);
            }else {
                backTV.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private int getBoardingItem(int i){
        return mSLideViewPager.getCurrentItem() + i;
    }
}