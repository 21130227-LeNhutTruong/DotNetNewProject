using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using WcfService1.Models;
using ZstdSharp.Unsafe;

namespace WcfService1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IService1" in both code and config file together.
    [ServiceContract]
    public interface IService1
    {

        [OperationContract]
        List<Banner> GetAllBanners();

        [OperationContract]
        List<ItemsDomain> GetAllItemsPopulars();
        [OperationContract]
        ItemsDomain GetItemsPopularById(string id);

        [OperationContract]
        List<ItemsDomain> GetAllItemsGiay();
        [OperationContract]
        List<ItemsDomain> GetAllItemsBag();
        [OperationContract]
        List<ItemsDomain> GetAllItemsTuiXach();
        [OperationContract]
        List<ItemsDomain> GetAllItemsClothes();

        [OperationContract]
        List<SliderItems> GetSliderItems();

        [OperationContract]
        List<ItemsDomain> GetItemsQuan();
        [OperationContract]
        List<ItemsDomain> GetItemsQuanNam();
        [OperationContract]
        List<ItemsDomain> GetItemsQuanNu();
        [OperationContract]
        ItemsDomain GetItemsQuanById(string id);
        [OperationContract]
        ItemsDomain GetItemsQuanNamById(string id);
        [OperationContract]
        ItemsDomain GetItemsQuanNuById(string id);
        [OperationContract]
        bool AddUser(User user);
        [OperationContract]
        bool Register(User user);

        [OperationContract]
        bool SendEmail(string to, string subject, string body);

        [OperationContract]
        User CheckLogin(string email, string password);

        [OperationContract]
        User GetUserById(string id);

        [OperationContract]
        bool isExistUser(string email);

        [OperationContract]
        Cart GetCartByUser(string id);
        [OperationContract]
        bool UpdateCartQuantity(string id, string idProduct, int quantity);
        [OperationContract]
        bool AddCart(string id, string idProduct, int quantity, string type);
        [OperationContract]
        bool RemoveCart(string id, string idProduct);

    }


    //// Use a data contract as illustrated in the sample below to add composite types to service operations.
    //[DataContract]
    //public class CompositeType
    //{
    //    bool boolValue = true;
    //    string stringValue = "Hello ";

    //    [DataMember]
    //    public bool BoolValue
    //    {
    //        get { return boolValue; }
    //        set { boolValue = value; }
    //    }

    //    [DataMember]
    //    public string StringValue
    //    {
    //        get { return stringValue; }
    //        set { stringValue = value; }
    //    }
    //}
}
