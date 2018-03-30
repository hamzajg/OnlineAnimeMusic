package com.thephoenixit.onlineanimemusic.utils;

public class ItemAbout {

    public ItemAbout(String s, String s1, String s2, String s3, String s4, String s5, String s6,
                     String s7, String s8, String s9)
    {
        app_name = s;
        app_logo = s1;
        app_desc = s2;
        app_version = s3;
        author = s4;
        contact = s5;
        email = s6;
        website = s7;
        privacy = s8;
        developedby = s9;
    }

    public String getAppDesc()
    {
        return app_desc;
    }

    public String getAppLogo()
    {
        return app_logo;
    }

    public String getAppName()
    {
        return app_name;
    }

    public String getAppVersion()
    {
        return app_version;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getContact()
    {
        return contact;
    }

    public String getDevelopedby()
    {
        return developedby;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPrivacy()
    {
        return privacy;
    }

    public String getWebsite()
    {
        return website;
    }

    private String app_desc;
    private String app_logo;
    private String app_name;
    private String app_version;
    private String author;
    private String contact;
    private String developedby;
    private String email;
    private String privacy;
    private String website;
}
