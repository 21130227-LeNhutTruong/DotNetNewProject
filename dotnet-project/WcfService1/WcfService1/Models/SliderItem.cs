using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfService1.Models
{
    public class SliderItems
    {
        [BsonId]
        public ObjectId _id { get; set; }
        //public string id { get; set; }    
        public string description { get; set; }
        public List<string> picUrl { get; set; }
        public int price { get; set; }
        public string title { get; set; }
    }
}