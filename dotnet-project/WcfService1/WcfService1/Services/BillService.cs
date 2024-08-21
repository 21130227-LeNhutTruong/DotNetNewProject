using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.CodeDom;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WcfService1.Models;

namespace WcfService1.Services
{
    public class BillService
    {
        private static BillService instance;
        private readonly IMongoCollection<Bill> _bill;

        public BillService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("shop");
            _bill = database.GetCollection<Bill>("Bills");
        }

        public static BillService GetInstance()
        {
            if(instance == null) instance = new BillService();
            return instance;
        }

        public Bill GetBillById(string id)
        {
            var billObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);

            var filter = Builders<Bill>.Filter.Eq(b => b._id, billObjectId);

            var result = _bill.Find(filter).FirstOrDefault();

            return result;
        }
        public List<Bill> GetAllBill()
        {

            var filter = Builders<Bill>.Filter.Empty;

            var result = _bill.Find(filter).ToList();

            return result;
        }

        public List<Bill> GetBillByUser(string idUser)
        {
            var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idUser);

            var filter = Builders<Bill>.Filter.Eq(b => b.userId, userObjectId);

            var result = _bill.Find(filter).ToList();
            return result;
        }

        public bool UpdateStatusBill(string id, string status)
        {
            var billObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);

            var filter = Builders<Bill>.Filter.Eq(b => b._id, billObjectId);

            var update = Builders<Bill>.Update.Set("status", status);

            var result = _bill.UpdateOne(filter, update);

            if (result.ModifiedCount == 0)
            {
                return false;
            }
            else
            {
                return true;
            }

        }

        public bool AddBill(string address, string fullName, string payment, string phone, int totalAmount, string idUser, string idProduct, int quanity, string type)
        {

            var userObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(idUser);

            var bill = new Bill
            {
                date = DateTime.UtcNow, 
                address = address,
                fullName = fullName,
                payment = payment,
                status = "Đang xử lý",
                phone = phone,
                totalAmount = totalAmount,
                userId = userObjectId
            };

            try
            {
                _bill.InsertOne(bill);
                BillDetailService.GetInstance().AddBillDetail(idUser, idProduct, quanity, type);

                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }

        public bool DeleteBill(string id)
        {
            var billObjectId = ObjectIdService.GetInstance().ChangeIdStringToObjectId(id);

            var filter = Builders<Bill>.Filter.Eq(b => b._id, billObjectId);

            var result = _bill.DeleteOne(filter);

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