package com.thephoenixit.onlineanimemusic.utils;

import android.graphics.Bitmap;

public class ItemSong {
    public ItemSong(String s, String s1, String s2, Bitmap bitmap, String s3, String s4)
    {
        id = s;
        artist = s1;
        Mp3Url = s2;
        image = bitmap;
        Mp3Name = s3;
        Duration = s4;
    }

    public ItemSong(String s, String s1, String s2, String s3, String s4, String s5, String s6,
                    String s7, String s8, String s9)
    {
        id = s;
        CategoryId = s1;
        CategoryName = s2;
        artist = s3;
        Mp3Url = s4;
        Imagebig = s5;
        Imagesmall = s6;
        Mp3Name = s7;
        Duration = s8;
        Description = s9;
    }

    public String getArtist()
    {
        return artist;
    }

    public Bitmap getBitmap()
    {
        return image;
    }

    public String getCategoryId()
    {
        return CategoryId;
    }

    public String getCategoryName()
    {
        return CategoryName;
    }

    public String getDescription()
    {
        return Description;
    }

    public String getDuration()
    {
        return Duration;
    }

    public String getId()
    {
        return id;
    }

    public String getImageBig()
    {
        return Imagebig;
    }

    public String getImageSmall()
    {
        return Imagesmall;
    }

    public String getMp3Name()
    {
        return Mp3Name;
    }

    public String getMp3Url()
    {
        return Mp3Url;
    }

    public void setCategoryId(String s)
    {
        CategoryId = s;
    }

    public void setCategoryName(String s)
    {
        CategoryName = s;
    }

    public void setDescription(String s)
    {
        Description = s;
    }

    public void setDuration(String s)
    {
        Duration = s;
    }

    public void setId(String s)
    {
        id = s;
    }

    public void setImageBig(String s)
    {
        Imagebig = s;
    }

    public void setImageSmall(String s)
    {
        Imagesmall = s;
    }

    public void setMp3Name(String s)
    {
        Mp3Name = s;
    }

    public void setMp3Url(String s)
    {
        Mp3Url = s;
    }

    private String CategoryId;
    private String CategoryName;
    private String Description;
    private String Duration;
    private String Imagebig;
    private String Imagesmall;
    private String Mp3Name;
    private String Mp3Url;
    private String artist;
    private String id;
    private Bitmap image;
}
