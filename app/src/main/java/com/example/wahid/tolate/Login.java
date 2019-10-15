package com.example.wahid.tolate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wahid.tolate.ModelClass.Status;
import com.example.wahid.tolate.ModelClass.UploadClintInfoModel;
import com.example.wahid.tolate.ModelClass.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private RelativeLayout layout1;
    private ImageView mCoverImage;
    private TextView textView;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog1;



    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            layout1.setVisibility(View.VISIBLE);
            mCoverImage.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.emailForLogin);
        mPassword = findViewById(R.id.passwordForLogin);
        mCoverImage = findViewById(R.id.imageViewId);
        mAuth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.footer);
        layout1 = findViewById(R.id.rellay1);
        //layout2 = findViewById(R.id.relative2Id);
        progressDialog1 = new ProgressDialog(this);
        handler.postDelayed(runnable, 3000);



    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser!=null)
        {
           startActivity(new Intent(Login.this,Empty.class));
           progressDialog1.dismiss();
           finish();

        }
    }

    public void loginButton(View view) {
        String email = mEmail.getText().toString().trim();
        String pass = mPassword.getText().toString();

        if (validate() == true) {
            progressDialog1.setMessage("Try to Login");
            progressDialog1.setTitle("Processing...");
            progressDialog1.show();
            login(email, pass);
        } else {
            validate();
        }
    }


    public boolean validate() {
        boolean valid = true;
        String email = mEmail.getText().toString().trim();
        String pass = mPassword.getText().toString();

        if (email.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            return false;
        } else {
            mEmail.setError(null);
        }

        if (pass.isEmpty()) {
            mPassword.setError("enter your correct password");
            return false;
        } else {
            mPassword.setError(null);
        }
        return valid;
    }

    private void login(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(Login.this,Empty.class));
                            progressDialog1.dismiss();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Check your email or password",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog1.cancel();
                        }

                        // ...
                    }
                });
    }


    public void goToSingin(View view) {
        startActivity(new Intent(this,Registration.class));
        finish();
    }
}
