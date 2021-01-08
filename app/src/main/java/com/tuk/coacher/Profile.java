package com.tuk.coacher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.tuk.coacher.helper.UserProfile;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class Profile extends Base implements View.OnClickListener {

    public static final String TAG = "TAG";
    private MaterialButton change_pass, edit_prof, upload_pic, delete_acc;
    private MaterialTextView full_name, email, phone, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        full_name = findViewById(R.id.prof_full_name);
        email = findViewById(R.id.prof_email);
        phone = findViewById(R.id.prof_phone);
        username = findViewById(R.id.prof_username);

        change_pass = findViewById(R.id.prof_change_pass_btn);
        edit_prof = findViewById(R.id.prof_edit_prof_btn);
        upload_pic = findViewById(R.id.prof_upload_img_btn);
        delete_acc = findViewById(R.id.prof_delete_acc_btn);
        delete_acc.setOnClickListener(this);
        change_pass.setOnClickListener(this);
        edit_prof.setOnClickListener(this);
        upload_pic.setOnClickListener(this);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout d_layout = findViewById(R.id.profile_drawable);
        NavigationView nav_view = findViewById(R.id.nav_profile_view);
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
                        startActivity(new Intent(Profile.this, Home.class));
                        break;
                    case R.id.nav_map:
                        startActivity(new Intent(Profile.this, Maps.class));
                        break;
                    case R.id.nav_history:
                        startActivity(new Intent(Profile.this, History.class));
                        break;
                    case R.id.nav_profile:
                        startActivity(new Intent(Profile.this, Profile.class));
                        break;
                    case R.id.nav_logout:
                        auth.signOut();
                        startActivity(new Intent(Profile.this, Login.class));
                        finish();
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });


        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        String UUID = auth.getCurrentUser().getUid();
        HashMap<String, Object> bookings = new HashMap<>();
        DocumentReference docRef = firebaseFirestore.collection("Users").document(UUID);
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                UserProfile p  = documentSnapshot.toObject(UserProfile.class);
//            }
//        });
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String f_name =
                        documentSnapshot.get("F_Name").toString() +"  "+ documentSnapshot.get(
                        "L_Name").toString();
                Log.d(TAG, "Profile :: onEvent : " + f_name);
                if(f_name.trim().replace(" ","").equals("")){
                    if(full_name.getText().toString().trim().equals("")) {
                        startActivity(new Intent(Profile.this, SignUp.class));
                        finish();
                    }
                }else{
                    full_name.setText(f_name);
                    email.setText(documentSnapshot.get("Email").toString());
//                    username.setText(documentSnapshot.get("Username").toString());
                    phone.setText(documentSnapshot.get("Phone").toString());
                }

            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view == change_pass) {
        }
        if (view == edit_prof) {
        }
        if (view == upload_pic) {
        }
        if(view == delete_acc){}
    }
}
