package com.example.adiputra.movie_app;

/**
 * Created by adiputra on 5/7/2017.
 */

import java.util.List;

public class Post{

    public int page;
    public List<Results> results;
    //private Map<String, String> results;

    public static class Results{
        private String title;
        private int id;
        private String poster_path;

        @Override
        public String toString() {
            return getId() +"-"+ getTitle() +"-"+ getPoster_path();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(page);
    }


}