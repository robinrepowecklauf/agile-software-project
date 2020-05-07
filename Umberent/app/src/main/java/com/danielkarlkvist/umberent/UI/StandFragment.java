package com.danielkarlkvist.umberent.UI;


import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;

import com.danielkarlkvist.umberent.Model.Rental;
import com.danielkarlkvist.umberent.Model.Umberent;
import com.danielkarlkvist.umberent.Model.Umbrella;
import com.danielkarlkvist.umberent.R;

import java.time.LocalDate;
import java.util.Objects;

public class StandFragment extends Fragment {


    private Button rent_button;
    private Button end_rent_button;
    private Chronometer rentalTimeElapsedChronometer;
    private boolean running = true;
    private Umberent umberent = Umberent.getInstance();
    private Umbrella umbrella = new Umbrella(1, true);
    private Rental rental = new Rental(System.currentTimeMillis(), System.currentTimeMillis(),  LocalDate.now(), umberent.getProfile(), umbrella);

    //PopupWindow display method
    public void showPopupWindow(final View view) {

        //Create a View object through inflater
        final LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.stand_card, null);


        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

       //Set the animation of the window
        popupWindow.setAnimationStyle(R.style.AnimationPopUp);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        // initialize rent button
        rent_button = popupView.findViewById(R.id.rent_button);
        rent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Start a new rental

                rental.setStartTime(System.currentTimeMillis());
                System.out.println("Rental start time is: " + rental.getStartTime());

                // Starts ticking stopwatch
                startChronometer(view);

                // Remove stand window
                popupWindow.dismiss();

                // Open Rental window
                openRentalWindow(view);

            }
        });

        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });


    }

    private void openRentalWindow(final View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View rentalView = inflater.inflate(R.layout.fragment_rental, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow rentalWindow = new PopupWindow(rentalView, width, height, focusable);

        //Set the animation of the window
        rentalWindow.setAnimationStyle(R.style.AnimationPopUp);

        //Set the location of the window on the screen
        rentalWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        initializeRentalViews(rentalView);
        initializeRentalButtonListeners();

    }

    private void initializeRentalViews(View view) {
        end_rent_button = view.findViewById(R.id.end_rent_button);
        rentalTimeElapsedChronometer = view.findViewById(R.id.rentalTimeElapsedChronometer);
    }

    private void initializeRentalButtonListeners() {
        end_rent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rental.setEndTime(System.currentTimeMillis());
                calculateRentalTime(rental.getStartTime(), rental.getEndTime());
                calculatePrice(rental.getStartTime(), rental.getEndTime());
                rental.setDate(LocalDate.now());

                // reset chronometer
                resetChronometer(v);

            }
        });
    }

    private long calculateRentalTime(long startTime, long endTime) {

        long difference = endTime - startTime;
        System.out.println("Time of rental: " + difference/1000 + " seconds");
        return difference;
    }

    private long calculatePrice(long startTime, long endTime){

        long totalCost = (2*((endTime - startTime)/1000))/60;
        System.out.println("Total cost is: " + totalCost + "kr");
        return totalCost;

    }

    private void startChronometer(View view) {
        if (!running) {
             rentalTimeElapsedChronometer.start();
             running = true;
        }
    }

    private void resetChronometer(View view) {
        rentalTimeElapsedChronometer.setBase(SystemClock.elapsedRealtime());
    }
}
