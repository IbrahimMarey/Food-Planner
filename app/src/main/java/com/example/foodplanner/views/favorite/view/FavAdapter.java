package com.example.foodplanner.views.favorite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.MealDto;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder>{

    private final Context context;
    private List<MealDto> favMealsList;

    public void setFavMealsListList(List<MealDto> favMealsList) {
        this.favMealsList = favMealsList;
        notifyDataSetChanged();
    }

    private OnClickFavoriteMeal listener;

    public FavAdapter(Context context, List<MealDto> favMealsList , OnClickFavoriteMeal listener ) {
        this.context = context;
        this.favMealsList = favMealsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_fav_meal,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mealNameTextView.setText(favMealsList.get(position).strMeal);
        Glide.with(context)
                .load(favMealsList.get(position).strMealThumb)
                .into(holder.mealImage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(favMealsList.get(holder.getAbsoluteAdapterPosition()).idMeal);
            }
        });

        holder.deleteMealIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDeleteItem(favMealsList.get(holder.getAbsoluteAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return favMealsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mealNameTextView;
        RoundedImageView mealImage;
        RoundedImageView deleteMealIcon;
        CardView cardView;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;

            mealNameTextView = v.findViewById(R.id.tv_meal_title_fav_item);
            mealImage = v.findViewById(R.id.imageView_meal_fav_item);
            deleteMealIcon = v.findViewById(R.id.ic_remove_meal_fav_item);
            cardView = v.findViewById(R.id.cardViewRandomMeal);
        }
    }

}
