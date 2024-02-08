package com.example.foodplanner.views.testsearch.view.ingredients;

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
import com.example.foodplanner.helpers.CheckSearchBy;
import com.example.foodplanner.model.dtos.IngredientDto;
import com.example.foodplanner.model.dtos.IngredientsListDto;
import com.example.foodplanner.model.repositories.meal.MealRepoImplementation;
import com.example.foodplanner.views.testsearch.presenter.SearchPresenter;
import com.example.foodplanner.views.testsearch.presenter.SearchPresenterImplementation;
import com.example.foodplanner.views.testsearch.view.SearchFragmentDirections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class IngredientsSearchFragment extends Fragment implements OnIngredientClicked
{

    private RecyclerView ingredientRecyclerView;
    private IngredientsSearchAdapter ingredientAdapter;
    private SearchPresenter searchPresenter;

    public EditText searchByIngredient;
    FrameLayout animationIngredientSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchPresenter = SearchPresenterImplementation.getInstance(MealRepoImplementation.getInstance());


        return inflater.inflate(R.layout.fragment_all_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationIngredientSearch = view.findViewById(R.id.frame_animation_ingredients_search);
        searchByIngredient = view.findViewById(R.id.searchFilterByIngredient_editText);
        searchByIngredient.setVisibility(View.GONE);
        ingredientRecyclerView = view.findViewById(R.id.searchFilterByIngredient_recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        ingredientRecyclerView.setLayoutManager(gridLayoutManager);
        ingredientAdapter = new IngredientsSearchAdapter(getContext(), new ArrayList<>(),IngredientsSearchFragment.this);

        getAllIngredients();

        ingredientRecyclerView.setAdapter(ingredientAdapter);
    }

    private void searchForIngredients(ArrayList<IngredientDto> ingredients) {

         Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                        searchByIngredient.addTextChangedListener(new TextWatcher() {
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
                        });
                    }
                })
                 .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        List<IngredientDto> ingredientList = ingredients.stream().filter(ingredient ->
                            ingredient.strIngredient.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        ingredientAdapter.setIngredientsList(ingredientList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }

                });

    }

    private void getAllIngredients() {
        searchPresenter.getIngredients()
                .subscribe(new SingleObserver<IngredientsListDto>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull IngredientsListDto rootIngredient) {

                        searchForIngredients(rootIngredient.ingredients);
                        ingredientAdapter.setIngredientsList(rootIngredient.ingredients);
                        animationIngredientSearch.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    @Override
    public void onIngredientClicked(String name) {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        checkSearchBy.setType(CheckSearchBy.ingredient);
        checkSearchBy.setName(name);
        Log.i("Category", "onClick: " + name);
        SearchFragmentDirections.ActionSearchFragmentToDisplaySearchMealsFragment action = SearchFragmentDirections
                .actionSearchFragmentToDisplaySearchMealsFragment(checkSearchBy);
        Navigation.findNavController(requireView()).navigate(action);
    }
}