using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfService1.Models
{
    public class User
    {
        [BsonId]
        public ObjectId _id { get; set; }
        public string email { get; set; }
        public string pass { get; set; }
        public string name { get; set; }
        public int age { get; set; }
        public string avatar { get; set; }
        public int sex { get; set; }
        public int role { get; set; }

    }
}