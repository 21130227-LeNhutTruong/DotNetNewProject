package com.example.app2_use_firebase.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app2_use_firebase.Adapter.PopularAdapter;
import com.example.app2_use_firebase.Adapter.SliderImgAdapter;
import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.databinding.ActivictyListClotheAoBinding;
import com.example.app2_use_firebase.web_service.SoapClient;

import java.util.ArrayList;
import java.util.List;

public class AoActivity extends BaseActivity{
    ActivictyListClotheAoBinding binding;
    private ViewPager2 viewPager2, viewPager3;
    private Handler sliderHandler = new Handler(Looper.getMainLooper());
    private Runnable sliderRunnable,sliderRunnable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivictyListClotheAoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        initAo();
        initSliderImage();
        initSliderImage2();
        setClickAction();


    }

    // hiển thị sản phẩm
    private void initAo() {
        binding.progressAo.setVisibility(View.VISIBLE);
        ArrayList<ItemsDomain> items = new ArrayList<>();

        SoapClient soapClient = new SoapClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<ItemsDomain> itemsAoList = soapClient.getAllItemsAos();

                    for (int i = 0; i < itemsAoList.size(); i++) {
                        itemsAoList.get(i).setType("ItemsAo");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (itemsAoList != null && !itemsAoList.isEmpty()) {
                                items.addAll(itemsAoList);

                                if (!items.isEmpty()) {
                                    binding.recyclerViewListAo.setLayoutManager(new GridLayoutManager(AoActivity.this, 2));
                                    binding.recyclerViewListAo.setAdapter(new PopularAdapter(items));
                                }

                                binding.progressAo.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(AoActivity.this, "Web service connect error", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("SoapClient", "Error: " + e.getMessage(), e);
                }
            }
        }).start();
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
                                    SliderImgAdapter slideAdapter = new SliderImgAdapter(AoActivity.this, imageUrls);
                                    viewPager2.setAdapter(slideAdapter);
                                }

                                binding.progressAo.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(AoActivity.this, "Web service connect error", Toast.LENGTH_LONG).show();
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
                                    SliderImgAdapter slideAdapter = new SliderImgAdapter(AoActivity.this, imageUrls);
                                    viewPager3.setAdapter(slideAdapter);
                                }

                                binding.progressAo.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(AoActivity.this, "Web service connect error", Toast.LENGTH_LONG).show();
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
        // click back
        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
