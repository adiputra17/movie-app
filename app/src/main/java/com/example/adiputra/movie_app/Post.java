package com.example.adiputra.movie_app;

/**
 * Created by adiputra on 5/7/2017.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Post{

    public int page;
    public List<Results> results;
    //private Map<String, String> results;

    public class Results{
        private String title;
        private String overview;
        @Override
        public String toString() {
            return title+" "+overview;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(page);
    }


}