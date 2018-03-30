package com.thephoenixit.onlineanimemusic.views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;

import com.thephoenixit.onlineanimemusic.R;
import com.thephoenixit.onlineanimemusic.utils.Constant;
import com.thephoenixit.onlineanimemusic.utils.JsonUtils;

public class SplashActivity extends AppCompatActivity {


    private void openMainActivity()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setStatusColor();
        JsonUtils jsonutils;
        float f;
        try
        {
            Constant.isFromPush = Boolean.valueOf(getIntent().getExtras().getBoolean("ispushnoti", false));
            Constant.pushID = getIntent().getExtras().getString("noti_nid");
        }
        catch(Exception exception)
        {
            Constant.isFromPush = Boolean.valueOf(false);
        }
        try
        {
            Constant.isFromNoti = Boolean.valueOf(getIntent().getExtras().getBoolean("isnoti", false));
        }
        catch(Exception exception1)
        {
            Constant.isFromNoti = Boolean.valueOf(false);
        }
        jsonutils = new JsonUtils(this);
        f = TypedValue.applyDimension(1, 3F, getResources().getDisplayMetrics());
        Constant.columnWidth = (int)(((float)jsonutils.getScreenWidth() - 4F * f) / 3F);
        if(!Constant.isFromNoti.booleanValue())
        {
            (new Handler()).postDelayed(new Runnable() {

                public void run()
                {
                    openMainActivity();
                }
            }, 2000L);
            return;
        } else
        {
            openMainActivity();
            return;
        }
    }

    public void setStatusColor()
    {
        if(android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = getWindow();
            window.addFlags(Window.FEATURE_ACTION_BAR);
            window.setStatusBarColor(getResources().getColor(R.color.statusBar));
        }
    }
}
