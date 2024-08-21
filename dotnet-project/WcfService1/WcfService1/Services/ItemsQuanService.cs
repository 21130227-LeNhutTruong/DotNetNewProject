using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsQuanService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsQuan;
        private static ItemsQuanService instance;
        public ItemsQuanService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsQuan = database.GetCollection<ItemsDomain>("ItemsQuan");
        }

        public static ItemsQuanService GetInstance()
        {
            if (instance == null) instance = new ItemsQuanService();
            return instance;
        }

        public List<ItemsDomain> GetItemsQuan()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsQuan.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
    }
}
