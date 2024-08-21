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

            var filter = Builders<Cart>.Filter.And(
                Builders<Cart>.Filter.Eq(c => c._id, cartObjectId),
                Builders<Cart>.Filter.ElemMatch(c => c.products, p => p._id == productObjectId)
            );

            var existingCart = _cart.Find(filter).FirstOrDefault();

            if (existingCart != null)
            {
                int quantityStore = 0;
                for(int i = 0; i < existingCart.products.Count; i++)
                {
                    if (productObjectId == existingCart.products[i]._id)
                    {
                        quantityStore = existingCart.products[i].quantity;
                        break;
                    }
                }
                return UpdateCartQuantity(id, idProduct, quantityStore + quantity);
            }

            var newProduct = new ProductBuy
            {
                _id = productObjectId,
                quantity = quantity,
                type = type
            };

            var update = Builders<Cart>.Update.Push("products", newProduct);

            var result = _cart.UpdateOne(Builders<Cart>.Filter.Eq(c => c._id, cartObjectId), update);

            if (result.ModifiedCount > 0)
            {
                return true; 
            }
            else
            {
                return false;
            }
        }

        public bool AddNewCart(string idUser, string idProduct, string type)
        {
            try
            {
                var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idUser);
                var productObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idProduct);

                List<ProductBuy> products = new List<ProductBuy>();
                ProductBuy productBuy = new ProductBuy{
                    _id = productObjectId,
                    quantity = 1,
                    type = type
                };

                products.Add(productBuy);

                var newCart = new Cart
                {
                    _id = ObjectId.GenerateNewId(),
                    userId = userObjectId,
                    products = products
                };
                _cart.InsertOne(newCart);
                return true;
            }
            catch (Exception ex)
            {
                return false;
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

        public bool DeleteCart(string id)
        {
            var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);

            var filter = Builders<Cart>.Filter.Eq(c => c.userId, userObjectId);

            var result = _cart.DeleteOne(filter);

            if (result.DeletedCount > 0)
            {
                return true; 
            }
            else
            {
                return false; 
            }
        }


    }
}