package com.example.app2_use_firebase.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.app2_use_firebase.R;
import com.example.app2_use_firebase.databinding.ActivityProfileBinding;
import com.example.app2_use_firebase.model.User;
import com.example.app2_use_firebase.web_service.SoapClient;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends BaseActivity {
    ActivityProfileBinding binding;
    TextView tvUserName, tvEmail;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigation();
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initUI();
        initProfile();
        setonclickDetil();


        /*mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            SharedPreferences sharedPref = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
            String userId = sharedPref.getString("userId", null);
            Log.d("User null", "Not Login: "+userId);
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }*/



        binding.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,FavActivity.class));
            }
        });

    }

    private void setonclickDetil(){
        ConstraintLayout detail = findViewById(R.id.detail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SettingProfileActivity.class));
            }
        });
    }
    private void initUI() {
        tvUserName = findViewById(R.id.tv_userName);
        tvEmail = findViewById(R.id.tv_email);
    }

    private void initProfile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sharedPref = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
                    String userId = sharedPref.getString("userId", null);

                    User user = SoapClient.getInstance().getUserById(userId);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (user == null) {
                                Log.d("User Null", "Not Login: "+ userId);
                                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                finish();
                            }else {
                                String name = user.getName();
                                String email = user.getEmail();
                                Toast.makeText(ProfileActivity.this, "" + name, Toast.LENGTH_SHORT).show();
                                tvUserName.setText(name);
                                tvEmail.setText(email);
                            }
                        }
                    });
                }catch (Exception e) {
                    Log.d("User Null", "Not Login", e);
                }
            }
        }).start();


        // Lấy thông tin người dùng hiện tại từ Firebase Authentication
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Hiển thị thông tin người dùng lên UI
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            Toast.makeText(ProfileActivity.this, "" + name, Toast.LENGTH_SHORT).show();
//            tvUserName.setText(name);
//            tvEmail.setText(email);
//        }
    }


    public void bottomNavigation() {
        LinearLayout cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, CartActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        LinearLayout home = findViewById(R.id.home_nav);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
        LinearLayout bill = findViewById(R.id.bill);
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,BillActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });


    }

    public void signOut(View view) {
       logoutUser();
    }



    // đăng xuất
    private void logoutUser() {
//        mAuth.signOut();
        // xóa trạng thái đăng nhập
        clearLoginState();
        // chuyển sang màn hình đăng nhập
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    // xóa trạng thái đăng nhập
    private void clearLoginState() {
        SharedPreferences sharedPref = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("userId");
        editor.apply();
    }


}
