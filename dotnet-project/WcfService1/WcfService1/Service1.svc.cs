using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using WcfService1.Models;
using WcfService1.Services;

namespace WcfService1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    public class Service1 : IService1
    {
        
        public List<Banner> GetAllBanners()
        {
            return BannerService.GetInstance().GetAllBanners();
        }

        public List<ItemsDomain> GetAllItemsPopulars()
        {
            return ItemsPopularService.GetInstance().GetAllItemsPopulars();
        }
        public List<SliderItems> GetSliderItems()
        {
            return SliderItemService.GetInstance().GetSliderItems();
        }
        public List<ItemsDomain> GetItemsQuan()
        {
            return ItemsQuanService.GetInstance().GetItemsQuan();
        }
        public List<ItemsDomain> GetItemsQuanNam()
        {
            return ItemsQuanNamService.GetInstance().GetItemsQuanNam();
        }
        public List<ItemsDomain> GetItemsQuanNu()
        {
            return ItemsQuanNuService.GetInstance().GetItemsQuanNu();
        }

        public ItemsDomain GetItemsPopularById(string id)
        {
            return ItemsPopularService.GetInstance().GetItemsPopularById(id);
        }

        public bool AddUser(User user)
        {
            return UserService.GetInstance().AddUser(user);
        }
        
        public bool Register(User user)
        {
            return UserService.GetInstance().Register(user);
        }

        public bool SendEmail(string to, string subject, string body)
        {
           return EmailService.GetInstance().SendEmail(to, subject, body);
        }

        public User CheckLogin(string email, string password)
        {
            return UserService.GetInstance().CheckLogin(email, password);
        }

        public User GetUserById(string id)
        {
            return UserService.GetInstance().GetUserById(id);
        }

        public bool isExistUser(string email)
        {
            return UserService.GetInstance().isExistUser(email);   
        }

        public Cart GetCartByUser(string id)
        {
            return CartService.GetInstance().GetCartByUser(id);
        }

        public bool UpdateCartQuantity(string id, string idProduct, int quantity)
        {
            return CartService.GetInstance().UpdateCartQuantity(id, idProduct, quantity);
        }

        public bool AddCart(string id, string idProduct, int quantity, string type)
        {
            return CartService.GetInstance().AddCart(id, idProduct, quantity, type);
        }

        public bool RemoveCart(string id, string idProduct)
        {
            return CartService.GetInstance().RemoveCart(id, idProduct);
        }


        public List<ItemsDomain> GetAllItemsGiay()
        {
            return ItemsGiayService.GetInstance().GetAllItemsGiay();


        }

        public List<ItemsDomain> GetAllItemsBag()
        {
            return ItemsBagService.GetInstanceBag().GetAllItemsBag();
        }

        public List<ItemsDomain> GetAllItemsTuiXach()
        {
            return ItemsTuiXachService.GetInstanceTuiXach().GetAllItemsTuiXach();
        }

        public List<ItemsDomain> GetAllItemsClothes()
        {
            return ItemsClothesService.GetInstance().GetAllItemsClothes();
        }


      
        public List<ItemsDomain> GetAllItems()
        {
            // Khởi tạo dịch vụ với kết nối MongoDB và tên cơ sở dữ liệu
            var connectionString = "mongodb://localhost:27017";
            var databaseName = "shop";
            var collectionNames = new[] { "ItemsPopular", "ItemsQuan", "ItemsAo", "ItemsGiay", "ItemsTuiXach" };

            var getAllItemsService = new GetAllItemsService(connectionString, databaseName);
            return getAllItemsService.GetAllItems(collectionNames);
        }


        public List<ItemsDomain> SearchItems(string searchText)
        {
            var connectionString = "mongodb://localhost:27017";
            var databaseName = "shop";
            var collectionNames = new[] { "ItemsPopular", "ItemsQuan", "ItemsAo", "ItemsGiay", "ItemsTuiXach" };

            var searchItemsService = new GetAllItemsService(connectionString, databaseName);
            return searchItemsService.SearchItems(collectionNames, searchText);
        }




        //public CompositeType GetDataUsingDataContract(CompositeType composite)
        //{
        //    if (composite == null)
        //    {
        //        throw new ArgumentNullException("composite");
        //    }
        //    if (composite.BoolValue)
        //    {
        //        composite.StringValue += "Suffix";
        //    }
        //    return composite;
        //}
    }
}
