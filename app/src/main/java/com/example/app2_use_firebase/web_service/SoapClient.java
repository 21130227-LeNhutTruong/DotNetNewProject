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
import com.example.app2_use_firebase.services.GetAllItemsService;
import com.example.app2_use_firebase.services.ItemsAoNamService;
import com.example.app2_use_firebase.services.ItemsAoNuService;
import com.example.app2_use_firebase.services.ItemsAoService;
import com.example.app2_use_firebase.services.ItemsBagService;
import com.example.app2_use_firebase.services.ItemsClothesService;
import com.example.app2_use_firebase.services.ItemsGiayService;
import com.example.app2_use_firebase.services.ItemsPopularService;
import com.example.app2_use_firebase.services.ItemsQuanNamService;
import com.example.app2_use_firebase.services.ItemsQuanNuService;
import com.example.app2_use_firebase.services.ItemsQuanService;
import com.example.app2_use_firebase.services.ItemsTuiXachService;
import com.example.app2_use_firebase.services.SliderItemsService;
import com.example.app2_use_firebase.services.UserService;

import java.util.List;

public class SoapClient {

    private static final String NAMESPACE = "http://tempuri.org/";


//    ngrok http 55685 --host-header="localhost:55685"

    private static final String URL = "https://0942-112-197-38-97.ngrok-free.app/Service1.svc";


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
        return CartService.getInstance().updateCartQuantity(NAMESPACE, URL, id, idProduct, quantity);
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

    public List<ItemsDomain> getItemsGiay() {
        return ItemsGiayService.getInstance().getItemsGiayService(NAMESPACE, URL);
    }

    public ItemsDomain getItemsGiayById(String id) {
        return ItemsGiayService.getInstance().getItemsGiayById(NAMESPACE, URL, id);
    }

    public List<ItemsDomain> getItemsBag() {
        return ItemsBagService.getInstance().getItemsBagService(NAMESPACE, URL);
    }

    public ItemsDomain getItemsBagById(String id) {
        return ItemsBagService.getInstance().getItemsBagById(NAMESPACE, URL, id);
    }

    public List<ItemsDomain> getItemsClothes() {
        return ItemsClothesService.getInstance().getItemsClothes(NAMESPACE, URL);
    }

//    public List<ItemsDomain> getItemsTuiXach() {


    public ItemsDomain getItemsClothesById(String id) {
        return ItemsClothesService.getInstance().getItemsClothesById(NAMESPACE, URL, id);
    }

    public List<ItemsDomain> getItemsTuiXach() {
        return ItemsTuiXachService.getInstance().getItemsTuiXach(NAMESPACE, URL);
    }

    public List<ItemsPopular> getAllItemsService() {
        return GetAllItemsService.getInstance().getAllItems(NAMESPACE, URL);
    }

    public List<ItemsPopular> getSearchService(String query) {
        return GetAllItemsService.getInstance().searchItems("searchItems", NAMESPACE, URL);
    }

    public ItemsDomain getItemsTuiXachById(String id) {
        return ItemsTuiXachService.getInstance().getItemsTuiXachById(NAMESPACE, URL, id);
    }

    public List<ItemsDomain> getAllItemsAos() {
        return ItemsAoService.getInstance().getItemsAos(NAMESPACE, URL);
    }

    public ItemsDomain getItemsAoById(String id) {
        return ItemsAoService.getInstance().getItemsAoById(NAMESPACE, URL, id);
    }

    public List<Bill> getBillByUser(String idUser) {
        return BillService.getInstance().getBillByUser(NAMESPACE, URL, idUser);
    }

    public BillDetail getBillDetail(String idUser) {
        return BillDetailService.getInstance().getBillDetail(NAMESPACE, URL, idUser);
    }

    public boolean addBill(Bill bill) {
        return BillService.getInstance().addBill(NAMESPACE, URL, bill);
    }

    public boolean deleteCart(String id) {
        return CartService.getInstance().deleteCart(NAMESPACE, URL, id);
    }


    public boolean addNewCart(String idUser, String idProduct, String type) {
        return CartService.getInstance().addNewCart(NAMESPACE, URL, idUser, idProduct, type);
    }


    public List<ItemsDomain> getAllItemsAoNams() {
        return ItemsAoNamService.getInstance().getItemsAoNams(NAMESPACE, URL);
    }

    public ItemsDomain getItemsAoNamById(String id) {
        return ItemsAoNamService.getInstance().getItemsAoNamById(NAMESPACE, URL, id);
    }

    public List<ItemsDomain> getAllItemsAoNus() {
        return ItemsAoNuService.getInstance().getItemsAoNus(NAMESPACE, URL);
    }

    public ItemsDomain getItemsAoNuById(String id) {
        return ItemsAoNuService.getInstance().getItemsAoNuById(NAMESPACE, URL, id);
    }
}


