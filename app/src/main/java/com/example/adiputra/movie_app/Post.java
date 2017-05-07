package com.example.adiputra.movie_app;

/**
 * Created by adiputra on 5/7/2017.
 */

import java.util.ArrayList;
import java.util.Date;
import com.google.gson.annotations.SerializedName;

public class Post extends ArrayList<Post.Container> {
    public class Container {
        @SerializedName("page")
        private int page;
        @SerializedName("result")
        private Object result;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }
    }

    public class Result {
        @SerializedName("poster_path")
        private String poster_path;
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}