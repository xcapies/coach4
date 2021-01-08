package com.tuk.coacher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tuk.coacher.cash.MCoacher;
import com.tuk.coacher.helper.Tickets;
import com.tuk.coacher.mpesa.Mode;
import com.tuk.coacher.mpesa.Mpesa;
import com.tuk.coacher.mpesa.interfaces.AuthListener;
import com.tuk.coacher.mpesa.interfaces.MpesaListener;
import com.tuk.coacher.mpesa.models.STKPush;
import com.tuk.coacher.mpesa.utils.Config;
import com.tuk.coacher.mpesa.utils.Pair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


public class Payments extends Base implements View.OnClickListener, AuthListener, MpesaListener {
    private String access_token, UUID;
    private MCoacher mCoacher;
    private MaterialButton ok, cancel;
    private boolean payed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
//        Config con  = new Config();
//        Log.d(TAG, "Payments ::" +con.getResponse().toString());
        Mpesa.with(this, Config.CONSUMER_SECRET, Config.CONSUMER_KEY);

        UUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.payments_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout d_layout = findViewById(R.id.payments_drawable);
        NavigationView nav_view = findViewById(R.id.nav_payments_view);
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
                        startActivity(new Intent(Payments.this, Home.class));
                        break;
                    case R.id.nav_map:
                        startActivity(new Intent(Payments.this, Maps.class));
                        break;
                    case R.id.nav_history:
                        startActivity(new Intent(Payments.this, History.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(Payments.this, Profile.class));
                        break;
                    case R.id.nav_logout:
                        auth.signOut();
                        startActivity(new Intent(Payments.this, Login.class));
                        finish();
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });

        TextInputEditText amount = findViewById(R.id.payment_amount_te);
        ok = findViewById(R.id.payment_ok_btn);
        ok.setOnClickListener(this);
        cancel = findViewById(R.id.payment_cancel_btn);
        cancel.setOnClickListener(this);
        Tickets tick = Tickets.getTick();
        amount.setText(tick.getTotal_cost());
        if (payed) {


        }
    }

    @Override
    public void onClick(View view) {
        if (view == ok) {
            STKPush.Builder builder = new STKPush.Builder(Config.getBusinessShortCode(), Config.getConsumerKey(),
                    1,
                    Config.getBusinessShortCode(), "0713480632");
            STKPush push = builder.build();
            Mpesa.getInstance().pay(this, push);
        } else if (view == cancel) {
            Log.d(TAG, "Payments :: onClick : cancel ");
            startActivity(new Intent(Payments.this, Booking.class));
            finish();
        }
    }


    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.d(TAG, "Payments :: onAuthError " + result.code + " " + result.message);
    }

    @Override
    public void onAuthSuccess() {
        Log.d(TAG, "Payments :: onAuthSuccess ");
    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {
        Log.d(TAG, "Payments :: onMpesaError " + result.code + " " + result.message);
    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        Log.d(TAG, "Payments :: onMpesaSuccess " + MerchantRequestID + " " + CustomerMessage);
        FirebaseFirestore.getInstance()
                .collection("Bookings")
                .add(
                        Tickets.getTick()
                )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(Booking.this, "Success :: " + documentReference.getId().trim(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Booking :: onSuccess : " + documentReference.getId().trim());
                        hideProgressBar();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(Booking.this, "Successful Task", Toast.LENGTH_LONG).show();
                        if (task.isSuccessful()) {
                            hideProgressBar();
                            Log.d(TAG, "Payments :: onComplete : UUID " + UUID);
                            startActivity(new Intent(Payments.this, History.class));
                            finish();
                        } else {
                            hideProgressBar();
                            Log.d(TAG, "Booking :: onComplete : failed to save");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Booking :: onFailure :" + e.toString());
                        hideProgressBar();
                    }
                });
    }
}
