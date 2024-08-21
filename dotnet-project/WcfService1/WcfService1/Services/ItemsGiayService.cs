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
        private readonly IMongoCollection<ItemsPopular> _itemsGiay;
        private static ItemsGiayService instance;
        public ItemsGiayService() {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsGiay = database.GetCollection<ItemsPopular>("ItemsGiay");
        }

        public static ItemsGiayService GetInstance()
        {
            if (instance == null) instance = new ItemsGiayService();
            return instance;       
        }

        public List<ItemsPopular> GetAllItemsGiay()
        {
            var filter = Builders<ItemsPopular>.Filter.Empty;
            var projection = Builders<ItemsPopular>.Projection.Exclude("id");
            var result = _itemsGiay.Find(filter).Project<ItemsPopular>(projection).ToList();
            return result;
        }
    }
}