using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsGiayService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsGiay;
        private static ItemsGiayService instance;
        public ItemsGiayService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsGiay = database.GetCollection<ItemsDomain>("ItemsGiay");
        }

        public static ItemsGiayService GetInstance()
        {
            if (instance == null) instance = new ItemsGiayService();
            return instance;
        }

        public List<ItemsDomain> GetAllItemsGiay()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsGiay.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
        public ItemsDomain GetItemsGiayById(string id)
        {
            var objectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var filter = Builders<ItemsDomain>.Filter.Eq(ip => ip._id, objectId);
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsGiay.Find(filter).Project<ItemsDomain>(projection).FirstOrDefault();
            return result;
        }
    }
}