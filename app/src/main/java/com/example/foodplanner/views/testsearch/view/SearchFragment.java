package com.example.foodplanner.views.testsearch.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.views.testsearch.view.categories.CategoriesSearchFragment;
import com.example.foodplanner.views.testsearch.view.cuisines.CuisinesSearchFragment;
import com.example.foodplanner.views.testsearch.view.ingredients.IngredientsSearchFragment;
import com.example.foodplanner.views.testsearch.view.meals.MealSearchFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class SearchFragment extends Fragment {

    ChipGroup chipGroup;
    EditText searchInput;

    CategoriesSearchFragment categoriesSearchFragment;
    CuisinesSearchFragment cuisinesSearchFragment;
    IngredientsSearchFragment ingredientsSearchFragment;
    MealSearchFragment mealSearchFragment;
    String fragment ="";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        categoriesSearchFragment = new CategoriesSearchFragment();
        cuisinesSearchFragment = new CuisinesSearchFragment();
        ingredientsSearchFragment = new IngredientsSearchFragment();
        mealSearchFragment = new MealSearchFragment();
        return inflater.inflate(R.layout.fragment_search_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chipGroup = view.findViewById(R.id.chip_search_group);
        searchInput= view.findViewById(R.id.input_search);
        setupChips();
        searchInput.addTextChangedListener(searchWatcher());

    }

    void setupChips()
    {
        for (int i=0; i<chipGroup.getChildCount();i++)
        {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        if (chip.getText().toString().equals("Meal"))
                        {
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_search_container, mealSearchFragment);
                            fragmentTransaction.commit();
                            fragment="Meal";
                            searchInput.setHint(R.string.search_by_meal);
                        } else if (chip.getText().toString().equals("Categories")) {
                            searchInput.setHint(R.string.search_by_categories);
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_search_container, categoriesSearchFragment);
                            fragmentTransaction.commit();
                            fragment="Categories";
                        }else if (chip.getText().toString().equals("Ingredient")) {
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_search_container, ingredientsSearchFragment);
                            fragmentTransaction.commit();
                            fragment="Ingredient";
                            searchInput.setHint(R.string.search_by_ingredient);
                        }else if (chip.getText().toString().equals("Countries")) {
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_search_container, cuisinesSearchFragment);
                            fragmentTransaction.commit();
                            fragment = "Countries";
                            searchInput.setHint(R.string.search_by_cuisine);
                        }
                    }else
                    {
                        searchInput.setHint(R.string.select_search);
                    }
                }
            });
            if (chip.getText().toString().equals("Categories")) {
                chip.setChecked(true); // Select "Categories" chip by default
            }
        }
    }

    TextWatcher searchWatcher()
    {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (fragment)
                {
                    case "Meal": {
                        if (mealSearchFragment.editTextSearch!= null)
                            mealSearchFragment.editTextSearch.setText(s.toString());
                        break;
                    }
                    case "Categories":
                        if (categoriesSearchFragment.categorySearchET!=null)
                            categoriesSearchFragment.categorySearchET.setText(s.toString());
                        break;
                    case "Ingredient":
                        if (ingredientsSearchFragment.searchByIngredient!=null)
                            ingredientsSearchFragment.searchByIngredient.setText(s.toString());
                        break;
                    case "Countries":
                        if (cuisinesSearchFragment.searchByCountry!=null)
                            cuisinesSearchFragment.searchByCountry.setText(s.toString());
                        break;
                    default:
                        break;
                }
            }
        };
    }
}