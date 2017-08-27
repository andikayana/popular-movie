package com.blikadek.popularmovie.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.blikadek.popularmovie.database.DbContract.*;

/**
 * Created by M13x5aY on 27/08/2017.
 */

public class DbOpenHelper  extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movie_favorite";
    private static final int DATABASE_VERSION = 1;


    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    private static final String SQL_CREATE_TABEL_MOVIE_FAV =
            "CREATE TABLE " + MovieItemContract.TABLE_NAME + " (" +
                    MovieItemContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MovieItemContract.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                    MovieItemContract.COLUMN_TITLE + " TEXT, " +
                    MovieItemContract.COLUMN_POSTER + " TEXT, " +
                    MovieItemContract.COLUMN_SYNOPSIS + " TEXT, " +
                    MovieItemContract.COLUMN_RATING + " REAL, " +
                    MovieItemContract.COLUMN_RELEASE_DATE + " TEXT, " +
                    MovieItemContract.COLUMN_BACKDROP + " TEXT " +
                    MovieItemContract.COLUMN_LANGUAGE + "TEXT " +
                    "); ";

    private static final String SQL_DROP_TABEL_MOVIE_FAV =
            "DROP TABEL IF EXISTS " + MovieItemContract.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABEL_MOVIE_FAV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABEL_MOVIE_FAV);
        onCreate(db);

    }
}
