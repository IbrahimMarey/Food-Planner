package com.example.foodplanner.views.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.helpers.CheckSearchBy;
import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.repositories.meal.MealRepoImplementation;
import com.example.foodplanner.views.home.presenter.HomePresenter;
import com.example.foodplanner.views.home.presenter.HomePresenterImplementation;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment implements OnCategoryHomeClicked
{
    private RecyclerView recyclerView;
    private TextView textViewMealName;
    private TextView textViewMealCountry;
    private RoundedImageView imageViewDishOfTheDay;

    private CategoriesHomeAdapter adapter;
    private HomePresenter homeFragmentPresenter;
    CardView cardView;
    FrameLayout animationFrameHome;
    private Disposable disposableRandomMeal;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeFragmentPresenter = HomePresenterImplementation.getInstance(MealRepoImplementation.getInstance());
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationFrameHome = view.findViewById(R.id.frame_animation_home);
        animationFrameHome.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.rc_category_home);
        textViewMealName = view.findViewById(R.id.txtViewMealNameDishOfTheDay);
        textViewMealCountry = view.findViewById(R.id.textViewCountryDishOfTheDay);
        imageViewDishOfTheDay = view.findViewById(R.id.imageViewDishOfTheDay);
        cardView = view.findViewById(R.id.cardViewRandomMeal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        adapter = new CategoriesHomeAdapter(new ArrayList<>(),HomeFragment.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getRandomMeal();
        getAllCategories();
    }

    void getRandomMeal() {
        disposableRandomMeal = homeFragmentPresenter.getRandomMeal().subscribe((rootMeal, throwable) -> {
            if (rootMeal != null && rootMeal.meals != null) {
                MealDto mealDto = rootMeal.meals.get(0);
                animationFrameHome.setVisibility(View.GONE);
                setMealIntoTheView(mealDto);
            }
        });
    }
    private void setMealIntoTheView(MealDto mealDto) {
        textViewMealName.setText(mealDto.strMeal);
        textViewMealCountry.setText(mealDto.strArea);
        Glide.with(requireContext())
                .load(mealDto.strMealThumb)
                .placeholder(R.drawable.meal)
                .error(R.drawable.meal)
                .into(imageViewDishOfTheDay);
        cardView.setOnClickListener(v -> {
            HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action = HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(mealDto.idMeal);
            Navigation.findNavController(v).navigate(action);
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.disposableRandomMeal != null && disposableRandomMeal.isDisposed()) {
            disposableRandomMeal.dispose();
        }
    }


    void getAllCategories()
    {
        homeFragmentPresenter.getCategory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesListDto>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoriesListDto rootCategory) {
                        adapter.setCategories(rootCategory.categories);
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }
    @Override
    public void onCategoryHomeClicked(String name)
    {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        checkSearchBy.setType(CheckSearchBy.category);
        checkSearchBy.setName(name);
        HomeFragmentDirections.ActionHomeFragmentToDisplaySearchMealsFragment action = HomeFragmentDirections.actionHomeFragmentToDisplaySearchMealsFragment(checkSearchBy);
        Navigation.findNavController(requireView()).navigate(action);
    }
}