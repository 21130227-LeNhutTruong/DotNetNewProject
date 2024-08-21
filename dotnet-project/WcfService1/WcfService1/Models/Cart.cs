using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace WcfService1.Models
{
    [DataContract]
    public class Cart
    {
        [BsonId]
        [DataMember]
        public ObjectId _id {  get; set; }
        
        [DataMember]
        public ObjectId userId { get; set; }

        [DataMember]
        public List<ProductBuy> products { get; set; }



    }
}