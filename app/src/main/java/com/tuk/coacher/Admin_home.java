package com.tuk.coacher;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tuk.coacher.helper.UserData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

public class Admin_home extends Base implements View.OnClickListener {
    private MaterialButton add_buss, view_bus, add_route, view_route, view_tickets;
    private TextInputEditText email, password;
    public static final String TAG = "TAG";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener state;
    private FirebaseUser user;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(state);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        auth = FirebaseAuth.getInstance();

        state = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() == null) {
                startActivity(new Intent(Admin_home.this, Admin_loggin.class));
                finish();
            }
        };

        add_buss = findViewById(R.id.btn_add_bus);
        add_buss.setOnClickListener(this);
        view_bus = findViewById(R.id.btn_view_bus);
        view_bus.setOnClickListener(this);
        add_route = findViewById(R.id.btn_add_route);
        add_route.setOnClickListener(this);
        view_route = findViewById(R.id.btn_view_route);
        view_route.setOnClickListener(this);
        view_tickets = findViewById(R.id.btn_view_tickets);
        view_tickets.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == add_buss){
            Log.d(TAG, "Admin_home :: Admin_Add_Bus ");
            startActivity(new Intent(Admin_home.this, Admin_Add_Bus.class));
        }
        if(view == view_bus){
            Log.d(TAG, "Admin_home :: Admin_View_Bus ");
            startActivity(new Intent(Admin_home.this, Admin_View_Bus.class));
        }
        if(view == add_route){
            Log.d(TAG, "Admin_home :: Admin_Add_Route ");
            startActivity(new Intent(Admin_home.this, Admin_Add_Route.class));
        }
        if(view == view_route){
            Log.d(TAG, "Admin_home :: Admin_View_Route ");
            startActivity(new Intent(Admin_home.this, Admin_View_Route.class));
        }
        if(view == view_tickets){
            Log.d(TAG, "Admin_home :: Admin_View_Tickets");
            startActivity(new Intent(Admin_home.this, Admin_View_Tickets.class));
        }

    }
}