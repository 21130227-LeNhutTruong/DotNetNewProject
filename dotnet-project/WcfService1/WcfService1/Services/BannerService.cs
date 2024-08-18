using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class BannerService
    {
        private readonly IMongoCollection<Banner> _banner;
        private static BannerService instance;

        public  BannerService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _banner = database.GetCollection<Banner>("Banner");
        }

        public static BannerService GetInstance()
        {
            if(instance == null) instance = new BannerService();
            return instance;
        }

        public List<Banner> GetAllBanners()
        {
            var filter = Builders<Banner>.Filter.Empty;
            var result = _banner.Find(filter).ToList();
            return result;
        }
    }
}