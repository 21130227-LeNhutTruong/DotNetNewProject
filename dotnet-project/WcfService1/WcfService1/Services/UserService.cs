using MongoDB.Bson;
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
            if (instance == null) instance = new UserService();
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
        public bool Register(User user)
        {
            try
            {
                if(!isExistUser(user.email))
                {
                    _user.InsertOne(user);
                    return true;
                }
                return false;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        public User CheckLogin(string email, string pass)
        {
            var filter = Builders<User>.Filter.Eq(u => u.email, email) & Builders<User>.Filter.Eq(u => u.pass, pass);
            var result = _user.Find(filter).ToList();
            if (result.Count == 1) return result[0];
            return null;
        }

        public User GetUserById(string id)
        {
            var objectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var filter = Builders<User>.Filter.Eq(u => u._id, objectId);

            var result = _user.Find<User>(filter).ToList();

            if (result.Count == 1) return result[0];
            return null;
        }

        public bool isExistUser(string email)
        {
            var filter = Builders<User>.Filter.Eq(u => u.email, email);
            var result = _user.Find<User>(filter).ToList();
            if (result.Count > 0) return true;
            return false;
        }

        
    }
}