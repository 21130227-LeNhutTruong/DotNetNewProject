using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace WcfService1.Models
{
    public class ItemsDomain
    {
        [BsonId]
        [DataMember]
        public ObjectId _id { get; set; }
        [DataMember]
        public string description { get; set; }
        [DataMember]
        public double oldPrice { get; set; }
        [DataMember]
        public List<string> picUrl { get; set; }
        [DataMember]
        public string des { get; set; }
        [DataMember]
        public int price { get; set; }
        [DataMember]
        public double rating { get; set; }
        [DataMember]
        public int review { get; set; }
        [DataMember]
        public string title { get; set; }
    }
}
