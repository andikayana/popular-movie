package com.blikadek.popularmovie.database;

import android.provider.BaseColumns;

/**
 * Created by M13x5aY on 27/08/2017.
 */

public class DbContract {

    public static class MovieItemContract implements BaseColumns{

        public static final String TABLE_NAME = "movie_favorite";

        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String POSTER = "poster";
        public static final String SYNOPSIS = "synopsis";
        public static final String RATING = "rating";
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String VOTE_COUNT = "vote_count";
        public static final String RELEASE_DATE = "release_date";
        public static final String BACKDROP = "backdrop";

    }
}
