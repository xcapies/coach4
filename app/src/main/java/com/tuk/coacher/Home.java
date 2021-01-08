package com.tuk.coacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tuk.coacher.helper.Locations;
import com.tuk.coacher.helper.UserData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Home extends Base implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,
        MaterialSpinner.OnItemSelectedListener {
    private static String TAG = "TAG";
    private MaterialSpinner spinner_from, spinner_to;
    private EditText et_numsOfTravs;
    private RadioGroup trips;
    private NumberPicker numberPicker;
    private RadioButton one, round;
    private MaterialButton book, map, logout, btn_date, btn_time;
    private String travel_date = "";
    private String travel_time = "";
    private MaterialTextView tv_date, tv_time;
    private String spinner_from_id = "";
    private String spinner_to_id = "";
    private MenuItem nav_home, nav_history, nav_map, nav_profile, nav_edit_profile, nav_logout;

    //authentication
    public FirebaseAuth.AuthStateListener state;
    public FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Home :: onStart");
        auth.addAuthStateListener(state);
        if (!isNetworkAvailable())
            Toast.makeText(Home.this, "Internet connection required", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Home :: onCreate");
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        state = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                startActivity(new Intent(Home.this, Login.class));
                finish();
            }
        };

        book = findViewById(R.id.btn_book);
        map = findViewById(R.id.btn_search);
        logout = findViewById(R.id.btn_logout);
        trips = findViewById(R.id.radio_group);
        one = findViewById(R.id.rbtn_single);
        round = findViewById(R.id.rbtn_round);
        spinner_from = findViewById(R.id.spinner_from);
        spinner_to = findViewById(R.id.spinner_to);
        btn_date = findViewById(R.id.btn_date);
        btn_time = findViewById(R.id.btn_time);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        nav_logout = findViewById(R.id.nav_logout);
        nav_home = findViewById(R.id.nav_home);
        nav_history = findViewById(R.id.nav_history);
        nav_map = findViewById(R.id.nav_map);
        nav_profile = findViewById(R.id.nav_profile);

        book.setOnClickListener(this);
        map.setOnClickListener(this);
        logout.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_time.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout d_layout = findViewById(R.id.home_drawable);
        NavigationView nav_view = findViewById(R.id.nav_view);
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
                        startActivity(new Intent(Home.this, Home.class));
                        break;
                    case R.id.nav_history:
                        startActivity(new Intent(Home.this, History.class));
                        break;
                    case R.id.nav_map:
                        startActivity(new Intent(Home.this, Maps.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(Home.this, Profile.class));
                        break;
                    case R.id.nav_logout:
                        auth.signOut();
                        startActivity(new Intent(Home.this, Login.class));
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        numberPicker = findViewById(R.id.et_numsOfTravs);
        numberPicker.setValue(1);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(20);
//        et_numsOfTravs.setText("1");
//        hideKeyboard(et_numsOfTravs);

        ArrayList<String> ls = new Locations().getFromLocations();
        int index_ = spinner_from.getSelectedIndex();
        if (index_ == 0) {
            spinner_from.setItems(Objects.requireNonNull(ls.toArray()));
        }
        spinner_from.setOnItemSelectedListener(this);
        int index_1 = spinner_to.getSelectedIndex();
        if (index_1 == 0) {
            spinner_to.setItems(ls);
        }
        spinner_to.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view == map) {
            startActivity(new Intent(this, Maps.class));
        }
        if (view == book) {
            if (checkInputs()) {
                UserData.setTrips(String.valueOf(getTrip()));
                UserData.setDestination(spinner_to_id);
                UserData.setOrigin(spinner_from_id);
                UserData.setTravel_date(travel_date);
                UserData.setTravel_time(travel_time);
                UserData.setNumber_of_travellers(String.valueOf(numberPicker.getValue()));
                startActivity(new Intent(this, Booking.class));
                Log.d(TAG, "Home :: book " + UserData.getDestination());
            } else {
                return;
            }
        }
        if (view == btn_date) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog date = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int y, int m, int d) {
                            travel_date = d + "/" + (m + 1) + "/" + y;
                            tv_date.setText(travel_date);
                        }
                    }, mYear, mMonth, mDay);
            date.show();
        }
        if (view == btn_time) {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog time = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            travel_time = hourOfDay + ":" + minute;
                            tv_time.setText(travel_time);
                        }
                    }, mHour, mMinute, false);
            time.show();
        }
        if (view == logout) {
            auth.signOut();
            startActivity(new Intent(Home.this, Login.class));
            finish();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        if (view == spinner_from) {
            Toast.makeText(this, "From :: " + item.toString(), Toast.LENGTH_LONG).show();
            spinner_from_id = item.toString();
        }
        if (view == spinner_to) {
//            Toast.makeText(this, "To :: " + item.toString(), Toast.LENGTH_LONG).show();
            Snackbar.make(view, "To :: " + item.toString(), Snackbar.LENGTH_LONG).show();
            spinner_to_id = item.toString();
        }
    }

    private boolean checkInputs() {
        if (spinner_to_id.equals("") || spinner_to_id.equals("Choose a location")) {
            Toast.makeText(this, "Please select your destination", Toast.LENGTH_LONG).show();
            return false;
        }
        if (spinner_from_id.equals("") || spinner_from_id.equals("Choose a location")) {
            Toast.makeText(this, "Please select your origin", Toast.LENGTH_LONG).show();
            return false;
        }
        if (travel_date.equals("")) {
            Toast.makeText(this, "Please select your travelling Date", Toast.LENGTH_LONG).show();
            return false;
        }
        if (travel_time.equals("")) {
            Toast.makeText(this, "Please select your travelling Time", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private int getTrip() {
        AtomicInteger trip = new AtomicInteger();
        int trip_ = trips.getCheckedRadioButtonId();
        if (trip_ == one.getId()) {
            trip.set(1);
        }
        if (trip_ == round.getId()) {
            trip.set(2);
        }
        return trip.get();
    }


}
