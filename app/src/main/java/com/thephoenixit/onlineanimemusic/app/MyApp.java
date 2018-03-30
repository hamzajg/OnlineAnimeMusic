package com.thephoenixit.onlineanimemusic.app;

import android.app.Application;

import com.onesignal.OneSignal;
import com.thephoenixit.onlineanimemusic.utils.DBHelper;

import java.io.IOException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp extends Application {
    public void onCreate()
    {
        super.onCreate();
        CalligraphyConfig.initDefault((new uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder()).setDefaultFontPath("fonts/futura_med.ttf").setFontAttrId(0x7f010003).build());
        OneSignal.startInit(getApplicationContext()).init();
        DBHelper dbhelper = new DBHelper(getApplicationContext());
        try
        {
            dbhelper.createDataBase();
            return;
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }
}
