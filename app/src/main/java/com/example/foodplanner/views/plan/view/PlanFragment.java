package com.example.foodplanner.views.plan.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;
import com.example.foodplanner.helpers.DateFormatter;
import com.example.foodplanner.model.dtos.PlanDto;
import com.example.foodplanner.model.repositories.mealplanrepo.FavAndPlanRepoImplementation;
import com.example.foodplanner.network.InternetConnection;
import com.example.foodplanner.views.plan.presenter.PlanPresenterImplementation;
import com.example.foodplanner.views.plan.presenter.PlanPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PlanFragment extends Fragment implements ONItemClickListener, OnRemoveClickListener{
    private static Calendar calendar = Calendar.getInstance();
    RecyclerView recyclerView;

    PlanAdapter planAdapter;
    String date;
    CardView cardCalender;
    TextView calenderTv;
    private InternetConnection internetConnection;

    private PlanPresenter planPresenter;
    FrameLayout animationCalender;

    List<PlanDto> planDtoList;
    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        planPresenter = PlanPresenterImplementation.getInstance(FavAndPlanRepoImplementation.getInstance(requireContext()));
        internetConnection = new InternetConnection(requireContext());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationCalender = view.findViewById(R.id.frame_animation_calender);
        planAdapter = new PlanAdapter(new ArrayList<>(), this, this );
        cardCalender = view.findViewById(R.id.card_date_calendar);
        calenderTv = view.findViewById(R.id.tv_date_calender);
        date = DateFormatter.getString(calendar.getTime());
        calenderTv.setText(date);
        setViews(date);
        recyclerView = view.findViewById(R.id.mealWeekPlan_recyclerView_weekplan);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(planAdapter);
        cardCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new PlanFragment.DatePickerFragment(PlanFragment.this);
                newFragment.show(requireActivity().getSupportFragmentManager(), "datePicker");
            }
        });
    }

    private void getPlan(String dateString) {
        planPresenter.getMealPlanByDate(dateString).observe(getViewLifecycleOwner(), new Observer<List<PlanDto>>() {
            @Override
            public void onChanged(List<PlanDto> planDtos) {
                planDtoList = planDtos;
                if (planDtos.size()>0)
                    animationCalender.setVisibility(View.GONE);
                planAdapter.setMealPlanners(planDtos);
            }
        });
    }

    private void setViews(String date) {
        calenderTv.setText(date);
        animationCalender.setVisibility(View.VISIBLE);
        getPlan(date);
    }
    @Override
    public void onClick(PlanDto planDto) {
        PlanFragmentDirections.ActionPlanFragmentToMealDetailsFragment action = PlanFragmentDirections.actionPlanFragmentToMealDetailsFragment(planDto.idMeal);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onRemove(PlanDto planDto, int position) {
        if(internetConnection.isConnectedMobile() || internetConnection.isConnectedWifi()){
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.Delete_Meal)
                    .setMessage(R.string.Are_you_sure_you_want_to_delete_this_meal)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        planPresenter.removeItemPlan(planDto);
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            if (planDtoList.size()<1)
                animationCalender.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(requireContext(), "Please Check Your Internet Connection And Try Again", Toast.LENGTH_LONG).show();
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        PlanFragment planFragment;
        public DatePickerFragment(PlanFragment planFragment)
        {
            this.planFragment =planFragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
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
           planFragment.setViews(DateFormatter.getString(year, month, day));
        }
    }
}