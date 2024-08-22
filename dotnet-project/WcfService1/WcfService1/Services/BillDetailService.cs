using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class BillDetailService
    {
        private static BillDetailService instance;
        private readonly IMongoCollection<BillDetail> _billDetail;
        public BillDetailService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _billDetail = database.GetCollection<BillDetail>("BillDetail");
        }

        public static BillDetailService GetInstance()
        {
            if(instance == null) instance = new BillDetailService();
            return instance;
        }

        public BillDetail GetBillDetail(string idUser)
        {
            var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idUser);

            var filter = Builders<BillDetail>.Filter.Eq(b => b.billId, userObjectId);

            var result = _billDetail.Find(filter).FirstOrDefault();

            return result;
        }

        public bool DeleteBillDetail(string idUser)
        {

            var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idUser);

            var filter = Builders<BillDetail>.Filter.Eq(b => b.billId, userObjectId);

            var result = _billDetail.DeleteOne(filter);

            if (result.DeletedCount > 0)
            {
                return true;
            }
            else
            {
                return false;
            }

        }

        public bool AddBillDetail(string idUser)
        {
            try
            {
                var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idUser);

                Bill bill = BillService.GetInstance().GetNewBillByUser(idUser);

                var productDetails = new List<ProductBuy>();

                var billDetail = new BillDetail
                {
                    billId = bill._id, 
                    products = productDetails 
                };

                
                _billDetail.InsertOne(billDetail);

                return true; 
            }
            catch (Exception ex)
            {
                return false; 
            }
        }

        public bool AddProductInBillDetail(string idUser, string idProduct, int quantity, string type)
        {
            try
            {
                var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idUser);
                var productObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idProduct);

                Bill bill = BillService.GetInstance().GetNewBillByUser(idUser);


                var filter = Builders<BillDetail>.Filter.Eq(bd => bd.billId, bill._id);

                var newProduct = new ProductBuy
                {
                    _id = productObjectId,
                    quantity = quantity,
                    type = type
                };

                var update = Builders<BillDetail>.Update.Push(bd => bd.products, newProduct);

                var result = _billDetail.UpdateOne(filter, update);

                return result.ModifiedCount > 0;
            }
            catch (Exception ex)
            {
                return false;
            }
        }


    }
}