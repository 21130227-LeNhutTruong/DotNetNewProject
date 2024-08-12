using System.ServiceModel;

[ServiceContract]
public interface IECommerceService
{
    [OperationContract]
    string GetProductDetails(int productId);

    [OperationContract]
    void AddProduct(string name, double price);

    // Thêm các phương thức khác cần thiết cho dịch vụ
}
