using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Collections.Generic;

namespace WcfService1.Models
{
    public class ItemsPopular
    {
        [BsonId]
        public ObjectId _id { get; set; }
        //public string id { get; set; }    
        public string description { get; set; }
        public double oldPrice { get; set; }
        public List<string> picUrl { get; set; }
        public string des { get; set; }
        public int price { get; set; }
        public double rating { get; set; }
        public int review { get; set; }
        public string title { get; set; }
    }
}
