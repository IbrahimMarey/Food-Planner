package com.example.foodplanner.views.testsearch.view.categories;

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
import com.example.foodplanner.model.dtos.CategoryDto;

import java.util.List;


public class CategoriesSearchAdapter extends RecyclerView.Adapter<CategoriesSearchAdapter.ViewHolder>{

    private final Context context;
    private List<CategoryDto> categoryList;


    public void setCategoriesSearchList(List<CategoryDto> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    private final OnCategoryClicked onCategoryClicked;



    public CategoriesSearchAdapter(Context context, List<CategoryDto> categoryList,OnCategoryClicked onCategoryClicked )
    {
        this.context = context;
        this.categoryList = categoryList;
        this.onCategoryClicked = onCategoryClicked;
    }

    @NonNull
    @Override
    public CategoriesSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.category_search_row_layout,parent,false);
        CategoriesSearchAdapter.ViewHolder vh = new CategoriesSearchAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesSearchAdapter.ViewHolder holder, int position) {
        String name = categoryList.get(position).getStrCategory();
        holder.categoryTextView.setText(categoryList.get(position).getStrCategory());
        Glide.with(context)
                .load(categoryList.get(position).getStrCategoryThumb())
                .into(holder.categoryImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryClicked.onCategoryClicked(name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categoryTextView;
        ImageView categoryImage;
        CardView cardView;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            categoryTextView = v.findViewById(R.id.cateroryName_textView_card);
            categoryImage = v.findViewById(R.id.category_imageView_card);
            cardView = v.findViewById(R.id.category_cardView_search);
        }
    }
}

