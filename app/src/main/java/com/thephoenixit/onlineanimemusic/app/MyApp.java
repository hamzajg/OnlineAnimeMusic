package com.thephoenixit.onlineanimemusic.app;

import android.app.Application;

import com.onesignal.OneSignal;
import com.thephoenixit.onlineanimemusic.utils.DBHelper;

import java.io.IOException;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class MyApp extends Application {
    public void onCreate()
    {
        super.onCreate();
        ViewPump.init(ViewPump.builder().addInterceptor(new CalligraphyInterceptor(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/futura_med.ttf").setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath).build())).build());
        OneSignal.initWithContext(this, "878107db-b014-409e-89e3-267920f0d972");
        OneSignal.getNotifications().requestPermission(true, new kotlin.coroutines.Continuation<Boolean>() {
            @Override
            public kotlin.coroutines.CoroutineContext getContext() {
                return kotlin.coroutines.EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(Object result) {
            }
        });
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
