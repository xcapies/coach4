package com.tuk.coacher;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

public class Login extends Base implements View.OnClickListener{
    private MaterialButton login, reset_pass, create_acc, admin_btn;
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
        setContentView(R.layout.activity_login);
        setProgressBar(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        state = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(Login.this, Home.class));
                finish();
            }
        };
        login = findViewById(R.id.login_btn11);
        create_acc = findViewById(R.id.lgn_create_acc);
        admin_btn = findViewById(R.id.admin_login);

        login.setOnClickListener(this);
        create_acc.setOnClickListener(this);
        admin_btn.setOnClickListener(this);

        email = findViewById(R.id.login_email_txt);
        password = findViewById(R.id.login_password_txt);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            String email_txt = email.getText().toString().trim();
            if(email_txt.equals("")){
                email.setError("Email required", getDrawable(R.drawable.error_24));
                return;
            }
            String password_txt = password.getText().toString().trim();
            if(password_txt.equals("")){
                password.setError("Password required", getDrawable(R.drawable.error_24));
                return;
            }
            auth.signInWithEmailAndPassword(email_txt, password_txt)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "Login :: onComplete :: Success");
                            }else{
                                Toast.makeText(Login.this, "An error occurred",
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Login :: onComplete :: error");
                            }
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.d(TAG,
                                    "Login  :: addOnSuccessListener " + authResult.getUser().getEmail());
                            startActivity(new Intent(Login.this, Home.class));
                            finish();
                        }
                    });
        }

        if(view == create_acc){
            Log.d(TAG, "LOGIN :: create acc");
            startActivity(new Intent(Login.this, SignUp.class));
        }

        if(view == admin_btn){
            Log.d(TAG, "Admin :: login");
            startActivity(new Intent(Login.this, Admin_loggin.class));
        }
    }
}