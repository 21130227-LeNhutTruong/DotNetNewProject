using MongoDB.Bson.Serialization.Attributes;
using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace WcfService1.Models
{
    public class BillDetail
    {
        [BsonId]
        [DataMember]
        public ObjectId _id { get; set; }

        [DataMember]
        public ObjectId userId { get; set; }

        [DataMember]
        public List<ProductBuy> products { get; set; }
    }
}