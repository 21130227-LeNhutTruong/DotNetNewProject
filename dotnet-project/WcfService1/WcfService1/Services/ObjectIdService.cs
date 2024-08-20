using MongoDB.Bson;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfService1.Services
{
    public class ObjectIdService
    {
        private static ObjectIdService instance;
        public static ObjectIdService GetInstance()
        {
            if(instance == null) instance = new ObjectIdService();  
            return instance;
        }

        public ObjectId ChangeIdStringToObjectId(string id)
        {
            string a = id.Substring(0, 10);
            string c = id.Substring(id.Length - 9);
            string b = id.Substring(10, id.Length - 19);

            string partA = int.Parse(a).ToString("x8"); 
            string partB = int.Parse(b).ToString("x8");
            string partC = int.Parse(c).ToString("x8");

            string objectIdString = partA + partB + partC;

            return new ObjectId(objectIdString);
        }

        
    }
}