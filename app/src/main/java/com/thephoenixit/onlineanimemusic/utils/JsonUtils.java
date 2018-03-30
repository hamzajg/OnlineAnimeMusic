package com.thephoenixit.onlineanimemusic.utils;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.thephoenixit.onlineanimemusic.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class JsonUtils {

    public JsonUtils(Context context)
    {
        _context = context;
    }

    public static int calculateTime(String s)
    {
        int i;
        int k;
        StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
        i = Integer.parseInt(stringtokenizer.nextToken());
        k = Integer.parseInt(stringtokenizer.nextToken());
        int j = k;
        try {
            StringTokenizer stringtokenizer1 = new StringTokenizer(s, ":");
            i = Integer.parseInt(stringtokenizer1.nextToken());
            j = Integer.parseInt(stringtokenizer1.nextToken());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1000 * (j + i * 60);
    }

    public static String getJSONString(String s)
    {
        HttpURLConnection httpurlconnection;
        String s1 = null;
        int i;
        try {
            httpurlconnection = (HttpURLConnection)(new URL(s)).openConnection();
            i = httpurlconnection.getResponseCode();
            InputStream inputstream;
            ByteArrayOutputStream bytearrayoutputstream;
            if(i != 200) {
                httpurlconnection.disconnect();
                return s1;
            } else {
                inputstream = httpurlconnection.getInputStream();
                bytearrayoutputstream = new ByteArrayOutputStream();
                int j = inputstream.read();
                if(j == -1) {
                    String s2 = new String(bytearrayoutputstream.toByteArray());
                    s1 = s2;
                } else {
                    bytearrayoutputstream.write(j);
                    s1 = null;
                    httpurlconnection.disconnect();
                }
                httpurlconnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s1;
    }

    public static int getProgressPercentage(long l, long l1)
    {
        Double.valueOf(0.0D);
        long l2 = (int)(l / 1000L);
        long l3 = (int)(l1 / 1000L);
        return Double.valueOf(100D * ((double)l2 / (double)l3)).intValue();
    }

    public static long getSeekFromPercentage(int i, long l)
    {
        return 1000L * (((long)(int)(l / 1000L) * (long)i) / 100L);
    }

    public static boolean isNetworkAvailable(Context context)
    {
        NetworkInfo networkinfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isConnected();
    }

    public static String milliSecondsToTimer(long l)
    {
        String s = "";
        int i = (int)(l / 0x36ee80L);
        int j = (int)(l % 0x36ee80L) / 60000;
        int k = (int)((l % 0x36ee80L % 60000L) / 1000L);
        if(i > 0)
            s = (new StringBuilder()).append(i).append(":").toString();
        String s1;
        if(k < 10)
            s1 = (new StringBuilder()).append("0").append(k).toString();
        else
            s1 = (new StringBuilder()).append("").append(k).toString();
        return (new StringBuilder()).append(s).append(j).append(":").append(s1).toString();
    }

    public void forceRTLIfSupported(Window window)
    {
        if(_context.getResources().getString(R.string.isRTL).equals("true") && android.os.Build.VERSION.SDK_INT >= 17)
            window.getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }

    public int getScreenWidth()
    {
        Display display = ((WindowManager)_context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        point.x = display.getWidth();
        point.y = display.getHeight();
        return point.x;
    }

    public int progressToTimer(int i, int j)
    {
        int k = j / 1000;
        return 1000 * (int)(((double)i / 100D) * (double)k);
    }

    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4F);
    private Context _context;
}
