using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsAoNuService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsAoNuCollection;
        private static ItemsAoNuService instance;
        public ItemsAoNuService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsAoNuCollection = database.GetCollection<ItemsDomain>("ItemsAoNu");
        }

        public static ItemsAoNuService GetInstance()
        {
            if (instance == null) instance = new ItemsAoNuService();
            return instance;
        }

        public List<ItemsDomain> GetAllItemsAoNus()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsAoNuCollection.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
    }
}