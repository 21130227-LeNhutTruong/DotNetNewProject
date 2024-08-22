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

public class VerifyCodeActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4;

    Button btnVerify;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veryfi_screen); // Liên kết với file XML
        EditText et1 = findViewById(R.id.etCode1);
        EditText et2 = findViewById(R.id.etCode2);
        EditText et3 = findViewById(R.id.etCode3);
        EditText et4 = findViewById(R.id.etCode4);
        btnVerify = findViewById(R.id.btnConfirm);
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
        String correctCode = getIntent().getStringExtra("verificationCode") + "";
        User user =(User) getIntent().getSerializableExtra("user");
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();

                if (enteredCode.equals(correctCode)) {
                    // Mã xác minh đúng, chuyển đến trang đăng nhập
                    registerOnClick(user);
                    Intent intent = new Intent(VerifyCodeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VerifyCodeActivity.this, "Incorrect code. Please try again.", Toast.LENGTH_SHORT).show();
                }
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