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
        private readonly IMongoCollection<ItemsPopular> _itemsPopular;
        private static ItemsPopularService instance;
        public ItemsPopularService() {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsPopular = database.GetCollection<ItemsPopular>("ItemsPopular");
        }

        public static ItemsPopularService GetInstance()
        {
            if (instance == null) instance = new ItemsPopularService();
            return instance;       
        }

        public List<ItemsPopular> GetAllItemsPopulars()
        {
            var filter = Builders<ItemsPopular>.Filter.Empty;
            var projection = Builders<ItemsPopular>.Projection.Exclude("id");
            var result = _itemsPopular.Find(filter).Project<ItemsPopular>(projection).ToList();
            return result;
        }

        public ItemsPopular GetItemsPopularById(string id)
        {
            var objectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var filter = Builders<ItemsPopular>.Filter.Eq(ip => ip._id, objectId);
            var projection = Builders<ItemsPopular>.Projection.Exclude("id");
            var result = _itemsPopular.Find(filter).Project<ItemsPopular>(projection).FirstOrDefault();
            return result;
        }
    }
}