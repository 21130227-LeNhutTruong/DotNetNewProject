using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

[Serializable]
public class Order : ISerializable
{
    public string Id { get; set; }
    public string Date { get; set; }
    public string Hoten { get; set; }
    public string Diachi { get; set; }
    public string Sdt { get; set; }
    public string Phuongthuc { get; set; }
    public double TotalAmount { get; set; }
    public string Status { get; set; }
    public List<Dictionary<string, object>> Items { get; set; }
    public string UserId { get; set; }
    public string UserName { get; set; }

    public Order()
    {
        // Constructor mặc định cần thiết cho Firestore hoặc khi sử dụng serialization
    }

    public Order(string userName, string userId, string id, string date, string hoten, string diachi, string sdt, string phuongthuc, double totalAmount, List<Dictionary<string, object>> items, string status)
    {
        UserName = userName;
        UserId = userId;
        Id = id;
        Date = date;
        Hoten = hoten;
        Diachi = diachi;
        Sdt = sdt;
        Phuongthuc = phuongthuc;
        TotalAmount = totalAmount;
        Items = items;
        Status = status;
    }

    protected Order(SerializationInfo info, StreamingContext context)
    {
        Id = info.GetString("Id");
        Date = info.GetString("Date");
        Hoten = info.GetString("Hoten");
        Diachi = info.GetString("Diachi");
        Sdt = info.GetString("Sdt");
        Phuongthuc = info.GetString("Phuongthuc");
        TotalAmount = info.GetDouble("TotalAmount");
        Status = info.GetString("Status");
        Items = (List<Dictionary<string, object>>)info.GetValue("Items", typeof(List<Dictionary<string, object>>));
        UserId = info.GetString("UserId");
        UserName = info.GetString("UserName");
    }

    public void GetObjectData(SerializationInfo info, StreamingContext context)
    {
        info.AddValue("Id", Id);
        info.AddValue("Date", Date);
        info.AddValue("Hoten", Hoten);
        info.AddValue("Diachi", Diachi);
        info.AddValue("Sdt", Sdt);
        info.AddValue("Phuongthuc", Phuongthuc);
        info.AddValue("TotalAmount", TotalAmount);
        info.AddValue("Status", Status);
        info.AddValue("Items", Items);
        info.AddValue("UserId", UserId);
        info.AddValue("UserName", UserName);
    }
}
