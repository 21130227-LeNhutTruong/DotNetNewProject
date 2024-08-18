using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class UserService
    {
        private static UserService instance;
        private readonly IMongoCollection<User> _user;

        public UserService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _user = database.GetCollection<User>("User");
        }
        public static UserService GetInstance()
        {
            if(instance == null) instance = new UserService();
            return instance; 
        }

        public bool AddUser(User user)
        {
            try
            {
                _user.InsertOne(user);
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }
    }
}