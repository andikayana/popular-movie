package com.blikadek.popularmovie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blikadek.popularmovie.database.DbContract.MovieItemContract;
import com.blikadek.popularmovie.model.MovieItem;

import java.util.List;

/**
 * Created by M13x5aY on 27/08/2017.
 */

public class DbOpenHelper  extends SQLiteOpenHelper{

    private static final String TAG = DbOpenHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "movie_favorite";
    private static final int DATABASE_VERSION = 2;


    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_TABEL_MOVIE_FAV =
            "CREATE TABLE " + MovieItemContract.TABLE_NAME + " (" +
                    MovieItemContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MovieItemContract.MOVIE_ID + " INTEGER, " +
                    MovieItemContract.TITLE + " TEXT, " +
                    MovieItemContract.POSTER + " TEXT, " +
                    MovieItemContract.SYNOPSIS + " TEXT, " +
                    MovieItemContract.RATING + " REAL, " +
                    MovieItemContract.VOTE_COUNT + " INTEGER, " +
                    MovieItemContract.RELEASE_DATE + " TEXT, " +
                    MovieItemContract.ORIGINAL_LANGUAGE + " TEXT, " +
                    MovieItemContract.BACKDROP + " TEXT " +

                    "); ";

    private static final String SQL_DROP_TABEL_MOVIE_FAV =
            "DROP TABLE IF EXISTS " + MovieItemContract.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABEL_MOVIE_FAV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABEL_MOVIE_FAV);
        onCreate(db);

    }

    public long saveMovieItem(MovieItem movieItem){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MovieItemContract.MOVIE_ID, movieItem.getId());
        cv.put(MovieItemContract.TITLE, movieItem.getOriginalTitle());
        cv.put(MovieItemContract.POSTER, movieItem.getPosterPath());
        cv.put(MovieItemContract.SYNOPSIS, movieItem.getOverview());
        cv.put(MovieItemContract.RATING, movieItem.getVoteAverage());
        cv.put(MovieItemContract.VOTE_COUNT, movieItem.getVoteCount());
        cv.put(MovieItemContract.RELEASE_DATE, movieItem.getReleaseDate());
        cv.put(MovieItemContract.ORIGINAL_LANGUAGE, movieItem.getOriginalLanguage());
        cv.put(MovieItemContract.BACKDROP, movieItem.getBackdropPath());


        long rowID = db.insert(MovieItemContract.TABLE_NAME, null, cv);
        db.close();

        Log.d("OpenHelper", "isSaveSucsess ? " + String.valueOf(true));

        return rowID;


    }

    public boolean deleteMovieItem(Integer id ){
            SQLiteDatabase db = getReadableDatabase();
            String whereClause = MovieItemContract.MOVIE_ID + "=?";
            String[] whereArgs = new String[]{String.valueOf(id)};
            int rowEffected = db.delete(MovieItemContract.TABLE_NAME, whereClause, whereArgs);

            db.close();

            return rowEffected > 0;
    }

    public boolean isMovieSaveAsFavorite(int id){
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(
                    MovieItemContract.TABLE_NAME,
                    null,
                    MovieItemContract.MOVIE_ID + "=?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );

            int totalRow = cursor.getCount();
            db.close();
            return totalRow>0;
    }




}
