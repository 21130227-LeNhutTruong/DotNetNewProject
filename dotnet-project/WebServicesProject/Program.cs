using CoreWCF;
using CoreWCF.Configuration;

var builder = WebApplication.CreateBuilder(args);

// Đăng ký dịch vụ CoreWCF
builder.Services.AddServiceModelServices();

var app = builder.Build();

if (builder.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage();
}

// Cấu hình dịch vụ CoreWCF
app.UseServiceModel(serviceBuilder =>
{
    serviceBuilder.AddService<ECommerceService>();
    serviceBuilder.AddServiceEndpoint<ECommerceService, IECommerceService>(new BasicHttpBinding(), "/ECommerceService.svc");
});

app.Use(async (context, next) =>
{
    try
    {
        await next.Invoke();
    }
    catch (Exception ex)
    {
        await context.Response.WriteAsync($"Error: {ex.Message}");
    }
});


app.Run(async (context) =>
{
    await context.Response.WriteAsync("Service is running...");
});

app.Run();
