package com.blikadek.popularmovie.database;

import android.provider.BaseColumns;

/**
 * Created by M13x5aY on 27/08/2017.
 */

public class DbContract {

    public static class MovieItemContract implements BaseColumns{

        public static final String TABLE_NAME = "movie_favorite";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_BACKDROP = "backdrop";
        public static final String COLUMN_LANGUAGE = "language";
    }
}
