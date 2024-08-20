using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace WcfService1.Models
{
    [DataContract(Namespace = "http://tempuri.org/")]
    public class User
    {

        [BsonId]
        [DataMember]
        public ObjectId _id { get; set; }
        [DataMember]
        public string email { get; set; }
        [DataMember]
        public string pass { get; set; }
        [DataMember]
        public string username { get; set; }
        [DataMember]
        public string name { get; set; }
        [DataMember]
        public int age { get; set; }
        [DataMember]
        public string avatar { get; set; }
        [DataMember]
        public int sex { get; set; }
        [DataMember]
        public int role { get; set; }

    }
}