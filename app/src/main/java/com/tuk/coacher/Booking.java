package com.tuk.coacher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tuk.coacher.helper.CostAndDistance;
import com.tuk.coacher.helper.Tickets;
import com.tuk.coacher.helper.Timer;
import com.tuk.coacher.helper.UserData;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class Booking extends Base implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "TAG";
    private Button confirm, cancel, back, dropOff;
    private String trips, origin, destination, number_of_travellers, travel_time, travel_date,
            ticket_purchase_date, total_cost, distance, individual_cost, avg_arrival_time,
            avg_arrival_date, UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Booking :: onCreate");
        setContentView(R.layout.activity_booking);
        setProgressBar(R.id.book_progressBar);
        UUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        origin = UserData.getOrigin();
        destination = UserData.getDestination();
        number_of_travellers = UserData.getNumber_of_travellers();
        travel_time = UserData.getTravel_time();
        travel_date = UserData.getTravel_date();
        trips = UserData.getTrips();
        int travellers = Integer.decode(number_of_travellers);
        CostAndDistance cd = new CostAndDistance(origin, destination, travellers);
        Timer est = new Timer(travel_date, travel_time, cd.getDistance());
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        ticket_purchase_date = String.valueOf(new Date().getTime());
        total_cost = String.valueOf(cd.getTotalCost()) + "Ksh";
        individual_cost = String.valueOf(cd.getIndividualCost()) + "Ksh";
        distance = String.valueOf(cd.getDistance()) + " KMs";
        avg_arrival_time = est.getArrivalTime();
        avg_arrival_date = est.getArrivalDate();

        TextView tv_origin = findViewById(R.id.tv_origin);
        TextView tv_destination = findViewById(R.id.tv_destination);
        TextView tv_numberOfTravellers = findViewById(R.id.tv_numOfTravellers);
        TextView tv_indidual_cost = findViewById(R.id.tv_individual_cost);
        TextView tv_total_cost = findViewById(R.id.tv_total_cost);
        TextView tv_distance = findViewById(R.id.tv_distance);
        TextView tv_dateOfTravel = findViewById(R.id.tv_travel_date);
        TextView tv_timeOfTravel = findViewById(R.id.tv_time_to_depart);
        TextView tv_timeOfArrival = findViewById(R.id.tv_appx_arr_time);
        TextView tv_dateOfArrival = findViewById(R.id.tv_appx_arr_date);
        TextView tv_trips = findViewById(R.id.tv_trips);

        Toolbar toolbar = findViewById(R.id.toolbar_book);
        setSupportActionBar(toolbar);
        DrawerLayout d_layout = findViewById(R.id.booking_drawable);
        NavigationView nav_view = findViewById(R.id.nav_booking_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, d_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        d_layout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.bringToFront();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Booking.this, Home.class));
                        break;
                    case R.id.nav_map:
                        startActivity(new Intent(Booking.this, Maps.class));
                        break;
                    case R.id.nav_history:
                        startActivity(new Intent(Booking.this, History.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(Booking.this, Profile.class));
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signOut();
                        startActivity(new Intent(Booking.this, Login.class));
                        finish();
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });

        tv_origin.setText(origin);
        tv_destination.setText(destination);
        tv_distance.setText(distance);
        tv_numberOfTravellers.setText(number_of_travellers);
        tv_indidual_cost.setText(individual_cost);
        tv_total_cost.setText(total_cost);
        tv_dateOfTravel.setText(travel_date);
        tv_timeOfTravel.setText(travel_time);
        tv_timeOfArrival.setText(avg_arrival_time);
        tv_dateOfArrival.setText(avg_arrival_date);
        tv_trips.setText(trips);


        confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(this);
        cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "Booking :: onClick");
        if (view == confirm) {
            //TODO : use a pop up payment method system
            showProgressBar();
            Tickets.setTick(
                    new Tickets(
                            destination.trim(),
                            origin.trim(),
                            number_of_travellers.trim(),
                            trips.trim(),
                            ticket_purchase_date.trim(),
                            avg_arrival_date.trim(),
                            avg_arrival_time.trim(),
                            individual_cost.trim(),
                            total_cost.trim(),
                            travel_date.trim(),
                            travel_time.trim(),
                            distance.trim(),
                            UUID.trim(),
                            true
                    )
            );
            Log.d(TAG, "Home :: onClick  confirm ");
            startActivity(new Intent(this, Payments.class));

        }
        if (view == cancel) {
            startActivity(new Intent(Booking.this, Home.class));
            finish();
        }
        if (view == back) {
            startActivity(new Intent(Booking.this, Home.class));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    private boolean paymentConfirmed() {
        //fake payments for now
        return true;
    }
}
