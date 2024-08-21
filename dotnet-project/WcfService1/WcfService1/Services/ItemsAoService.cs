using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsAoService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsAoCollection;
        private static ItemsAoService instance;
        public ItemsAoService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsAoCollection = database.GetCollection<ItemsDomain>("ItemsAo");
        }

        public static ItemsAoService GetInstance()
        {
            if (instance == null) instance = new ItemsAoService();
            return instance;
        }

        public List<ItemsDomain> GetAllItemsAos()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsAoCollection.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
    }
}