using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Runtime.Serialization;

namespace WcfService1.Models
{
    public class Bill
    {
        [BsonId]
        [DataMember]
        public ObjectId _id { get; set; }

        [DataMember]
        public DateTime date { get; set; }

        [DataMember]
        public string address { get; set; }

        [DataMember]
        public string fullName { get; set; }

        [DataMember]
        public string payment { get; set; }
        [DataMember]
        public string status { get; set; }

        [DataMember]
        public string phone { get; set; }

        [DataMember]
        public int totalAmount { get; set; }

        [DataMember]
        public ObjectId userId { get; set; }
    }
}
