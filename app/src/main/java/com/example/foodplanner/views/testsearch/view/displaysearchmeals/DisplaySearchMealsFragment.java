package com.example.foodplanner.views.testsearch.view.displaysearchmeals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.helpers.CheckSearchBy;
import com.example.foodplanner.model.dtos.MealInfoDto;
import com.example.foodplanner.model.dtos.MealsInfoListDto;
import com.example.foodplanner.model.repositories.meal.MealRepoImplementation;
import com.example.foodplanner.views.testsearch.presenter.SearchPresenter;
import com.example.foodplanner.views.testsearch.presenter.SearchPresenterImplementation;

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
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DisplaySearchMealsFragment extends Fragment implements OnDisplayMealClicked
{
    private RecyclerView previewMealRecyclerView;
    private DisplaySearchMealsAdapter mealsAdapter;
    SearchPresenter searchPresenter;

    EditText filterResultOfSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchPresenter = SearchPresenterImplementation.getInstance(MealRepoImplementation.getInstance());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_search_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CheckSearchBy checkSearchBy = DisplaySearchMealsFragmentArgs.fromBundle(getArguments()).getSearchType();
        filterResultOfSearch = view.findViewById(R.id.input_search_result);
        filterResultOfSearch.setHint(getString(R.string.search_by_any)+checkSearchBy.getName());
        previewMealRecyclerView = view.findViewById(R.id.mealResults_recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        previewMealRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mealsAdapter = new DisplaySearchMealsAdapter(getContext() , new ArrayList<>(),DisplaySearchMealsFragment.this);
        if(checkSearchBy.getType() == CheckSearchBy.ingredient){
            searchPresenter.getMealByIngredient(checkSearchBy.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<MealsInfoListDto>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull MealsInfoListDto mealsInfoListDto) {
                            mealsAdapter.setMealInfoList(mealsInfoListDto.mealInfoList);
                            searchForMeal(mealsInfoListDto.mealInfoList);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        }
        else if(checkSearchBy.getType() == CheckSearchBy.country){
            searchPresenter.getMealByCountry(checkSearchBy.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<MealsInfoListDto>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull MealsInfoListDto mealsInfoListDto) {
                            mealsAdapter.setMealInfoList(mealsInfoListDto.mealInfoList);
                            searchForMeal(mealsInfoListDto.mealInfoList);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });

        }
        else if(checkSearchBy.getType() == CheckSearchBy.category){
            searchPresenter.getMealByCategory(checkSearchBy.getName()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<MealsInfoListDto>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull MealsInfoListDto mealsInfoListDto) {
                            mealsAdapter.setMealInfoList(mealsInfoListDto.mealInfoList);
                            searchForMeal(mealsInfoListDto.mealInfoList);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
        }
        previewMealRecyclerView.setAdapter(mealsAdapter);
    }
    private void searchForMeal(ArrayList<MealInfoDto> mealInfoDtos)
    {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                        filterResultOfSearch.addTextChangedListener(new TextWatcher() {
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
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        List<MealInfoDto> mealInfoDtoList = mealInfoDtos.stream().filter(category ->
                                category.strMeal.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        Log.d(MainActivity.TAG, "onNext: "+mealInfoDtoList.size());
                        mealsAdapter.setMealInfoList(mealInfoDtoList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(MainActivity.TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }
    @Override
    public void onDisplayMealClicked(String id)
    {
        DisplaySearchMealsFragmentDirections.ActionDisplaySearchMealsFragmentToMealDetailsFragment action = DisplaySearchMealsFragmentDirections.actionDisplaySearchMealsFragmentToMealDetailsFragment(id);
        Navigation.findNavController(requireView()).navigate(action);
    }
}