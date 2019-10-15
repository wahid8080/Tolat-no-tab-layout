package com.example.wahid.tolate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private EditText mEmail, mPassword, mRePassWord;
    private RadioGroup radioGroup;
    public RadioButton radioButton,owner,render;
    private ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mEmail = findViewById(R.id.emailForRegistration);
        mPassword = findViewById(R.id.passwordForRegistration);
        mRePassWord = findViewById(R.id.re_passwordForRegistration);
        radioGroup = findViewById(R.id.radioGroupId);
        progressDialog2 = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        owner = findViewById(R.id.ownerId);
        render = findViewById(R.id.rendererId);
    }


    public void singupButton(View view) {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString();
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        if (validate() == true) {
            progressDialog2.setMessage("Try to Sing in");
            progressDialog2.setTitle("Processing...");
            progressDialog2.show();
            singin(email, password);

        } else {
            validate();
        }

    }

    public boolean validate() {
        boolean valid = true;
        String email = mEmail.getText().toString().trim();
        String pass = mPassword.getText().toString();
        String rePass = mRePassWord.getText().toString();

        if (email.isEmpty() | !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            return false;
        } else {
            mEmail.setError(null);
        }

        if (pass.length() <= 5) {
            mPassword.setError("password too short");
            return false;
        } else if (pass.isEmpty()) {
            mPassword.setError("Password is empty");
            return false;
        } else if (!pass.equals(rePass)) {
            mRePassWord.setError("Password don't match");
            return false;
        } else {
            mRePassWord.setError(null);
        }
        return valid;
    }

    private void singin(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            if (radioButton.equals(owner) && validate() == true) {
                                Intent intent = new Intent(Registration.this, UploadClintInfo.class);
                                intent.putExtra("user", "owner");
                                progressDialog2.dismiss();
                                Toast.makeText(Registration.this, "Sing in successfully.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            } else if (radioButton.equals(render) && validate() == true) {
                                Intent intent = new Intent(Registration.this, UploadRenderInfo.class);
                                intent.putExtra("user", "renter");
                                progressDialog2.dismiss();
                                Toast.makeText(Registration.this, "Sing in successfully.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }else {
                                Toast.makeText(Registration.this,"Problem",Toast.LENGTH_SHORT).show();
                                progressDialog2.cancel();
                            }

                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registration.this, "Sing in failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog2.cancel();
                        }

                        // ...
                    }
                });
    }
}
