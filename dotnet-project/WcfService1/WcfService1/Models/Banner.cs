using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfService1.Models
{
    public class Banner
    {
        [BsonId]
        public ObjectId Id { get; set; }
        public string url { get; set; }
    }
}