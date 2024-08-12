using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

[Serializable]
public class Products : ISerializable
{
    public string Id { get; set; }
    public string Title { get; set; }
    public string Description { get; set; }
    public List<string> PicUrl { get; set; }
    public string Des { get; set; }
    public double Price { get; set; }
    public double OldPrice { get; set; }
    public int Review { get; set; }
    public double Rating { get; set; }
    public int NumberInCart { get; set; }

    public Products()
    {
    }

    public Products(string des, string id, string title, string description, List<string> picUrl, double price, double oldPrice, int review, double rating)
    {
        Des = des;
        Id = id;
        Title = title;
        Description = description;
        PicUrl = picUrl;
        Price = price;
        OldPrice = oldPrice;
        Review = review;
        Rating = rating;
    }

    protected Products(SerializationInfo info, StreamingContext context)
    {
        Id = info.GetString("Id");
        Title = info.GetString("Title");
        Description = info.GetString("Description");
        PicUrl = (List<string>)info.GetValue("PicUrl", typeof(List<string>));
        Des = info.GetString("Des");
        Price = info.GetDouble("Price");
        OldPrice = info.GetDouble("OldPrice");
        Review = info.GetInt32("Review");
        Rating = info.GetDouble("Rating");
        NumberInCart = info.GetInt32("NumberInCart");
    }

    public void GetObjectData(SerializationInfo info, StreamingContext context)
    {
        info.AddValue("Id", Id);
        info.AddValue("Title", Title);
        info.AddValue("Description", Description);
        info.AddValue("PicUrl", PicUrl);
        info.AddValue("Des", Des);
        info.AddValue("Price", Price);
        info.AddValue("OldPrice", OldPrice);
        info.AddValue("Review", Review);
        info.AddValue("Rating", Rating);
        info.AddValue("NumberInCart", NumberInCart);
    }
}
