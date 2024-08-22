using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsTuiXachService
    {
        private readonly IMongoCollection<ItemsDomain> _itemTuiXach;
        private static ItemsTuiXachService instance;
        public ItemsTuiXachService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemTuiXach = database.GetCollection<ItemsDomain>("ItemsTuiXach");
        }

        public static ItemsTuiXachService GetInstanceTuiXach()
        {
            if (instance == null) instance = new ItemsTuiXachService();
            return instance;
        }

        public List<ItemsDomain> GetAllItemsTuiXach()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemTuiXach.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
        public ItemsDomain GetItemsTuiSachById(string id)
        {
            var objectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var filter = Builders<ItemsDomain>.Filter.Eq(ip => ip._id, objectId);
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemTuiXach.Find(filter).Project<ItemsDomain>(projection).FirstOrDefault();
            return result;
        }
    }
}