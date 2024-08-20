using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsQuanNamService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsQuanNam;
        private static ItemsQuanNamService instance;
        public ItemsQuanNamService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsQuanNam = database.GetCollection<ItemsDomain>("ItemsQuanNam");
        }

        public static ItemsQuanNamService GetInstance()
        {
            if (instance == null) instance = new ItemsQuanNamService();
            return instance;
        }

        public List<ItemsDomain> GetItemsQuanNam()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsQuanNam.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
    }
}
