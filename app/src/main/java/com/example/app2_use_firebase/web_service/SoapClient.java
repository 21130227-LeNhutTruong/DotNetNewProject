package com.example.app2_use_firebase.web_service;

import com.example.app2_use_firebase.Domain.ItemsDomain;
import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.model.Banner;
import com.example.app2_use_firebase.model.Bill;
import com.example.app2_use_firebase.model.BillDetail;
import com.example.app2_use_firebase.model.Cart;
import com.example.app2_use_firebase.model.ItemsPopular;
import com.example.app2_use_firebase.model.User;
import com.example.app2_use_firebase.services.BannerService;
import com.example.app2_use_firebase.services.BillDetailService;
import com.example.app2_use_firebase.services.BillService;
import com.example.app2_use_firebase.services.CartService;
import com.example.app2_use_firebase.services.ItemsBagService;
import com.example.app2_use_firebase.services.ItemsGiayService;
import com.example.app2_use_firebase.services.ItemsPopularService;
import com.example.app2_use_firebase.services.ItemsQuanNamService;
import com.example.app2_use_firebase.services.ItemsQuanNuService;
import com.example.app2_use_firebase.services.ItemsQuanService;
import com.example.app2_use_firebase.services.SliderItemsService;
import com.example.app2_use_firebase.services.UserService;

import java.util.List;

public class SoapClient {

    private static final String NAMESPACE = "http://tempuri.org/";
    
//ngrok http 55685 --host-header="localhost:55685"
    private static final String URL = "https://fe2b-2001-ee0-51b8-8f60-c0d0-776f-73f9-9424.ngrok-free.app/Service1.svcBil";


    private static SoapClient instance;

   public static SoapClient getInstance() {
        if (instance == null) instance = new SoapClient();
        return instance;
    }

    public List<Banner> getBanners() {
        return BannerService.getInstance().getAllBanners(NAMESPACE, URL);
    }

    public List<ItemsPopular> getAllItemsPopular() {
        return ItemsPopularService.getInstance().getItemsPopular(NAMESPACE, URL);
    }

    public ItemsDomain getItemsPopularsById(String id) {
        return ItemsPopularService.getInstance().getItemsPopularsById(NAMESPACE, URL, id);
    }

    public boolean addUser(User user) {
        return UserService.getInstance().addUser(NAMESPACE, URL, user);
    }

    public boolean register(User user) {
        return UserService.getInstance().register(NAMESPACE, URL, user);
    }

    public com.example.app2_use_firebase.model.User checkLogin(String email, String pass) {
        return UserService.getInstance().checkLogin(NAMESPACE, URL, email, pass);
    }

    public com.example.app2_use_firebase.model.User getUserById(String id) {
        return UserService.getInstance().getUserById(NAMESPACE, URL, id);
    }

    public boolean isExistUser(String email) {
        return UserService.getInstance().isExistUser(NAMESPACE, URL, email);
    }

    public Cart getCartByUser(String userId) {
        return CartService.getInstance().getCartByUser(NAMESPACE, URL, userId);
    }

    public boolean updateCartQuantity(String id, String idProduct, int quantity) {
        return CartService.getInstance().updateCartQuantity(NAMESPACE,URL, id, idProduct, quantity);
    }

    public boolean removeCart(String id, String idProduct) {
        return CartService.getInstance().removeCart(NAMESPACE, URL, id, idProduct);
    }

    public boolean addCart(String id, String idProduct, int quantity, String type) {
        return CartService.getInstance().addCart(NAMESPACE, URL, id, idProduct, quantity, type);
    }

    public List<SliderItems> getSliderItems() {
        return SliderItemsService.getInstance().getSliderItems(NAMESPACE, URL);
    }
    public List<ItemsDomain> getItemsQuan() {
       return ItemsQuanService.getInstance().getItemsQuan(NAMESPACE, URL);
    }
    public List<ItemsDomain> getItemsQuanNam() {
        return ItemsQuanNamService.getInstance().getItemsQuanNam(NAMESPACE, URL);
    }
    public List<ItemsDomain> getItemsQuanNu() {
        return ItemsQuanNuService.getInstance().getItemsQuanNu(NAMESPACE, URL);
    }

    public List<ItemsPopular> getItemsGiay() {
        return ItemsGiayService.getInstance().getItemsGiayService(NAMESPACE, URL);
    }
    public List<ItemsPopular> getItemsBag() {
        return ItemsBagService.getInstance().getItemsBagService(NAMESPACE, URL);
    }

    public List<Bill> getBillByUser(String idUser) {
       return BillService.getInstance().getBillByUser(NAMESPACE, URL, idUser);
    }

    public BillDetail getBillDetail(String idUser) {
       return BillDetailService.getInstance().getBillDetail(NAMESPACE, URL, idUser);
    }

    public boolean deleteCart(String id) {
       return CartService.getInstance().deleteCart(NAMESPACE, URL, id);
    }

    public boolean addBill(Bill bill, String idProduct, int quantity, String type) {
       return BillService.getInstance().addBill(NAMESPACE, URL, bill, idProduct, quantity, type);
    }

    public boolean addNewCart(String idUser, String idProduct, String type) {
       return CartService.getInstance().addNewCart(NAMESPACE, URL, idUser, idProduct, type);
    }


}

