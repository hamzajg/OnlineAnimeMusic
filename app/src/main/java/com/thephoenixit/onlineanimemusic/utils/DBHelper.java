package com.thephoenixit.onlineanimemusic.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class DBHelper extends SQLiteOpenHelper
{

    public DBHelper(Context context1)
    {
        super(context1, DB_NAME, null, 1);
        outFileName = "";
        context = context1;
        DB_PATH = (new StringBuilder()).append("/data/data/").append(context1.getPackageName()).append("/").append("databases/").toString();
    }

    private boolean checkDataBase()
    {
        return (new File((new StringBuilder()).append(DB_PATH).append(DB_NAME).toString())).exists();
    }

    private void copyDataBase()
            throws IOException
    {
        InputStream inputstream = context.getAssets().open(DB_NAME);
        FileOutputStream fileoutputstream = new FileOutputStream((new StringBuilder()).append(DB_PATH).append(DB_NAME).toString());
        byte abyte0[] = new byte[1024];
        do
        {
            int i = inputstream.read(abyte0);
            if(i > 0)
            {
                fileoutputstream.write(abyte0, 0, i);
            } else
            {
                fileoutputstream.flush();
                fileoutputstream.close();
                inputstream.close();
                return;
            }
        } while(true);
    }

    public void addToFav(ItemSong itemsong)
    {
        String s = DatabaseUtils.sqlEscapeString(itemsong.getDescription());
        String s1 = itemsong.getMp3Name().replace("'", "%27");
        String s2 = itemsong.getCategoryName().replace("'", "%27");
        dml((new StringBuilder()).append("insert into song (sid,title,desc,artist,duration,url,image,image_small,cid,cname) values ('").append(itemsong.getId()).append("', '").append(s1).append("', ").append(s).append(", '").append(itemsong.getArtist()).append("', '").append(itemsong.getDuration()).append("', '").append(itemsong.getMp3Url()).append("', '").append(itemsong.getImageBig()).append("', '").append(itemsong.getImageSmall()).append("', '").append(itemsong.getCategoryId()).append("', '").append(s2).append("')").toString());
    }

    public void addToRecent(ItemSong itemsong)
    {
        if(checkRecent(itemsong.getId()).booleanValue())
            dml((new StringBuilder()).append("delete from recent where sid = '").append(itemsong.getId()).append("'").toString());
        String s = DatabaseUtils.sqlEscapeString(itemsong.getDescription());
        String s1 = itemsong.getMp3Name().replace("'", "%27");
        String s2 = itemsong.getCategoryName().replace("'", "%27");
        dml((new StringBuilder()).append("insert into recent (sid,title,desc,artist,duration,url,image,image_small,cid,cname) values ('").append(itemsong.getId()).append("', '").append(s1).append("', ").append(s).append(", '").append(itemsong.getArtist()).append("', '").append(itemsong.getDuration()).append("', '").append(itemsong.getMp3Url()).append("', '").append(itemsong.getImageBig()).append("', '").append(itemsong.getImageSmall()).append("', '").append(itemsong.getCategoryId()).append("', '").append(s2).append("')").toString());
    }

    public Boolean checkFav(String s)
    {
        Cursor cursor = getData((new StringBuilder()).append("select * from song where sid = '").append(s).append("'").toString());
        if(cursor != null && cursor.getCount() > 0)
            return Boolean.valueOf(true);
        else
            return Boolean.valueOf(false);
    }

    public Boolean checkRecent(String s)
    {
        Cursor cursor = getData((new StringBuilder()).append("select * from recent where sid = '").append(s).append("'").toString());
        if(cursor != null && cursor.getCount() > 0)
            return Boolean.valueOf(true);
        else
            return Boolean.valueOf(false);
    }

    public void createDataBase()
            throws IOException
    {
        if(!checkDataBase())
        {
            getReadableDatabase();
            copyDataBase();
        }
    }

    public void dml(String s)
    {
        String s1 = (new StringBuilder()).append(DB_PATH).append(DB_NAME).toString();
        if(db == null)
            db = SQLiteDatabase.openDatabase(s1, null, 0);
        try
        {
            db.execSQL(s);
            return;
        }
        catch(Exception exception)
        {
            Log.e("Error", exception.toString());
        }
    }

    public Cursor getData(String s)
    {
        String s1 = (new StringBuilder()).append(DB_PATH).append(DB_NAME).toString();
        Cursor cursor;
        try
        {
            db = SQLiteDatabase.openDatabase(s1, null, 0);
            cursor = db.rawQuery(s, null);
        }
        catch(Exception exception)
        {
            Log.e("Err", exception.toString());
            return null;
        }
        return cursor;
    }

    public ArrayList loadData()
    {
        ArrayList arraylist = new ArrayList();
        Cursor cursor = getData("select * from song");
        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++)
            {
                String s = cursor.getString(cursor.getColumnIndex("sid"));
                String s1 = cursor.getString(cursor.getColumnIndex("cid"));
                String s2 = cursor.getString(cursor.getColumnIndex("cname")).replace("%27", "'");
                String s3 = cursor.getString(cursor.getColumnIndex("artist"));
                String s4 = cursor.getString(cursor.getColumnIndex("title")).replace("%27", "'");
                String s5 = cursor.getString(cursor.getColumnIndex("url"));
                String s6 = cursor.getString(cursor.getColumnIndex("desc"));
                String s7 = cursor.getString(cursor.getColumnIndex("duration"));
                arraylist.add(new ItemSong(s, s1, s2, s3, s5, cursor.getString(cursor.getColumnIndex("image")), cursor.getString(cursor.getColumnIndex("image_small")), s4, s7, s6));
                cursor.moveToNext();
            }

        }
        return arraylist;
    }

    public ArrayList loadDataRecent()
    {
        ArrayList arraylist = new ArrayList();
        Cursor cursor = getData("select * from recent");
        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++)
            {
                String s = cursor.getString(cursor.getColumnIndex("sid"));
                String s1 = cursor.getString(cursor.getColumnIndex("cid"));
                String s2 = cursor.getString(cursor.getColumnIndex("cname")).replace("%27", "'");
                String s3 = cursor.getString(cursor.getColumnIndex("artist"));
                String s4 = cursor.getString(cursor.getColumnIndex("title")).replace("%27", "'");
                String s5 = cursor.getString(cursor.getColumnIndex("url"));
                String s6 = cursor.getString(cursor.getColumnIndex("desc"));
                String s7 = cursor.getString(cursor.getColumnIndex("duration"));
                arraylist.add(new ItemSong(s, s1, s2, s3, s5, cursor.getString(cursor.getColumnIndex("image")), cursor.getString(cursor.getColumnIndex("image_small")), s4, s7, s6));
                cursor.moveToNext();
            }

            Collections.reverse(arraylist);
        }
        return arraylist;
    }

    public void onCreate(SQLiteDatabase sqlitedatabase)
    {
    }

    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
    {
    }

    public void removeFromFav(String s)
    {
        dml((new StringBuilder()).append("delete from song where sid = '").append(s).append("'").toString());
    }

    public void removeFromRecent(String s)
    {
        dml((new StringBuilder()).append("delete from recent where sid = '").append(s).append("'").toString());
    }

    private static String DB_NAME = "mp3.db";
    private String DB_PATH;
    private final Context context;
    private SQLiteDatabase db;
    String outFileName;
    android.content.SharedPreferences.Editor spEdit;
}
