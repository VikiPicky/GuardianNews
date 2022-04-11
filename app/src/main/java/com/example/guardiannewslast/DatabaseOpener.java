package com.example.guardiannewslast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpener extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "SavedNews.db";
    protected static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "news_saved";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_Title = "news_title";
    public static final String COLUMN_Section = "news_section";
    public static final String COLUMN_Date = "news_date";
    public static final String COLUMN_URL = "news_url";

    public DatabaseOpener(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =  "CREATE TABLE " + DATABASE_TABLE + "(" +
                COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_Title + " TEXT, " +
                COLUMN_Section + " TEXT, " +
                COLUMN_Date + " TEXT, " +
                COLUMN_URL + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
