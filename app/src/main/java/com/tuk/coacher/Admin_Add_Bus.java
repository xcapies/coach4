package com.tuk.coacher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tuk.coacher.helper.Bus;

import androidx.annotation.NonNull;

public class Admin_Add_Bus extends Base implements View.OnClickListener {
    private MaterialButton add_bus, back;
    private TextInputEditText numplate, capacity;
    private FirebaseAuth.AuthStateListener state;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    private String UUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__add__bus);

        add_bus = findViewById(R.id.add_bus_btn);
        back = findViewById(R.id.back_btn);
        numplate = findViewById(R.id.numplate_te);
        capacity = findViewById(R.id.bus_capacity_te);

        add_bus.setOnClickListener(this);
        back.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View view) {
        if(view== back){
            startActivity(new Intent(Admin_Add_Bus.this, Admin_home.class));
        }
        if (view == add_bus) {
            String number = numplate.getText().toString();
            if (number.equals("")) {
                numplate.setError("Number Plate Required", getDrawable(R.drawable.error_24));
                return;
            }
            String capacity_ = capacity.getText().toString();
            if (capacity_.equals("")) {
                capacity.setError("Capacity Required", getDrawable(R.drawable.error_24));
                return;
            }
            Log.d(TAG, "Admin_Add_Bus :: onClick  add_bus");
            FirebaseFirestore.getInstance()
                    .collection("Buses")
                    .add(new Bus(capacity_, number))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(Booking.this, "Success :: " + documentReference.getId().trim(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Admin_Add_Bus :: onSuccess : " + documentReference.getId().trim());
                            hideProgressBar();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Toast.makeText(Booking.this, "Successful Task", Toast.LENGTH_LONG).show();
                            if (task.isSuccessful()) {
                                hideProgressBar();
                                Log.d(TAG, "Admin_Add_Bus :: onComplete : UUID " + UUID);
                                startActivity(new Intent(Admin_Add_Bus.this, Admin_Add_Bus.class));
                                finish();
                            } else {
                                hideProgressBar();
                                Log.d(TAG, "Admin_Add_Bus :: onComplete : failed to save");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Admin_Add_Bus :: onFailure :" + e.toString());
                            hideProgressBar();
                        }
                    });

        }
    }
}