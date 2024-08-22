using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class ItemsQuanNuService
    {
        private readonly IMongoCollection<ItemsDomain> _itemsQuanNu;
        private static ItemsQuanNuService instance;
        public ItemsQuanNuService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _itemsQuanNu = database.GetCollection<ItemsDomain>("ItemsQuanNu");
        }

        public static ItemsQuanNuService GetInstance()
        {
            if (instance == null) instance = new ItemsQuanNuService();
            return instance;
        }

        public List<ItemsDomain> GetItemsQuanNu()
        {
            var filter = Builders<ItemsDomain>.Filter.Empty;
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsQuanNu.Find(filter).Project<ItemsDomain>(projection).ToList();
            return result;
        }
        public ItemsDomain GetItemsQuanNuById(string id)
        {
            var objectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var filter = Builders<ItemsDomain>.Filter.Eq(ip => ip._id, objectId);
            var projection = Builders<ItemsDomain>.Projection.Exclude("id");
            var result = _itemsQuanNu.Find(filter).Project<ItemsDomain>(projection).FirstOrDefault();
            return result;
        }
    }
}

