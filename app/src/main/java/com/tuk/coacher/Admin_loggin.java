package com.tuk.coacher;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Admin_loggin extends Base implements View.OnClickListener {
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
        setContentView(R.layout.activity_admin_loggin);

        auth = FirebaseAuth.getInstance();
        state = firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(Admin_loggin.this, Admin_home.class));
                finish();
            }
        };
        email = findViewById(R.id.login_email_txt);
        password = findViewById(R.id.login_password_txt);
        login = findViewById(R.id.login_btn11);

        login.setOnClickListener(this);

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
                                Log.d(TAG, "Admin_loggin :: onComplete :: Success");
                            }else{
                                Toast.makeText(Admin_loggin.this, "An error occurred",
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Admin_loggin :: onComplete :: error");
                            }
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.d(TAG,
                                    "Admin_loggin  :: addOnSuccessListener " + authResult.getUser().getEmail());
                            startActivity(new Intent(Admin_loggin.this, Admin_home.class));
                            finish();
                        }
                    });
        }
    }
}