package com.example.foodplanner.views.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.CategoryDto;
import com.example.foodplanner.model.dtos.MealDto;

import java.util.ArrayList;
import java.util.List;

public class CategoriesHomeAdapter extends RecyclerView.Adapter<CategoriesHomeAdapter.ViewHolder> {

    List<CategoryDto> categoryDtoList;
    OnCategoryHomeClicked onCategoryHomeClicked;

    public CategoriesHomeAdapter(List<CategoryDto> categoryDtoList, OnCategoryHomeClicked onClick) {
        this.categoryDtoList = categoryDtoList;
        this.onCategoryHomeClicked = onClick;
    }

    @NonNull
    @Override
    public CategoriesHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_category, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHomeAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(categoryDtoList.get(position).strCategory);
        Glide.with(holder.getView().getContext())
                .load(categoryDtoList.get(position).strCategoryThumb)
                .placeholder(R.drawable.meal)
                .error(R.drawable.meal)
                .into(holder.getImageView());
        holder.getView().setOnClickListener(v->
        {
            onCategoryHomeClicked.onCategoryHomeClicked(categoryDtoList.get(position).getStrCategory());
        });
    }

    @Override
    public int getItemCount() {
        return categoryDtoList.size();
    }

    public void setCategories(ArrayList<CategoryDto> categoryDtoList) {
        this.categoryDtoList = categoryDtoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewName;
        View  view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.img_category_home);
            textViewName = itemView.findViewById(R.id.tv_category_name);
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

