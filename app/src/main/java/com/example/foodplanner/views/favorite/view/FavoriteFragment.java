package com.example.foodplanner.views.favorite.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;
import com.example.foodplanner.model.repositories.mealplanrepo.RealTimeListener;
import com.example.foodplanner.network.InternetConnection;
import com.example.foodplanner.views.favorite.presenter.FavPresenter;
import com.example.foodplanner.views.favorite.presenter.FavPresenterImplementation;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnClickFavoriteMeal {

    RecyclerView favMealRecyclerView;
    FavAdapter favAdapter;
    FavPresenter favPresenter;

    private InternetConnection internetConnection;
    FrameLayout frameLayout;
    List<MealDto> mealFavList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favPresenter = FavPresenterImplementation.getInstance(FavAndPlanRepoImplementation.getInstance(getContext()));
        internetConnection = new InternetConnection(requireContext());
        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = view.findViewById(R.id.frame_animation_fav);
        favMealRecyclerView = view.findViewById(R.id.favoriteMeals_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        favMealRecyclerView.setLayoutManager(linearLayoutManager);
        favAdapter = new FavAdapter(getContext() , new ArrayList<>() ,this);
        favMealRecyclerView.setAdapter(favAdapter);
        favPresenter.getFavoriteMeals().observe(getViewLifecycleOwner(), new Observer<List<MealDto>>() {
            @Override
            public void onChanged(List<MealDto> mealDtos) {
                mealFavList=mealDtos;
                if (mealDtos.size()>0)
                    frameLayout.setVisibility(View.GONE);
                favAdapter.setFavMealsListList(mealDtos);
            }
        });
    }

    @Override
    public void onClickItem(String id) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment2 action =FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment2(id);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onClickDeleteItem(MealDto mealDto) {
        if (internetConnection.isConnectedWifi() || internetConnection.isConnectedMobile()){
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.Delete_Meal)
                    .setMessage(R.string.Are_you_sure_you_want_to_delete_this_meal)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            favPresenter.deleteMealFromFav(mealDto, new RealTimeListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            if (mealFavList.size()<1)
                frameLayout.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(requireContext(), "Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
        }
    }
}