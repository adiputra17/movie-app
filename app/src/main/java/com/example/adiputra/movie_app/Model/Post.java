package com.example.adiputra.movie_app.Model;

/**
 * Created by adiputra on 5/7/2017.
 */

import java.util.List;

public class Post{

    public int page;
    public List<Results> results;
    //private Map<String, String> results;

    public static class Results{
        private Long ID;
        private String title;
        private int id;
        private String poster_path;
        private String overview;
        private String release_date;
        private float popularity;
        private float vote_average;

        public Results(int id, String poster_path) {
            this.id = id;
            this.poster_path = poster_path;
        }

        public Results(Long ID, int id, String poster_path) {
            this.ID = ID;
            this.id = id;
            this.poster_path = poster_path;
        }

        @Override
        public String toString() {return getId()+"-"+getPoster_path();}

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


        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public float getPopularity() {
            return popularity;
        }

        public void setPopularity(float popularity) {
            this.popularity = popularity;
        }

        public float getVote_average() {
            return vote_average;
        }

        public void setVote_average(float vote_average) {
            this.vote_average = vote_average;
        }
    }
}