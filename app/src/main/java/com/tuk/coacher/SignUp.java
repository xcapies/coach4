package com.tuk.coacher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class SignUp extends Base implements View.OnClickListener {
    public static final String TAG = "TAG";
    private TextInputEditText fname, lname, password, email, password2, phone;
    private MaterialButton save, clear, back;

    private FirebaseAuth.AuthStateListener state;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    private String UUID;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setProgressBar(R.id.signup_progressBar);

        fname = findViewById(R.id.in_prof_fName);
        lname = findViewById(R.id.in_prof_lName);
        email = findViewById(R.id.in_prof_email);
        password2 = findViewById(R.id.in_prof_password2);
        password = findViewById(R.id.in_prof_password);
        phone = findViewById(R.id.in_prof_phone);

        save = findViewById(R.id.in_prof_ok_btn);
        clear = findViewById(R.id.in_prof_clear_btn);
        back = findViewById(R.id.in_prof_back_btn);

        clear.setOnClickListener(this);
        save.setOnClickListener(this);
        back.setOnClickListener(this);
//        auth.addAuthStateListener(state);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
//        UUID = auth.getCurrentUser().getUid();


    }

    @Override
    public void onClick(View view) {
        if (view == save) {
            String fname_txt = fname.getText().toString();
            if (fname_txt.equals("")) {
                fname.setError("First Name Required", getDrawable(R.drawable.error_24));
                return;
            }
            String lname_txt = lname.getText().toString();
            if (lname_txt.equals("")) {
                lname.setError("Last Name Required", getDrawable(R.drawable.error_24));
                return;
            }

            String phone_txt = phone.getText().toString();
            if (phone_txt.equals("")) {
                phone.setError("Phone number Required", getDrawable(R.drawable.error_24));
                return;
            }
            String password_txt = password.getText().toString();
            if (password_txt.equals("")) {
                password.setError("Password is Required", getDrawable(R.drawable.error_24));
                return;
            } else if (password_txt.length() < 5) {
                password.setError("Password too short", getDrawable(R.drawable.error_24));
            }
            String password2_txt = password2.getText().toString();
            if (password2_txt.equals("")) {
                password2.setError("Password is Required", getDrawable(R.drawable.error_24));
                return;
            }
            String email_txt = email.getText().toString();
            if (email_txt.equals("")) {
                email.setError("Email is Required", getDrawable(R.drawable.error_24));
                return;
            }
            if (!password_txt.equals(password2_txt)) {
                password2.setError("Match both Passwords", getDrawable(R.drawable.error_24));
                return;
            }
            showProgressBar();
            auth.createUserWithEmailAndPassword(email_txt, password_txt)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "SignUp :: onComplete :: Success");
                            } else {
                                Toast.makeText(SignUp.this, "An error occurred",
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "SignUp :: onComplete :: error");
                            }
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.d(TAG, "SignUp :: onSuccess :: Success");
                            UUID = authResult.getUser().getUid();
                            DocumentReference docRef = firebaseFirestore.collection("Users").document(UUID);
                            Map<String, Object> prof = new HashMap<>();
                            prof.put("F_Name", fname_txt);
                            prof.put("L_Name", lname_txt);
                            prof.put("Email", email_txt);
                            prof.put("Password", password_txt);
                            prof.put("Phone", phone_txt);
                            docRef.set(prof)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocRef onSuccess : " + UUID);
                                        }
                                    })
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignUp.this, "Profile Updated",
                                                        Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, "DocRef onComplete : " + UUID);
                                                startActivity(new Intent(SignUp.this, Home.class));
                                                finish();
                                            }else{
                                                Log.d(TAG, "DocRef onComplete : failed" + UUID);
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "DocRef onFailure :" + e.toString());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure :" + e.toString());
                        }
                    });
            hideProgressBar();

        }
        if (view == back) {
            startActivity(new Intent(SignUp.this, Login.class));
        }
        if (view == clear) {
            email.setText("");
            phone.setText("");
            password.setText("");
            password2.setText("");
            lname.setText("");
            fname.setText("");
        }
    }
}