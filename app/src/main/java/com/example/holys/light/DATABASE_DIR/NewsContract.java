package com.example.holys.light.DATABASE_DIR;

import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by holys on 2/26/2016.
 */
public class NewsContract {
    public NewsContract(){}
    public static final int WORLD = 10;
    public static final int WORLD_ID = 15;
    public static final int SCIENCE = 20;
    public static final int SCIENCE_ID = 25;
    public static final int SPORT = 30;
    public static final int SPORT_ID = 35;
    public static final int CULTURE = 40;
    public static final int CULTURE_ID = 45;
    public static final int LIFESTYLE = 50;
    public static final int LIFESTYLE_ID = 55;

    private static final String AUTHORITY = "com.example.holys.light.provider";
    public static final String WORLD_BASE_PATH = "world";
    public static final String SCIENCE_BASE_PATH = "science";
    public static final String SPORT_BASE_PATH = "sport";
    public static final String CULTURE_BASE_PATH=  "culture";
    public static final String LIFESTYLE_BASE_PATH = "lifestyle";
    public static final UriMatcher sURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURI_MATCHER.addURI(AUTHORITY, WORLD_BASE_PATH, WORLD);
        sURI_MATCHER.addURI(AUTHORITY, WORLD_BASE_PATH + "/#", WORLD_ID);
        sURI_MATCHER.addURI(AUTHORITY, SCIENCE_BASE_PATH, SCIENCE);
        sURI_MATCHER.addURI(AUTHORITY, SCIENCE_BASE_PATH + "/#", SCIENCE_ID);
        sURI_MATCHER.addURI(AUTHORITY, SPORT_BASE_PATH, SPORT);
        sURI_MATCHER.addURI(AUTHORITY, SPORT_BASE_PATH + "/#", SPORT_ID);
        sURI_MATCHER.addURI(AUTHORITY, CULTURE_BASE_PATH, CULTURE);
        sURI_MATCHER.addURI(AUTHORITY, CULTURE_BASE_PATH + "/#", CULTURE_ID);
        sURI_MATCHER.addURI(AUTHORITY, LIFESTYLE_BASE_PATH, LIFESTYLE);
        sURI_MATCHER.addURI(AUTHORITY, LIFESTYLE_BASE_PATH + "/#", LIFESTYLE_ID);
    }

    public static final Uri CONTENT_URI_WORLD = Uri.parse("content://" + AUTHORITY + "/" + WORLD_BASE_PATH);
    public static final Uri CONTENT_URI_SCIENCE = Uri.parse("content://" + AUTHORITY + "/" + SCIENCE_BASE_PATH);
    public static final Uri CONTENT_URI_SPORT = Uri.parse("content://" + AUTHORITY + "/" + SPORT_BASE_PATH);
    public static final Uri CONTENT_URI_CULTURE = Uri.parse("content://" + AUTHORITY + "/" + CULTURE_BASE_PATH);
    public static final Uri CONTENT_URI_LIFESTYLE = Uri.parse("content://" + AUTHORITY + "/" + LIFESTYLE_BASE_PATH);


    //public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/news";
    //public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/news";

    public static abstract class DataContract implements BaseColumns
    {
        public static final String WORLD_TABLE_NAME = "worldtable";
        public static final String SCIENCE_TABLE_NAME = "sciencetable";
        public static final String SPORT_TABLE_NAME = "sporttable";
        public static final String CULTURE_TABLE_NAME = "culturetable";
        public static final String LIFESTYLE_TABLE_NAME = "lifestyletable";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_THUMB = "imagedata";
        public static final String COLUMN_NAME_WEBADDRESS = "webaddress";
        public static final String COLUMN_NAME_TAG = "tag";


        private static final String DATABASE_CREATE_WORLD = "create table "+
                WORLD_TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                COLUMN_NAME_DATE +
                " text not null, " +
                COLUMN_NAME_TITLE +
                " text not null, " +
                COLUMN_NAME_THUMB +
                " blob not null, " +
                COLUMN_NAME_CONTENT +
                " text not null, " +
                COLUMN_NAME_TAG +
                " text not null, " +
                COLUMN_NAME_WEBADDRESS +
                " text not null);";
        private static final String DATABASE_CREATE_SCIENCE = "create table "+
                SCIENCE_TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                COLUMN_NAME_DATE +
                " text not null, " +
                COLUMN_NAME_TITLE +
                " text not null, " +
                COLUMN_NAME_THUMB +
                " blob not null, " +
                COLUMN_NAME_CONTENT +
                " text not null, " +
                COLUMN_NAME_TAG +
                " text not null, " +
                COLUMN_NAME_WEBADDRESS +
                " text not null);";
        private static final String DATABASE_CREATE_SPORT = "create table "+
                SPORT_TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                COLUMN_NAME_DATE +
                " text not null, " +
                COLUMN_NAME_TITLE +
                " text not null, " +
                COLUMN_NAME_THUMB +
                " blob not null, " +
                COLUMN_NAME_CONTENT +
                " text not null, " +
                COLUMN_NAME_TAG +
                " text not null, " +
                COLUMN_NAME_WEBADDRESS +
                " text not null);";
        private static final String DATABASE_CREATE_CULTURE = "create table "+
                CULTURE_TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                COLUMN_NAME_DATE +
                " text not null, " +
                COLUMN_NAME_TITLE +
                " text not null, " +
                COLUMN_NAME_THUMB +
                " blob not null, " +
                COLUMN_NAME_CONTENT +
                " text not null, " +
                COLUMN_NAME_TAG +
                " text not null, " +
                COLUMN_NAME_WEBADDRESS +
                " text not null);";
        private static final String DATABASE_CREATE_LIFESTYLE = "create table "+
                LIFESTYLE_TABLE_NAME +
                "(" +
                _ID +
                " integer primary key autoincrement, " +
                COLUMN_NAME_DATE +
                " text not null, " +
                COLUMN_NAME_TITLE +
                " text not null, " +
                COLUMN_NAME_THUMB +
                " blob not null, " +
                COLUMN_NAME_CONTENT +
                " text not null, " +
                COLUMN_NAME_TAG +
                " text not null, " +
                COLUMN_NAME_WEBADDRESS +
                " text not null);";

        public static void onCreate (SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_WORLD);
            db.execSQL(DATABASE_CREATE_SCIENCE);
            db.execSQL(DATABASE_CREATE_SPORT);
            db.execSQL(DATABASE_CREATE_CULTURE);
            db.execSQL(DATABASE_CREATE_LIFESTYLE);
        }

        public static void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.WORLD_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.SCIENCE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.SPORT_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.CULTURE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.LIFESTYLE_TABLE_NAME);
            onCreate(db);
        }

    }

}
