package com.example.foodplanner.views.mealdetails.view;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.helpers.ConvertMealToString;
import com.example.foodplanner.helpers.DateFormatter;
import com.example.foodplanner.helpers.GetArrayFromMeal;
import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.dtos.MealDto;
import com.example.foodplanner.helpers.ConvertMealToPlan;
import com.example.foodplanner.model.repositories.auth.AuthFirebaseRepoImplementation;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;
import com.example.foodplanner.model.repositories.mealplanrepo.RealTimeListener;
import com.example.foodplanner.model.repositories.meal.MealRepoImplementation;
import com.example.foodplanner.network.InternetConnection;
import com.example.foodplanner.views.auth.AuthFirebaseActivity;
import com.example.foodplanner.views.mealdetails.presenter.MealDetailsPresenter;
import com.example.foodplanner.views.mealdetails.presenter.MealDetailsPresenterImplementation;
import com.google.android.material.snackbar.Snackbar;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.disposables.Disposable;
public class MealDetailsFragment extends Fragment {


    public static final String TAG = "TAG";
    MealDetailsPresenter mealsDetailsPresenter;
    IngredientsAdapter ingredientsAdapter;

    CircleImageView imageViewADDToWeekPlanner;
    YouTubePlayerView yt;
    RecyclerView recyclerViewIngredientsItemDetails;
    Disposable disposable;

    InternetConnection internetConnection;

    TextView txtViewMealNameItemDetails;
    TextView textViewMealCateItemDetails;
    TextView textViewMealCountryItemDetails;
    TextView textViewProcedures;
    ImageView mealImage;
    CircleImageView imageViewAddToFavITemDetails;
    AuthFirebaseRepoImplementation authFirebaseRepoImplementation;
    View view;
    Button addToPhoneCalender;
    boolean isFav ;
    public MealDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mealsDetailsPresenter = MealDetailsPresenterImplementation.getInstance(MealRepoImplementation.getInstance(), FavAndPlanRepoImplementation.getInstance(requireContext()));
        internetConnection = new InternetConnection(requireContext());
        view= inflater.inflate(R.layout.fragment_meal_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addToPhoneCalender = view.findViewById(R.id.add_to_mob_calender);
        txtViewMealNameItemDetails = view.findViewById(R.id.txtViewMealNameItemDetails);
        textViewMealCateItemDetails = view.findViewById(R.id.textViewMealCateItemDetails);
        textViewMealCountryItemDetails = view.findViewById(R.id.textViewMealCountryItemDetails);
        textViewProcedures = view.findViewById(R.id.textViewProcedures);
        mealImage = view.findViewById(R.id.mealImage);
        imageViewAddToFavITemDetails = view.findViewById(R.id.imageViewAddToFavITemDetails);
        yt=view.findViewById(R.id.ytPlayer);
        imageViewADDToWeekPlanner = view.findViewById(R.id.imageViewAddToCalendarItemDetails);
        recyclerViewIngredientsItemDetails =view.findViewById(R.id.recyclerViewIngredientsItemDetails);
        ingredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        recyclerViewIngredientsItemDetails.setAdapter(ingredientsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewIngredientsItemDetails.setLayoutManager(linearLayoutManager);
        String id = MealDetailsFragmentArgs.fromBundle(requireArguments()).getMealID();
        getMealFromNetwork(id);
        getMealFromLocal(id);
    }
    private void getMealFromLocal(String id) {
        mealsDetailsPresenter.getMealFromFavById(id).observe(getViewLifecycleOwner(), new Observer<MealDto>() {
            @Override
            public void onChanged(MealDto mealDto) {
                if (mealDto !=null) {
                    isFav = true;
                    imageViewAddToFavITemDetails.setImageResource(R.drawable.ic_fav);

                }else {
                    isFav = false;
                }
            }
        });
    }

    private void openDatePicker(MealDto mealDto) {
        DialogFragment newFragment = new DatePickerFragment(mealDto, mealsDetailsPresenter,MealDetailsFragment.this);
        newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
    }

    private void getMealFromNetwork(String id) {
        disposable = mealsDetailsPresenter.getMeal(id).subscribe((rootMeal, throwable) -> {
            if (rootMeal != null) {
                List<MealDto> mealDtos = rootMeal.meals;

                if (mealDtos != null) {
                    MealDto mealDto = mealDtos.get(0);
                    setMealToTheView(mealDto);
                }
            }
        });
    }

    private void setMealToTheView(MealDto mealDto) {
        Log.i(TAG, "setMealToTheView: "+ mealDto.idMeal);
        txtViewMealNameItemDetails.setText(mealDto.strMeal);
        textViewMealCateItemDetails.setText(mealDto.strCategory);
        textViewMealCountryItemDetails.setText(mealDto.strArea);
        textViewProcedures.setText(formatText(mealDto.strInstructions));
        Glide.with(requireContext())
                .load(mealDto.strMealThumb)
                .placeholder(R.drawable.meal)
                .error(R.drawable.meal)
                .into(mealImage);
        ingredientsAdapter.setList(GetArrayFromMeal.getArrayList(mealDto));
        imageViewADDToWeekPlanner.setOnClickListener((v) -> {
            if(internetConnection.isConnectedMobile() || internetConnection.isConnectedWifi())
            {
                if(authFirebaseRepoImplementation.getInstance().isAuthenticated())
                {
                    openDatePicker(mealDto);
                }else {
                    showAuthSnack();
                }
            }else{
                Toast.makeText(requireContext(), "Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
            }
        });

        yt.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getYoutubeId(mealDto.strYoutube);
                youTubePlayer.cueVideo(videoId, 0);
            }
        });


        imageViewAddToFavITemDetails.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(internetConnection.isConnectedMobile() || internetConnection.isConnectedWifi()){
                    if (authFirebaseRepoImplementation.getInstance().isAuthenticated()) {
                        if (isFav){
                            mealsDetailsPresenter.delFavMeal(mealDto, new RealTimeListener() {
                                @Override
                                public void onSuccess() {
                                    isFav=false;
                                    imageViewAddToFavITemDetails.setImageResource(R.drawable.ic_not_fav);
                                }
                                @Override
                                public void onFailure(String message) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            mealsDetailsPresenter.addFavMeal(mealDto, new RealTimeListener() {
                                @Override
                                public void onSuccess() {
                                    isFav=true;
                                    imageViewAddToFavITemDetails.setImageResource(R.drawable.ic_fav);
                                    Toast.makeText(getActivity(), mealDto.strMeal + " " + v.getContext().getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(String message) {
                                    Log.i(TAG, "Click on Fav " + message);
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    } else {
                        showAuthSnack();
                    }
                }else {
                    Toast.makeText(requireContext(), "Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });

        addToPhoneCalender.setOnClickListener((v)->{
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.Events.TITLE, mealDto.strMeal)
                    .putExtra(CalendarContract.Events.DESCRIPTION, ConvertMealToString.getMealString(mealDto));
            startActivity(intent);
        });
    }
    private String getYoutubeId(String link) {
        if (link != null && link.split("\\?v=").length > 1)
            return link.split("\\?v=")[1];
        else return "";
    }
    private void showAuthSnack() {
        Snackbar snackbar = Snackbar.make(view, "Please Login to Start all Features", Snackbar.LENGTH_SHORT);
        snackbar.setAction("login now", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AuthFirebaseActivity.class);
                startActivity(intent);
            }
        });
        snackbar.show();
    }

    private String formatText(String strInstructions) {
        strInstructions = strInstructions.replace(". ", ".\n\n");
        return strInstructions;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        yt.release();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        MealDto mealDto;
        MealDetailsPresenter mealsDetailsPresenter;
        MealDetailsFragment mealDetailsFragment;

        public DatePickerFragment(MealDto mealDto, MealDetailsPresenter mealsDetailsPresenter, MealDetailsFragment mealDetailsFragment) {
            this.mealDto = mealDto;
            this.mealsDetailsPresenter = mealsDetailsPresenter;
            this.mealDetailsFragment = mealDetailsFragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            c.add(Calendar.DAY_OF_MONTH, 7);
            long maxDate = c.getTimeInMillis();
            datePickerDialog.getDatePicker().setMaxDate(maxDate);
            return datePickerDialog;
        }


        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.i(TAG, "onDateSet: "+year +" "+" "+month+" "+day);
            PlanDto planDto = ConvertMealToPlan.getMealPlannerFromMealAndDate(mealDto, DateFormatter.getString(year, month, day), 0);
            mealsDetailsPresenter.addToWeekPlanner(planDto, new RealTimeListener() {
                @Override
                public void onSuccess() {
                    mealDetailsFragment.imageViewADDToWeekPlanner.setImageResource(R.drawable.ic_calendar_added);
                    Toast.makeText(view.getContext(), R.string.added_successfully_to_your_plan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });
        }
    }
}
