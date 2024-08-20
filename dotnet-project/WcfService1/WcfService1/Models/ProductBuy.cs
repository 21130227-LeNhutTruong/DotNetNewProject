using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace WcfService1.Models
{
    public class ProductBuy 
    {
        [BsonId]
        [DataMember]
        public ObjectId _id {  get; set; }

        [DataMember]
        public int quantity { get; set; }
        [DataMember]
        public string type { get; set; }


    }
}