package com.example.foodplanner.views.testsearch.view.meals;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.repositories.meal.MealRepoImplementation;
import com.example.foodplanner.views.testsearch.presenter.SearchPresenter;
import com.example.foodplanner.views.testsearch.presenter.SearchPresenterImplementation;
import com.example.foodplanner.views.testsearch.view.SearchFragmentDirections;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;


public class MealSearchFragment extends Fragment implements OnMealSearchClickListener {

    public EditText editTextSearch;
    RecyclerView recyclerView;
    Disposable disposable;
    SearchPresenter searchByNamePresenter;

    private MealSearchGridAdapter adapter;
    FrameLayout animationSearch;
    public MealSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchByNamePresenter = SearchPresenterImplementation.getInstance(MealRepoImplementation.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationSearch = view.findViewById(R.id.frame_animation_meal_search);
        editTextSearch = view.findViewById(R.id.searchByName_editText);
        recyclerView = view.findViewById(R.id.searchByName_recyclerView);
        editTextSearch.setVisibility(View.GONE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapter = new MealSearchGridAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        searchWithTextWitcher();
    }

    private void searchWithTextWitcher() {

        Observable<String> observable = Observable.create((ObservableOnSubscribe<String>) emitter -> editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emitter.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        })).debounce(300, TimeUnit.MILLISECONDS);

        observable.subscribe(this::searchMeal);

    }

    private void searchMeal(String s) {
        if (!s.isEmpty()) {
            disposable = searchByNamePresenter.searchMealByName(s).subscribe((rootMeal, throwable) -> {
                if (rootMeal != null && rootMeal.meals != null) {
                    adapter.setMeals(rootMeal.meals);
                    animationSearch.setVisibility(View.GONE);
                }
                else {
                    animationSearch.setVisibility(View.VISIBLE);
                    adapter.setMeals(new ArrayList<>());
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onClick(MealDto meal) {
        SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action = SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(meal.idMeal);
        Navigation.findNavController(requireView()).navigate(action);

    }

}