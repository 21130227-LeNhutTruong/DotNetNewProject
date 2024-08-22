using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsBagService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsBag;
        private static ItemsBagService instance;
        public ItemsBagService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsBag = database.GetCollection<ItemsDomain>("ItemsBag");
        }

        public static ItemsBagService GetInstanceBag()
        {
            if (instance == null) instance = new ItemsBagService();
            return instance;
        }

        public List<ItemsDomain> GetAllItemsBag()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsBag.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }

        public ItemsDomain GetItemsBagById(string id)
        {
            var objectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var filter = Builders<ItemsDomain>.Filter.Eq(ip => ip._id, objectId);
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsBag.Find(filter).Project<ItemsDomain>(projection).FirstOrDefault();
            return result;
        }
    }
}