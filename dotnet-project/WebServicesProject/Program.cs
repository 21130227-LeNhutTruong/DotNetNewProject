using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System.ServiceModel;

var builder = WebApplication.CreateBuilder(args);

// Thêm dịch vụ SOAP
builder.Services.AddServiceModelServices();

var app = builder.Build();

// Cấu hình endpoint SOAP
app.UseEndpoints(endpoints =>
{
    endpoints.UseServiceModel(serviceBuilder =>
    {
        serviceBuilder.AddService<ECommerceService>();
        serviceBuilder.AddServiceEndpoint<ECommerceService, IECommerceService>(new BasicHttpBinding(), "/ECommerceService");
    });
});

app.Run();
