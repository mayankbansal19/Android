package com.example.db;

public class Category
{
    private long   id;
    private String title;
    private String description;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public String getTitle()
    {
        return title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

//    // Will be used by the ArrayAdapter in the ListView
//    @Override
//    public String toString()
//    {
//        return comment;
//    }

}
