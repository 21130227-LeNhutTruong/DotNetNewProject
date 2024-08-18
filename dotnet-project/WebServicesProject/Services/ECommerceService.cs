public class ECommerceService : IECommerceService
{
    public string GetProductDetails(int productId)
    {
        return $"Product details for product id {productId}";
    }
}
