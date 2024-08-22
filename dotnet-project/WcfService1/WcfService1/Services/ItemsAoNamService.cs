using MongoDB.Driver;
using System;
using System.Collections.Generic;
using WcfService1.Models;



namespace WcfService1.Services
{
    public class ItemsAoNamService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsAoNamCollection;
        private static ItemsAoNamService instance;
        public ItemsAoNamService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsAoNamCollection = database.GetCollection<ItemsDomain>("ItemsAoNam");
        }

        public static ItemsAoNamService GetInstance()
        {
            if (instance == null) instance = new ItemsAoNamService();
            return instance;
        }

        public List<ItemsDomain> GetAllItemsAoNams()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsAoNamCollection.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
        public ItemsDomain GetItemsAoNamById(string id)
        {
            var objectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var filter = Builders<ItemsDomain>.Filter.Eq(ip => ip._id, objectId);
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsAoNamCollection.Find(filter).Project<ItemsDomain>(projection).FirstOrDefault();
            return result;
        }
    }
}