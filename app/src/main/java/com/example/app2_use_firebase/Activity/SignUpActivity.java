package com.example.app2_use_firebase.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app2_use_firebase.R;
import com.example.app2_use_firebase.model.User;
import com.example.app2_use_firebase.web_service.SoapClient;

import java.util.Random;


public class SignUpActivity extends BaseActivity  {
    EditText edtUsername, edtEmail, edtPassword, edtRepassword;
    String username, email, password, repassword;
    Button register;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        initListener();
    }

    public void initUI() {
        edtUsername = findViewById(R.id.edt_Username);
        edtEmail = findViewById(R.id.edt_Email);
        edtPassword = findViewById(R.id.edt_Password);
        edtRepassword = findViewById(R.id.edt_Repassword);

        register = findViewById(R.id.btn_Register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
    }

    private void initListener() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = String.valueOf(edtUsername.getText());
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());
                repassword = String.valueOf(edtRepassword.getText());

                // Kiểm tra thông tin đăng nhập
                if (TextUtils.isEmpty(username)) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(repassword)) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Enter repassword", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.equals(password, repassword)) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Repassword invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                Random random = new Random();
                int code = random.nextInt(9000) + 1000;
                boolean isSend = SoapClient.getInstance().sendMail(email, "Your verification code", "Your verification code is: " + code);
                if(isSend) {
                    Intent intent = new Intent(SignUpActivity.this, VerifyCodeActivity.class);
                    User user = new User("", email, password, username, username, 18, "", 1, 0);
                    intent.putExtra("email", email);
                    intent.putExtra("verificationCode", code);
                    intent.putExtra("user", user);
                    startActivity(intent);progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Can`t send mail", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // đăng nhập
    private void registerOnClick() {
        progressDialog.show();
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();

//        username = String.valueOf(edtUsername.getText());
//        email = String.valueOf(edtEmail.getText());
//        password = String.valueOf(edtPassword.getText());
//        repassword = String.valueOf(edtRepassword.getText());
//
//        // Kiểm tra thông tin đăng nhập
//        if (TextUtils.isEmpty(username)) {
//            progressDialog.dismiss();
//            Toast.makeText(SignUpActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(email)) {
//            progressDialog.dismiss();
//            Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(password)) {
//            progressDialog.dismiss();
//            Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (password.length() < 6) {
//            progressDialog.dismiss();
//            Toast.makeText(SignUpActivity.this, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
//        }
//        if (TextUtils.isEmpty(repassword)) {
//            progressDialog.dismiss();
//            Toast.makeText(SignUpActivity.this, "Enter repassword", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!TextUtils.equals(password, repassword)) {
//            progressDialog.dismiss();
//            Toast.makeText(SignUpActivity.this, "Repassword invalid", Toast.LENGTH_SHORT).show();
//            return;
//        }



        // Đăng ký
        //
//        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                progressDialog.dismiss();
//                if (task.isSuccessful()) {
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    // Update user profile with username
//                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
//                    user.updateProfile(profileUpdates);
//
//                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finishAffinity();
//                } else {
//                    try {
//                        Exception e = task.getException();
//                        if (e != null) {
//                            // Display a more specific error message based on the exception type
//                            String message = e.getMessage();
//                            if (e instanceof FirebaseAuthUserCollisionException) {
//                                message = "Email already in use.";
//                            } else if (e instanceof FirebaseAuthWeakPasswordException) {
//                                message = "Password is too weak.";
//                            } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                                message = "Invalid email or password.";
//                            } else {
//                                message = "Authentication failed.";  // Generic message for other exceptions
//                            }
//                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e) {
//                        // Handle unexpected exceptions during error retrieval
//                        Toast.makeText(SignUpActivity.this, "An unexpected error occurred.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = new User("", email, password, username, username, 18, "", 1, 0);
                    boolean result = SoapClient.getInstance().register(user);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (result) {
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }else {
                                Toast.makeText(SignUpActivity.this, "Email already in use.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }catch (Exception e) {
                    Log.e("SignUpActivity", "Error during SOAP call", e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();



    }


}