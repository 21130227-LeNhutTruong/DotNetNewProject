using System.ServiceModel;

[ServiceContract]
public interface IECommerceService
{
    [OperationContract]
    string GetProductDetails(int productId);
}
