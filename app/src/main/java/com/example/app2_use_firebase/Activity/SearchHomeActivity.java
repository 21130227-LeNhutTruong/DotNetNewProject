package com.example.app2_use_firebase.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2_use_firebase.Adapter.PopularAdapter;
import com.example.app2_use_firebase.Adapter.SearchListAdapter;
import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.R;
import com.example.app2_use_firebase.databinding.ActivitySearchBinding;
import com.example.app2_use_firebase.model.ItemsPopular;
import com.example.app2_use_firebase.web_service.SoapClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class SearchHomeActivity extends BaseActivity {
    private ActivitySearchBinding binding;
    private SearchView searchView;

    private ArrayList<ItemsDomain> items = new ArrayList<>();
    private SearchListAdapter searchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

               // filterList(newText);
                //performSearch(newText);
                return true;
            }
        });
        //initSearch();
        ConstraintLayout bthbackHome = findViewById(R.id.back_home);
        bthbackHome.setOnClickListener(v -> finish());
    }
    private void filterList(String text) {
        List<ItemsDomain> filteredList = new ArrayList<>();
        for (ItemsDomain item : items) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (searchListAdapter != null) {
            searchListAdapter.setFilteredList(filteredList);
        }
    }
private void initSearch() {
    new Thread(() -> {
        try {
            List<ItemsPopular> allItems = SoapClient.getInstance().getSearchService("");
            runOnUiThread(() -> {
                if (allItems != null && !allItems.isEmpty()) {
                    for (ItemsPopular allItem : allItems) {
                        ItemsDomain itemsDomain = new ItemsDomain(
                                allItem.get_id(),
                                allItem.getTitle(),
                                allItem.getDescription(),
                                allItem.getPicUrl(),
                                allItem.getDes(),
                                allItem.getPrice(),
                                allItem.getOldPrice(),
                                allItem.getReview(),
                                allItem.getRating()
                        );
                        items.add(itemsDomain);
                    }

                    if (!items.isEmpty()) {
                        searchListAdapter = new SearchListAdapter(items);
                        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this, RecyclerView.VERTICAL, false));
                        binding.recyclerViewSearch.setAdapter(searchListAdapter);
                    }
                } else {
                    Toast.makeText(SearchHomeActivity.this, "No items found", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.e("SearchHomeActivity", "Error initializing search: " + e.getMessage(), e);
        }
    }).start();
}

    private void performSearch(String query) {
        new Thread(() -> {
            try {
                // Ghi log tham số tìm kiếm
                Log.d("SearchHomeActivity", "Đang thực hiện tìm kiếm với từ khóa: " + query);

                // Gọi dịch vụ SOAP để tìm kiếm
                List<ItemsPopular> searchResults = SoapClient.getInstance().getSearchService(query);
                // Ghi log kết quả tìm kiếm
                Log.d("SearchHomeActivity", "Nhận được kết quả tìm kiếm: " + searchResults);

                runOnUiThread(() -> {
                    if (searchResults != null && !searchResults.isEmpty()) {
                        ArrayList<ItemsDomain> searchItems = new ArrayList<>();
                        for (ItemsPopular item : searchResults) {
                            // Ghi log từng mục trong danh sách kết quả
                            Log.d("SearchHomeActivity", "Đang xử lý mục: " + item.getTitle());

                            ItemsDomain itemsDomain = new ItemsDomain(
                                    item.get_id(),
                                    item.getTitle(),
                                    item.getDescription(),
                                    item.getPicUrl(),
                                    item.getDes(),
                                    item.getPrice(),
                                    item.getOldPrice(),
                                    item.getReview(),
                                    item.getRating()
                            );
                            searchItems.add(itemsDomain);
                        }

                        if (searchListAdapter == null) {
                            // Ghi log khi thiết lập adapter mới
                            Log.d("SearchHomeActivity", "Thiết lập adapter mới với kết quả tìm kiếm.");
                            searchListAdapter = new SearchListAdapter(searchItems);
                            binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this, RecyclerView.VERTICAL, false));
                            binding.recyclerViewSearch.setAdapter(searchListAdapter);
                        } else {
                            // Ghi log khi cập nhật adapter hiện tại
                            Log.d("SearchHomeActivity", "Cập nhật adapter hiện tại với kết quả tìm kiếm mới.");
                            searchListAdapter.setFilteredList(searchItems);
                            searchListAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Ghi log khi không có kết quả tìm kiếm
                        Log.d("SearchHomeActivity", "Không tìm thấy mục nào với từ khóa tìm kiếm.");
                        Toast.makeText(SearchHomeActivity.this, "Không tìm thấy mục nào", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                // Ghi log lỗi nếu có
                Log.e("SearchHomeActivity", "Lỗi khi tìm kiếm: " + e.getMessage(), e);
            }
        }).start();
    }

}
