package com.example.foodplanner.views.testsearch.view.meals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.MealDto;

import java.util.ArrayList;
import java.util.List;

public class MealSearchGridAdapter extends RecyclerView.Adapter<MealSearchGridAdapter.ViewHolder> {

    List<MealDto> mealsDto;
    OnMealSearchClickListener onItemHomeClickListener;

    public MealSearchGridAdapter(List<MealDto> mealsDto, OnMealSearchClickListener onItemHomeClickListener) {
        this.mealsDto = mealsDto;
        this.onItemHomeClickListener = onItemHomeClickListener;
    }

    @NonNull
    @Override
    public MealSearchGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_meal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealSearchGridAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(mealsDto.get(position).strMeal);
        Glide.with(holder.getView().getContext())
                .load(mealsDto.get(position).strMealThumb)
                .placeholder(R.drawable.meal)
                .error(R.drawable.meal)
                .into(holder.getImageView());
        holder.getView().setOnClickListener(v->{
            onItemHomeClickListener.onClick(mealsDto.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mealsDto.size();
    }

    public void setMeals(ArrayList<MealDto> mealDtos) {
        this.mealsDto = mealDtos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewName;
        View  view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.imageView_item_home);
            textViewName = itemView.findViewById(R.id.tv_meal_title_item_home);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public View getView() {
            return view;
        }


    }
}

