using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class SliderItemService
    {
        private readonly IMongoCollection<SliderItems> _sliderItem;
        private static SliderItemService instance;

        public SliderItemService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _sliderItem = database.GetCollection<SliderItems>("SliderItems");
        }

        public static SliderItemService GetInstance()
        {
            if(instance == null) instance = new SliderItemService();
            return instance;
        }

        public List<SliderItems> GetSliderItems()
        {
            var filter = Builders<SliderItems>.Filter.Empty;
            var result = _sliderItem.Find(filter).ToList();
            return result;
        }
    }
}