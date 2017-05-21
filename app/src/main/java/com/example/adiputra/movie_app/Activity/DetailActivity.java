package com.example.adiputra.movie_app.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.movie_app.Model.Post;
import com.example.adiputra.movie_app.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private String id;
    private RequestQueue requestQueue;
    private Gson gson;
    private String URL = "http://api.themoviedb.org/3/movie/"+id+"?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TextView tvId = (TextView) findViewById(R.id.tvId);

//        Bundle tvB = getIntent().getExtras();
//        tvId.setText(tvB.getString("id"));
        Intent i = getIntent();
        id = i.getStringExtra("id");
        //Toast.makeText(this, "ID : "+ id, Toast.LENGTH_SHORT).show();
        tvId.setText(i.getStringExtra("id"));


        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        fetchPosts();
    }

    private void fetchPosts() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }
    private final Response.Listener<JSONObject> onPostsLoaded = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                String backdrop_path = response.getString("backdrop_path");
                int budget = response.getInt("budget");
                int id = response.getInt("id");
                String original_title = response.getString("original_title");
                String overview = response.getString("overview");
                double popularity = response.getDouble("popularity");
                String poster_path = response.getString("poster_path");
                String release_date = response.getString("release_date");
                int revenue = response.getInt("revenue");
                int runtime = response.getInt("runtime");
                String title = response.getString("title");
                double vote_average = response.getDouble("vote_average");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ListActivity", error.toString());
        }
    };
}
