package com.example.app2_use_firebase.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2_use_firebase.Adapter.SearchListAdapter;
import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.R;
import com.example.app2_use_firebase.databinding.ActivitySearchBinding;
import com.example.app2_use_firebase.web_service.SoapClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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
//    private void initSearch(){
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<ItemsDomain> itemsDomains = SoapClient.getInstance().getAllItemsAoNams();
//                    items.addAll(itemsDomains);
//                }catch (Exception e) {
//                    Log.d("SOAP ERROR", "ERROR CONNECTION", e);
//                }
//            }
//        }).start();
//
//    }
    private void initSearch1(){

        DatabaseReference myData = database.getReference("ItemsQuan");
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(ItemsDomain.class));

                    }
                    if (!items.isEmpty()) {
                        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this,RecyclerView.VERTICAL,false));
                        binding.recyclerViewSearch.setAdapter(searchListAdapter = new SearchListAdapter(items));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void initSearch2(){

        DatabaseReference myData = database.getReference("ItemsAo");
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(ItemsDomain.class));

                    }
                    if (!items.isEmpty()) {
                        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this,RecyclerView.VERTICAL,false));
                        binding.recyclerViewSearch.setAdapter(searchListAdapter = new SearchListAdapter(items));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void initSearch3(){

        DatabaseReference myData = database.getReference("ItemsGiay");
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(ItemsDomain.class));

                    }
                    if (!items.isEmpty()) {
                        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this,RecyclerView.VERTICAL,false));
                        binding.recyclerViewSearch.setAdapter(searchListAdapter = new SearchListAdapter(items));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void initSearch4(){

        DatabaseReference myData = database.getReference("ItemsTuiXach");
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(ItemsDomain.class));

                    }
                    if (!items.isEmpty()) {
                        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this,RecyclerView.VERTICAL,false));
                        binding.recyclerViewSearch.setAdapter(searchListAdapter = new SearchListAdapter(items));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
//    public void filterList(String text) {
//        List<ItemsDomain> fiteredList = new ArrayList<>();
//        for (ItemsDomain item : items) {
//            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
//                fiteredList.add(item);
//            }
//        }
//        Log.d("SEARCH", "SEARCH: " + items);
//        if (fiteredList.isEmpty()) {
//            // Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//        } else {
//            searchListAdapter.setFilteredList(fiteredList);
//        }
//    }

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
//private void initSearch() {
//    new Thread(() -> {
//        try {
//            List<ItemsDomain> allItems = SoapClient.getInstance().getSearchService("");
//            runOnUiThread(() -> {
//                if (allItems != null && !allItems.isEmpty()) {
//                    for (ItemsDomain allItem : allItems) {
//                        ItemsDomain itemsDomain = new ItemsDomain(
//                                allItem.get_id(),
//                                allItem.getTitle(),
//                                allItem.getDescription(),
//                                allItem.getPicUrl(),
//                                allItem.getDes(),
//                                allItem.getPrice(),
//                                allItem.getOldPrice(),
//                                allItem.getReview(),
//                                allItem.getRating()
//                        );
//                        items.add(itemsDomain);
//                    }
//
//                    if (!items.isEmpty()) {
//                        searchListAdapter = new SearchListAdapter(items);
//                        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this, RecyclerView.VERTICAL, false));
//                        binding.recyclerViewSearch.setAdapter(searchListAdapter);
//                    }
//                } else {
//                    Toast.makeText(SearchHomeActivity.this, "No items found", Toast.LENGTH_LONG).show();
//                }
//            });
//        } catch (Exception e) {
//            Log.e("SearchHomeActivity", "Error initializing search: " + e.getMessage(), e);
//        }
//    }).start();
//}

    private void performSearch(String query) {
        List<ItemsDomain> fiteredList = new ArrayList<>();
        searchListAdapter = new SearchListAdapter(items);
        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this, RecyclerView.VERTICAL, false));
        binding.recyclerViewSearch.setAdapter(searchListAdapter);
        new Thread(() -> {
            try {
                // Ghi log tham số tìm kiếm
//                Log.d("SearchHomeActivity", "Đang thực hiện tìm kiếm với từ khóa: " + query);

                // Gọi dịch vụ SOAP để tìm kiếm
                List<ItemsDomain> searchResults = SoapClient.getInstance().getSearchService(query);


                // Ghi log kết quả tìm kiếm
//                Log.d("SearchHomeActivity", "Nhận được kết quả tìm kiếm: " + searchResults.get(0).getTitle());

                runOnUiThread(() -> {
                    if (searchResults != null && !searchResults.isEmpty()) {
                        items.clear();
                        items.addAll(searchResults);

                        if (searchListAdapter == null) {
                            searchListAdapter = new SearchListAdapter(items);
                            binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(SearchHomeActivity.this, RecyclerView.VERTICAL, false));
                            binding.recyclerViewSearch.setAdapter(searchListAdapter);
                        } else {
                            searchListAdapter.setFilteredList(items);
                            searchListAdapter.notifyDataSetChanged();
                        }

                    } else {
                        // Ghi log khi không có kết quả tìm kiếm
//                        Log.d("SearchHomeActivity", "Không tìm thấy mục nào với từ khóa tìm kiếm.");
                        Toast.makeText(SearchHomeActivity.this, "Không tìm thấy mục nào", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                // Ghi log lỗi nếu có
//                Log.e("SearchHomeActivity", "Lỗi khi tìm kiếm: " + e.getMessage(), e);
            }
        }).start();
    }

}
