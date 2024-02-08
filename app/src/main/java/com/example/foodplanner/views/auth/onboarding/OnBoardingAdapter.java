package com.example.foodplanner.views.auth.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;

public class OnBoardingAdapter extends PagerAdapter
{

    Context context;

    int[] animations = {R.raw.egg , R.raw.dite,R.raw.inspire};

    int[] headerBoardingList = {R.string.head_boarding_one , R.string.head_boarding_two,R.string.head_boarding_three};

    int[] bodyBoardingList = {R.string.body_boarding_one , R.string.body_boarding_two,R.string.body_boarding_three};

    public OnBoardingAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return animations.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.on_boarding_item,container,false);

        LottieAnimationView slideLotti = view.findViewById(R.id.splash_lotti);
        TextView slideHeader = view.findViewById(R.id.header_textView);
        TextView slideDescription = view.findViewById(R.id.description_textView);

        slideLotti.setAnimation(animations[position]);
        slideHeader.setText(headerBoardingList[position]);
        slideDescription.setText(bodyBoardingList[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((ConstraintLayout)object);
    }
}
