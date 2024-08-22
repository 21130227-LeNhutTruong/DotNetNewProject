package com.example.app2_use_firebase.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app2_use_firebase.R;
import com.example.app2_use_firebase.model.User;
import com.example.app2_use_firebase.web_service.SoapClient;

import java.util.Random;

public class VerifyCodeActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4;

    Button btnVerify;
    Button sendCode;
    ProgressDialog progressDialog;
    private int correctCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veryfi_screen); // Liên kết với file XML
        EditText et1 = findViewById(R.id.etCode1);
        EditText et2 = findViewById(R.id.etCode2);
        EditText et3 = findViewById(R.id.etCode3);
        EditText et4 = findViewById(R.id.etCode4);
        btnVerify = findViewById(R.id.btnConfirm);
        sendCode = findViewById(R.id.btnResendCode);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");
        et1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    et2.requestFocus(); // Chuyển sang ô tiếp theo
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    et3.requestFocus(); // Chuyển sang ô tiếp theo
                } else if (s.length() == 0) {
                    et1.requestFocus(); // Quay lại ô trước nếu xóa
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    et4.requestFocus(); // Chuyển sang ô cuối cùng
                } else if (s.length() == 0) {
                    et2.requestFocus(); // Quay lại ô trước nếu xóa
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    et3.requestFocus(); // Quay lại ô trước nếu xóa
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        String email = getIntent().getStringExtra("email");
        correctCode = getIntent().getIntExtra("verificationCode", -1);

        User user =(User) getIntent().getSerializableExtra("user");
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();
                int enteredCodeInt = Integer.parseInt(enteredCode);
                if (enteredCodeInt == correctCode) {
                    // Mã xác minh đúng, chuyển đến trang đăng nhập
                    registerOnClick(user);
                    Intent intent = new Intent(VerifyCodeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Log.d("VERIFY", "CODE: "+enteredCode+", : "+correctCode);
                } else {
                    Toast.makeText(VerifyCodeActivity.this, "Incorrect code. Please try again.", Toast.LENGTH_SHORT).show();
                    Log.d("VERIFY", "ERROR: "+enteredCode+", : "+correctCode);
                }
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int code = random.nextInt(9000) + 1000;
                correctCode = code;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boolean isSend = SoapClient.getInstance().sendMail(email, "Your verification code", "Your verification code is: " + code);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(isSend) {
                                        Toast.makeText(VerifyCodeActivity.this, "Send Code Success", Toast.LENGTH_SHORT).show();
                                        Log.d("SOAP", "SEND MAIL: "+isSend);
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(VerifyCodeActivity.this, "Can`t send mail", Toast.LENGTH_SHORT).show();
                                        Log.d("SOAP", "SEND MAIL: "+isSend);
                                    }
                                }
                            });

                        }catch (Exception e) {
                            Log.d("SOAP ERROR", "ERROR CONNECTION", e);
                        }
                    }
                }).start();
            }
        });
    }
    private void registerOnClick(User user) {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean result = SoapClient.getInstance().register(user);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (result) {
                                Intent intent = new Intent(VerifyCodeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }else {
                                Toast.makeText(VerifyCodeActivity.this, "Email already in use.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }catch (Exception e) {
                    Log.e("SignUpActivity", "Error during SOAP call", e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(VerifyCodeActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }
}