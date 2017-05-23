package com.example.adiputra.movie_app.Model;

/**
 * Created by adiputra on 5/23/2017.
 */

public class StarredMovie {
    private String movie_id;
    private String poster_path;

    public StarredMovie(String movie_id, String poster_path){
        this.movie_id = movie_id;
        this.poster_path = poster_path;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
