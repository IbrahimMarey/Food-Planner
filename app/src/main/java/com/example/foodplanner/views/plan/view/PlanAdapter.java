package com.example.foodplanner.views.plan.view;

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
import com.example.foodplanner.model.dtos.PlanDto;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    private List<PlanDto> planDtos;
    private OnRemoveClickListener onRemoveClickListener;
    private ONItemClickListener onItemClickListener;


    public PlanAdapter(List<PlanDto> planDtos, OnRemoveClickListener onRemoveClickListener, ONItemClickListener onItemClickListener) {
        this.planDtos = planDtos;
        this.onRemoveClickListener = onRemoveClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    public void setMealPlanners(List<PlanDto> planDtos) {
        this.planDtos = planDtos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextViewTitle().setText(planDtos.get(position).strMeal);
        holder.getTextViewCountry().setText(planDtos.get(position).strArea);
        Glide.with(holder.itemView).load(planDtos.get(position).strMealThumb).into(holder.imageView);
        holder.getRow().setOnClickListener(v -> onItemClickListener.onClick(planDtos.get(holder.getAbsoluteAdapterPosition())));
        holder.addToCalendar.setOnClickListener(
                v -> onRemoveClickListener.onRemove(planDtos.get(holder.getAbsoluteAdapterPosition()),holder.getAbsoluteAdapterPosition())
        );
    }

    @Override
    public int getItemCount() {
        return planDtos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTitle;
        private final RoundedImageView imageView;
        private final TextView textViewCountry;
        private final CardView row;
        private final ImageView addToCalendar;
        View view;

        public View getView() {
            return view;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.titleMeal_textView);
            textViewCountry = itemView.findViewById(R.id.countryMeal_textView);
            imageView = itemView.findViewById(R.id.meal_imageView);
            row = itemView.findViewById(R.id.cardViewWeekPlanRow);
            view = itemView;
            addToCalendar = itemView.findViewById(R.id.addToCalendar_imageView);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public RoundedImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewCountry() {
            return textViewCountry;
        }

        public CardView getRow() {
            return row;
        }

    }
}
