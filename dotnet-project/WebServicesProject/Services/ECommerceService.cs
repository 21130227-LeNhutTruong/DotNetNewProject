using System.Collections.Generic;
using System.ServiceModel;

public class ECommerceService : IECommerceService
{
    private static readonly Dictionary<int, string> products = new Dictionary<int, string>();

    public string GetProductDetails(int productId)
    {
        return products.TryGetValue(productId, out var details) ? details : "Product not found";
    }

    public void AddProduct(string name, double price)
    {
        var newId = products.Count + 1;
        products[newId] = $"Name: {name}, Price: {price}";
    }
}
