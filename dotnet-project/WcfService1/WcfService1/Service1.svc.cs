using MongoDB.Bson;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using WcfService1.Models;
using WcfService1.Services;

namespace WcfService1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    public class Service1 : IService1
    {
        
        public List<Banner> GetAllBanners()
        {
            return BannerService.GetInstance().GetAllBanners();
        }

        public List<ItemsPopular> GetAllItemsPopulars()
        {
            return ItemsPopularService.GetInstance().GetAllItemsPopulars();
        }

        public bool AddUser(User user)
        {
            return UserService.GetInstance().AddUser(user);
        }

        public bool SendEmail(string to, string subject, string body)
        {
           return EmailService.GetInstance().SendEmail(to, subject, body);
        }

        //public CompositeType GetDataUsingDataContract(CompositeType composite)
        //{
        //    if (composite == null)
        //    {
        //        throw new ArgumentNullException("composite");
        //    }
        //    if (composite.BoolValue)
        //    {
        //        composite.StringValue += "Suffix";
        //    }
        //    return composite;
        //}
    }
}
