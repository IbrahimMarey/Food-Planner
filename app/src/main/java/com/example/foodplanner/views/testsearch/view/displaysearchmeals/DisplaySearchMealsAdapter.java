package com.example.foodplanner.views.testsearch.view.displaysearchmeals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.MealInfoDto;

import java.util.List;

public class DisplaySearchMealsAdapter extends RecyclerView.Adapter<DisplaySearchMealsAdapter.ViewHolder> {
    private final Context context;
    private List<MealInfoDto> mealInfoList;
    private OnDisplayMealClicked mealClicked;

    public  void setMealInfoList(List<MealInfoDto> mealInfoList)
    {
        this.mealInfoList = mealInfoList;
        notifyDataSetChanged();
    }

    public DisplaySearchMealsAdapter(Context context, List<MealInfoDto> mealInfoList,OnDisplayMealClicked mealClicked)
    {
        this.context = context;
        this.mealInfoList = mealInfoList;
        this.mealClicked = mealClicked;
    }

    @NonNull
    @Override
    public DisplaySearchMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_main_meal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplaySearchMealsAdapter.ViewHolder holder, int position) {
        holder.mealNameTextView.setText(mealInfoList.get(position).getStrMeal());
        Glide.with(context)
                .load(mealInfoList.get(position).getStrMealThumb())
                .into(holder.mealImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealClicked.onDisplayMealClicked(mealInfoList.get(position).idMeal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mealNameTextView;
        ImageView mealImage;
        CardView cardView;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            mealNameTextView = v.findViewById(R.id.textView_meal_title_item_main);
            mealImage = v.findViewById(R.id.imageView_item_main);
            cardView = v.findViewById(R.id.cardView_item_main);
        }
    }
}
