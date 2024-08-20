package com.example.app2_use_firebase.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app2_use_firebase.Adapter.PopularAdapter;
import com.example.app2_use_firebase.Adapter.SliderImgAdapter;
import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.R;
import com.example.app2_use_firebase.databinding.ActivityListGiayBinding;
import com.example.app2_use_firebase.web_service.SoapClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiayActivity extends BaseActivity{
    ActivityListGiayBinding binding;
    private ViewPager2 viewPager2, viewPager3;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable,sliderRunnable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListGiayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        initGiay();
        initSliderImage();
        initSliderImage2();
        setClickAction();


    }
    private void initGiay() {
        DatabaseReference myRef = database.getReference("ItemsGiay");
        binding.progressAo.setVisibility(View.VISIBLE);
        ArrayList<ItemsDomain> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(ItemsDomain.class));

                    }
                    if (!items.isEmpty()) {
                        binding.recyclerViewListGiay.setLayoutManager(new GridLayoutManager(GiayActivity.this, 2));
                        binding.recyclerViewListGiay.setAdapter(new PopularAdapter(items));


                    }
                    binding.progressAo.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initSliderImage(){
        binding.progressAo.setVisibility(View.VISIBLE);
        ArrayList<String> imageUrls = new ArrayList<>();
        viewPager2 = binding.viewPager2;


        SoapClient soapClient = new SoapClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    final List<Banner> banners = soapClient.callGetBannersService();
                    final List<SliderItems> itemsSliderItems = soapClient.getSliderItems();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (itemsSliderItems != null && !itemsSliderItems.isEmpty()) {

                                SliderItems sliderItem = itemsSliderItems.get(0);
                                imageUrls.addAll(sliderItem.getPicUrl());

                                if (!imageUrls.isEmpty()) {
                                    SliderImgAdapter slideAdapter = new SliderImgAdapter(GiayActivity.this, imageUrls);
                                    viewPager2.setAdapter(slideAdapter);
                                }

                                binding.progressAo.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(GiayActivity.this, "Web service connect error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("SoapClient", "Error: " + e.getMessage(), e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        }).start();


        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getCurrentItem() < imageUrls.size() - 1) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                } else {
                    viewPager2.setCurrentItem(0);
                }
                sliderHandler.postDelayed(this, 3000);
            }
        };

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        });

        sliderHandler.postDelayed(sliderRunnable, 5000);
    }
    private void initSliderImage2(){
        viewPager3 = binding.viewPager3;
        binding.progressAo.setVisibility(View.VISIBLE);
        ArrayList<String> imageUrls = new ArrayList<>();

        SoapClient soapClient = new SoapClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    final List<Banner> banners = soapClient.callGetBannersService();
                    final List<SliderItems> itemsSliderItems = soapClient.getSliderItems();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (itemsSliderItems != null && !itemsSliderItems.isEmpty()) {

                                SliderItems sliderItem = itemsSliderItems.get(1);
                                imageUrls.addAll(sliderItem.getPicUrl());

                                if (!imageUrls.isEmpty()) {
                                    SliderImgAdapter slideAdapter = new SliderImgAdapter(GiayActivity.this, imageUrls);
                                    viewPager3.setAdapter(slideAdapter);
                                }

                                binding.progressAo.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(GiayActivity.this, "Web service connect error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("SoapClient", "Error: " + e.getMessage(), e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }
        }).start();


        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager3.getCurrentItem() < imageUrls.size() - 1) {
                    viewPager3.setCurrentItem(viewPager3.getCurrentItem() + 1);
                } else {
                    viewPager3.setCurrentItem(0);
                }
                sliderHandler.postDelayed(this, 3000);
            }
        };

        viewPager3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        });

        sliderHandler.postDelayed(sliderRunnable, 5000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
        sliderHandler.removeCallbacks(sliderRunnable2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
        sliderHandler.postDelayed(sliderRunnable2, 3000);
    }
    private void setClickAction(){
        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
