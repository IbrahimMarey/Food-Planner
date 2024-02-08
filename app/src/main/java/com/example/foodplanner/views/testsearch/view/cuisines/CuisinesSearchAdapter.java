package com.example.foodplanner.views.testsearch.view.cuisines;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.CuisineDto;

import java.util.List;


public class CuisinesSearchAdapter extends RecyclerView.Adapter<CuisinesSearchAdapter.ViewHolder> {

    private final Context context;
    private List<CuisineDto> countryList;

    public void setCuisinesList(List<CuisineDto> countryList) {
        this.countryList = countryList;
        notifyDataSetChanged();
    }

    private OnCuisineClicked onCuisineClicked;

    public CuisinesSearchAdapter(Context context, List<CuisineDto> countryList,OnCuisineClicked onCuisineClicked)
    {
        this.context = context;
        this.countryList = countryList;
        this.onCuisineClicked = onCuisineClicked;
    }

    @NonNull
    @Override
    public CuisinesSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.country_search_row_layout, parent, false);
        CuisinesSearchAdapter.ViewHolder vh = new CuisinesSearchAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CuisinesSearchAdapter.ViewHolder holder, int position) {
        String name = countryList.get(position).getStrArea();
        if (name.equals("Unknown"))
            name = "Palestine";
        holder.countryTextView.setText(name);
        holder.flag.setImageResource(getImage(name));

        String finalName = name;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCuisineClicked.onCuisineClicked(countryList.get(position).getStrArea());
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryTextView;
        ImageView flag;
        CardView cardView;
        View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            flag = v.findViewById(R.id.img_flag);
            countryTextView = v.findViewById(R.id.country_TextView_search);
            Log.i("texxxxt", "TextView " + countryTextView);
            cardView = v.findViewById(R.id.card_country_search_item);
        }
    }

    int getImage(String cuisine)
    {
        int flag;
        switch (cuisine)
        {
            case "American":
                return R.drawable.us;
            case "British":
                return R.drawable.uk;
            case "Canadian":
                return R.drawable.canada;
            case "Chinese":
                return R.drawable.china;
            case "Croatian":
                return R.drawable.croatian;
            case "Dutch":
                return R.drawable.netherlands;
            case "Egyptian":
                return R.drawable.egypt;
            case "Filipino":
                return R.drawable.philippines;
            case "French":
                return R.drawable.france;
            case "Greek":
                return R.drawable.greek;
            case "Indian":
                return R.drawable.indian;
            case "Irish":
                return R.drawable.irish;
            case "Italian":
                return R.drawable.italy;
            case "Jamaican":
                return R.drawable.jamaican;
            case "Japanese":
                return R.drawable.japan;
            case "Kenyan":
                return R.drawable.kenya;
            case "Malaysian":
                return R.drawable.malaysia;
            case "Mexican":
                return R.drawable.mexican;
            case "Moroccan":
                return R.drawable.morocco;
            case "Polish":
                return R.drawable.polish;
            case "Portuguese":
                return R.drawable.portuguese;
            case "Russian":
                return R.drawable.russia;
            case "Spanish":
                return R.drawable.spanish;
            case "Thai":
                return R.drawable.thailand;
            case "Tunisian":
                return R.drawable.tunisian;
            case "Turkish":
                return R.drawable.turkey;
            case "Vietnamese":
                return R.drawable.vietnam;
            default:
                return R.drawable.palestine;
        }
    }

}

