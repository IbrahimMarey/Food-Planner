package com.example.foodplanner.views.testsearch.view.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.IngredientDto;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class IngredientsSearchAdapter extends RecyclerView.Adapter<IngredientsSearchAdapter.ViewHolder> {

    private final Context context;
    private List<IngredientDto> ingredientList;
    private OnIngredientClicked onIngredientClicked;

    public void setIngredientsList(List<IngredientDto> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    public IngredientsSearchAdapter(Context context, List<IngredientDto> ingredientList,OnIngredientClicked onIngredientClicked) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.onIngredientClicked = onIngredientClicked;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.ingredient_item_search_fragment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        CheckSearchBy checkSearchBy = new CheckSearchBy();
        String name = ingredientList.get(holder.getAbsoluteAdapterPosition()).getStrIngredient();
        holder.ingredientTextView.setText(ingredientList.get(holder.getAbsoluteAdapterPosition()).getStrIngredient());
        Glide.with(holder.itemView.getContext()).load("https://www.themealdb.com/images/ingredients/" + name + "-Small.png").placeholder(R.drawable.meal).error(R.drawable.meal).into(holder.ingredientImage);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onIngredientClicked.onIngredientClicked(name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTextView;
        RoundedImageView ingredientImage;
        ConstraintLayout constraintLayout;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            ingredientTextView = v.findViewById(R.id.textViewIngredientNameItem);
            ingredientImage = v.findViewById(R.id.imageViewIngredientImageItem);
            constraintLayout = v.findViewById(R.id.row_ingredient_layout);
        }
    }
}
