using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class CartService
    {
        private static CartService instance;
        private readonly IMongoCollection<Cart> _cart;

        public CartService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _cart = database.GetCollection<Cart>("Carts");
        }
        public static CartService GetInstance()
        {
            if(instance == null) instance = new CartService();  
            return instance; 
        }

        public Cart GetCartByUser(string id)
        {
            var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);

            var filter = Builders<Cart>.Filter.Eq(c => c.userId, userObjectId);

            var result = _cart.Find<Cart>(filter).ToList();

            if (result.Count == 1) return result[0];

            return null;
        }

        public bool UpdateCartQuantity(string id, string idProduct, int quantity)
        {
            var cartObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);

            var productObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idProduct);

            var filter = Builders<Cart>.Filter.And(
                Builders<Cart>.Filter.Eq(c => c._id, cartObjectId),
                Builders<Cart>.Filter.ElemMatch(c => c.products, p => p._id == productObjectId)
            );

            var update = Builders<Cart>.Update.Set("products.$.quantity", quantity); 

            var result = _cart.UpdateOne(filter, update);

            if (result.ModifiedCount == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }


        public bool AddCart(string id, string idProduct, int quantity, string type)
        {
            var cartObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var productObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idProduct);

            var filter = Builders<Cart>.Filter.Eq(c => c._id, cartObjectId);

            var newProduct = new ProductBuy
            {
                _id = productObjectId,
                quantity = quantity,
                type = type
            };

            var update = Builders<Cart>.Update.Push("products", newProduct);

            var result = _cart.UpdateOne(filter, update);

            if (result.MatchedCount == 0)
            {
                return false; 
            }
            else if (result.ModifiedCount == 0)
            {
                return false; 
            }
            else
            {
                return true;
            }

        }

        public bool RemoveCart(string id, string idProduct)
        {
            var cartObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);
            var productObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idProduct);

            var filter = Builders<Cart>.Filter.Eq(c => c._id, cartObjectId);

            var update = Builders<Cart>.Update.PullFilter(c => c.products, p => p._id == productObjectId);

            var result = _cart.UpdateOne(filter, update);

            if (result.MatchedCount == 0)
            {
                return false; 
            }
            else if (result.ModifiedCount == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

    }
}