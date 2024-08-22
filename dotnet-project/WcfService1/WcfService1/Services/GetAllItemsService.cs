using MongoDB.Bson;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class GetAllItemsService
    {
        private readonly IMongoDatabase _database;

        public GetAllItemsService(string connectionString, string databaseName)
        {
            var client = new MongoClient(connectionString);
            _database = client.GetDatabase(databaseName);
        }

        public List<ItemsDomain> GetAllItems(IEnumerable<string> collectionNames)
        {
            var items = new List<ItemsDomain>();

            foreach (var collectionName in collectionNames)
            {
                var collection = _database.GetCollection<ItemsDomain>(collectionName);
                var filter = Builders<ItemsDomain>.Filter.Empty;
                var projection = Builders<ItemsDomain>.Projection.Exclude("id");

                var allItems = collection.Find(filter)
                                         .Project<ItemsDomain>(projection)
                                         .ToList();

                items.AddRange(allItems);
            }

            return items;
        }
        public List<ItemsDomain> SearchItems(IEnumerable<string> collectionNames, string searchQuery)
        {
            var items = new List<ItemsDomain>();

            // Làm sạch và chuẩn bị tìm kiếm
            var sanitizedQuery = string.IsNullOrWhiteSpace(searchQuery)
                ? string.Empty
                : Regex.Escape(searchQuery);

            foreach (var collectionName in collectionNames)
            {
                var collection = _database.GetCollection<ItemsDomain>(collectionName);

                // Tạo bộ lọc tìm kiếm dựa trên từ khóa
                var filter = string.IsNullOrWhiteSpace(searchQuery)
                    ? Builders<ItemsDomain>.Filter.Empty
                    : Builders<ItemsDomain>.Filter.Regex("title", new BsonRegularExpression(sanitizedQuery, "i"));
                var projection = Builders<ItemsDomain>.Projection.Exclude("id");
                // Lấy dữ liệu từ collection với filter đã chỉ định
                var searchResults = collection.Find(filter).Project<ItemsDomain>(projection)
                                         .ToList();

                items.AddRange(searchResults);
            }

            return items;
        }
    }
}
