package com.example.foodplanner.views.testsearch.view.cuisines;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.foodplanner.R;
import com.example.foodplanner.helpers.CheckSearchBy;
import com.example.foodplanner.model.dtos.CuisineDto;
import com.example.foodplanner.model.dtos.CuisinesListDto;
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
import io.reactivex.rxjava3.schedulers.Schedulers;
public class CuisinesSearchFragment extends Fragment implements OnCuisineClicked {
    private RecyclerView countryRecyclerView;
    private CuisinesSearchAdapter countryAdapter;
    private SearchPresenter searchPresenter;
    public EditText searchByCountry;

    public CuisinesSearchFragment()
    {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchPresenter = SearchPresenterImplementation.getInstance(MealRepoImplementation.getInstance());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchByCountry = view.findViewById(R.id.search_filter_country_editText);
        searchByCountry.setVisibility(View.GONE);
        countryRecyclerView = view.findViewById(R.id.searchFilterByCountry_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        countryRecyclerView.setLayoutManager(layoutManager);
        countryAdapter = new CuisinesSearchAdapter(getContext() , new ArrayList<>(),CuisinesSearchFragment.this);
        getCuisines();
        countryRecyclerView.setAdapter(countryAdapter);
    }

    private void getCuisines() {
        searchPresenter.getCuisines().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CuisinesListDto>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CuisinesListDto rootCuisine) {
                        countryAdapter.setCuisinesList(rootCuisine.cuisines);
                        searchForCuisines(rootCuisine.cuisines);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }


    private void searchForCuisines(ArrayList<CuisineDto> cuisines) {


        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                        searchByCountry.addTextChangedListener(new TextWatcher() {
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
                .debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        List<CuisineDto> cuisineList = cuisines.stream().filter(cuisine ->
                                cuisine.strArea.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        countryAdapter.setCuisinesList(cuisineList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    @Override
    public void onCuisineClicked(String name)
    {
        CheckSearchBy checkSearchBy = new CheckSearchBy();
        checkSearchBy.setType(CheckSearchBy.country);
        checkSearchBy.setName(name);
        Log.i("Category", "onClick: " + name);
        SearchFragmentDirections.ActionSearchFragmentToDisplaySearchMealsFragment action = SearchFragmentDirections
                .actionSearchFragmentToDisplaySearchMealsFragment(checkSearchBy);
        Navigation.findNavController(requireView()).navigate(action);
    }
}