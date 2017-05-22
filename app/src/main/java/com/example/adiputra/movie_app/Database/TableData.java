package com.example.adiputra.movie_app.Database;

/**
 * Created by adiputra on 5/22/2017.
 */

import android.provider.BaseColumns;

public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns{
        //public static final int ID = 0;
        public static final String MOVIE_ID = "movie_id";
        public static final String POSTER_PATH = "poster_path";
        public static final String DATABASE_NAME = "movie_db";
        public static final String TABLE_NAME = "movie_table";
    }
}
