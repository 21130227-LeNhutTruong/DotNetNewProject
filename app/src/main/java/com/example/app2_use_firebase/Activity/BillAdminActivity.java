package com.example.app2_use_firebase.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2_use_firebase.Adapter.AdminBillAdapter;
import com.example.app2_use_firebase.Domain.Bill;
import com.example.app2_use_firebase.R;
import com.example.app2_use_firebase.databinding.ActivityAdminBillListBinding;
import com.example.app2_use_firebase.web_service.SoapClient;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BillAdminActivity extends BaseActivity {
    private RecyclerView rvBillList;
    private ArrayList<Bill> billList;
    private AdminBillAdapter billAdapter;
    private FirebaseFirestore db;
    ActivityAdminBillListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBillListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        rvBillList = findViewById(R.id.rvBillList);
        db = FirebaseFirestore.getInstance();
        billList = new ArrayList<>();

        billAdapter = new AdminBillAdapter(billList, this);
        rvBillList.setLayoutManager(new LinearLayoutManager(this));
        rvBillList.setAdapter(billAdapter);

        billAdapter.setOnItemClickListener(new AdminBillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bill bill) {
                showStatusUpdateDialog(bill);
            }
        });

            loadBillsFromFirebase();


    }
    private void loadBillsFromFirebase() {
        // load dữ liệu từ firestore
//        db.collectionGroup("bills")
//                .get()
//                .addOnCompleteListener(task -> {
//                    // Xử lý kết quả
//                    if (task.isSuccessful()) {
//                        // Lấy danh sách hóa đơn từ kết quả
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            // Tạo đối tượng hóa đơn từ document
//                            Bill bill = document.toObject(Bill.class);
//                            bill.setId(document.getId()); // Thiết lập id từ document ID
//                            // Thêm hóa đơn vào danh sách
//                            billList.add(bill);
//                        }
//                        // Cập nhật dữ liệu vào adapter
//                        billAdapter.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(BillAdminActivity.this, "Lỗi khi tải hóa đơn", Toast.LENGTH_SHORT).show();
//                    }
//                });


        new Thread(new Runnable() {
            @Override
            public void run() {
                List<com.example.app2_use_firebase.model.Bill> bills = SoapClient.getInstance().getAllBills();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < bills.size(); i++) {
                                com.example.app2_use_firebase.model.Bill bill = bills.get(i);
                                Bill bill1 = new Bill(bill.getFullName(), bill.getUserId(), bill.get_id(), bill.getDate().toString(),
                                        bill.getFullName(), bill.getAddress(), bill.getPhone(), bill.getPayment(),
                                        bill.getTotalAmount(), null, bill.getStatus());
                                billList.add(bill1);
                            }

                            billAdapter.notifyDataSetChanged();
                        }catch (Exception e) {
                            Log.d("SOAP ERROR", "ERROR CONNECT SOAP", e);
                        }

                    }
                });
            }
        }).start();
    }
    // hiển thị dialog update
    private void showStatusUpdateDialog(Bill bill) {
        // Dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_status);
        dialog.show();

        // View
        Spinner spinner = dialog.findViewById(R.id.spinnerStatus);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Đã xác nhận", "Đang giao hàng", "Hoàn thành"});
        spinner.setAdapter(arrayAdapter);

        btnUpdate.setOnClickListener(v -> {
            String selectedStatus = spinner.getSelectedItem().toString();
            updateBillStatus(bill, selectedStatus);
            dialog.dismiss();
        });

}
    private void updateBillStatus(Bill bill, String status) {
//        DocumentReference billRef = db.collection("users").document(bill.getUserId()).collection("bills").document(bill.getId());
//        billRef.update("status", status)
//                .addOnSuccessListener(aVoid -> Toast.makeText(BillAdminActivity.this, "Trạng thái đã được cập nhật", Toast.LENGTH_SHORT).show())
//                .addOnFailureListener(e -> Toast.makeText(BillAdminActivity.this, "Lỗi khi cập nhật trạng thái", Toast.LENGTH_SHORT).show());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean updateBill = SoapClient.getInstance().updateBill(bill.getId(), status);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (updateBill) {
                                bill.setStatus(status);
                                billAdapter.notifyDataSetChanged();
                                Toast.makeText(BillAdminActivity.this, "Update Status Bill Success", Toast.LENGTH_LONG).show();
                                Log.d("SOAP", "UPDATE BILL: "+updateBill);
                            }else {
                                Log.d("SOAP", "UPDATE BILL: "+updateBill);
                            }
                        }
                    });
                }catch (Exception e) {
                    Log.d("SOAP ERROR", "ERROR CONNECT", e);
                }



            }
        }).start();

    }

}
