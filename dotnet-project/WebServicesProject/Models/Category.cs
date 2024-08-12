using System;
using System.Collections.Generic;

public class Category
{
    public string Title { get; set; }
    public int Id { get; set; }
    public List<string> PicUrl { get; set; }

    public Category()
    {
    }

    public Category(string title, int id, List<string> picUrl)
    {
        Title = title;
        Id = id;
        PicUrl = picUrl;
    }
}
