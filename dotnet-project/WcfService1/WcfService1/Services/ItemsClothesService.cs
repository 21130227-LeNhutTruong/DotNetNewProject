using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsClothesService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsClothes;
        private static ItemsClothesService instance;
        public ItemsClothesService() {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsClothes = database.GetCollection<ItemsDomain>("ItemsQuanNu");
        }

        public static ItemsClothesService GetInstance()
        {
            if (instance == null) instance = new ItemsClothesService();
            return instance;       
        }

        public List<ItemsDomain> GetAllItemsClothes()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsClothes.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
    }
}