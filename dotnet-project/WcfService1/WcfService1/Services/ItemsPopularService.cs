using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsPopularService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsPopular;
        private static ItemsPopularService instance;
        public ItemsPopularService() {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsPopular = database.GetCollection<ItemsDomain>("ItemsPopular");
        }

        public static ItemsPopularService GetInstance()
        {
            if (instance == null) instance = new ItemsPopularService();
            return instance;       
        }

        public List<ItemsDomain> GetAllItemsPopulars()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsPopular.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
    }
}