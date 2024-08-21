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
        private readonly IMongoCollection<ItemsPopular> _itemsBag;
        private static ItemsTuiXachService instance;
        public ItemsTuiXachService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsBag = database.GetCollection<ItemsPopular>("ItemsBag");
        }

        public static ItemsTuiXachService GetInstanceBag()
        {
            if (instance == null) instance = new ItemsTuiXachService();
            return instance;
        }

        public List<ItemsPopular> GetAllItemsBag()
        {
            var filter = Builders<ItemsPopular>.Filter.Empty;
            var projection = Builders<ItemsPopular>.Projection.Exclude("id");
            var result = _itemsBag.Find(filter).Project<ItemsPopular>(projection).ToList();
            return result;
        }
    }
}