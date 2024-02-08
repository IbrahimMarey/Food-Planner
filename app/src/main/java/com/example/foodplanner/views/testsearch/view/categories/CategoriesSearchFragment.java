package com.example.foodplanner.views.testsearch.view.categories;

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

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.helpers.CheckSearchBy;
import com.example.foodplanner.model.dtos.CategoriesListDto;
import com.example.foodplanner.model.dtos.CategoryDto;
import com.example.foodplanner.model.dtos.CuisineDto;
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
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CategoriesSearchFragment extends Fragment implements OnCategoryClicked{

    private RecyclerView categoriesSearchRecycler;
    public EditText categorySearchET;
    CategoriesSearchAdapter categoriesSearchAdapter;

    SearchPresenter searchPresenter;
    public CategoriesSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchPresenter = SearchPresenterImplementation.getInstance(MealRepoImplementation.getInstance());
        return inflater.inflate(R.layout.fragment_categories_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoriesSearchRecycler = view.findViewById(R.id.categories_search_recycler);
        categorySearchET = view.findViewById(R.id.search_filter_category_editText);
        categorySearchET.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        categoriesSearchAdapter = new CategoriesSearchAdapter(getContext(),new ArrayList<>(),CategoriesSearchFragment.this);
        categoriesSearchRecycler.setLayoutManager(layoutManager);
        categoriesSearchRecycler.setAdapter(categoriesSearchAdapter);
        getAllCategories();
    }

    void getAllCategories()
    {
        searchPresenter.getCategory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesListDto>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CategoriesListDto rootCategory) {
                        categoriesSearchAdapter.setCategoriesSearchList(rootCategory.categories);
                        searchForCategory(rootCategory.categories);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
    }

    @Override
    public void onCategoryClicked(String name)
    {
            CheckSearchBy checkSearchBy = new CheckSearchBy();
            checkSearchBy.setType(CheckSearchBy.category);
            checkSearchBy.setName(name);
            Log.i("Category", "onClick: " + name);
            SearchFragmentDirections.ActionSearchFragmentToDisplaySearchMealsFragment action = SearchFragmentDirections
                    .actionSearchFragmentToDisplaySearchMealsFragment(checkSearchBy);
            Navigation.findNavController(requireView()).navigate(action);
    }

    private void searchForCategory(ArrayList<CategoryDto> categoryList)
    {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<String> emitter) throws Throwable {
                        categorySearchET.addTextChangedListener(new TextWatcher() {
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
                        List<CategoryDto> categoryDtoList = categoryList.stream().filter(category ->
                                category.strCategory.toLowerCase().startsWith(s.toLowerCase())
                        ).collect(Collectors.toList());
                        Log.d(MainActivity.TAG, "onNext: "+categoryDtoList.size());
                        categoriesSearchAdapter.setCategoriesSearchList(categoryDtoList);
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
}