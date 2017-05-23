package com.example.adiputra.movie_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adiputra.movie_app.Database.DatabaseOperations;
import com.example.adiputra.movie_app.Model.Post;
import com.example.adiputra.movie_app.Model.TraillerMovie;
import com.example.adiputra.movie_app.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private String id, poster_path;
    Context ctx = this;
    private RequestQueue requestQueue;
    private Gson gson;
    //private String URL = "http://api.themoviedb.org/3/movie/"+id+"?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";

    private TextView tvId, tvTitle, tvRuntime, tvRating, tvOverview;
    private ImageView ivPlayButton, ivBackdrop, ivPosters, starRating;
    private Button btnBudget, btnStarred;
    private ProgressBar progress;

    boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        progress = (ProgressBar)findViewById(R.id.progressBar1);
        progress.setVisibility(View.VISIBLE);

        tvId = (TextView) findViewById(R.id.tvId);
        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);
        btnStarred = (Button) findViewById(R.id.btnStarred);
        btnStarred.setVisibility(View.GONE);
        ivPlayButton = (ImageView) findViewById(R.id.ivPlayButton);
        ivPlayButton.setVisibility(View.GONE);
        starRating = (ImageView) findViewById(R.id.starRating);
        starRating.setVisibility(View.GONE);
        ivPosters = (ImageView) findViewById(R.id.ivPosters);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvRuntime = (TextView) findViewById(R.id.tvRuntime);
        tvRating = (TextView) findViewById(R.id.tvRating);
        btnBudget = (Button) findViewById(R.id.btnBudget);
        btnBudget.setVisibility(View.GONE);
        tvOverview = (TextView) findViewById(R.id.tvOverview);

//        Bundle tvB = getIntent().getExtras();
//        tvId.setText(tvB.getString("id"));
        Intent i = getIntent();
        id = i.getStringExtra("id");
        poster_path = i.getStringExtra("poster_path");
        //Toast.makeText(this, "ID : "+ id, Toast.LENGTH_SHORT).show();

        //STARRED MOVIE
        btnStarred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClick){
                    v.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    Toast.makeText(DetailActivity.this, "Movie Unstarred", Toast.LENGTH_SHORT).show();
                }else{
                    v.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    Toast.makeText(DetailActivity.this, "Movie Starred", Toast.LENGTH_SHORT).show();
                }
                isClick = !isClick; // reverse
                DatabaseOperations DB = new DatabaseOperations(ctx);
                DB.create(DB, id, poster_path);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        fetchPosts(id);
    }

    private void fetchPosts(String id) {
        String TRAILLER = "http://api.themoviedb.org/3/movie/"+id+"/videos?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, TRAILLER, null, onPostsLoadedTrailler, onPostsErrorTrailler);
        requestQueue.add(req);
        String URL = "http://api.themoviedb.org/3/movie/"+id+"?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.Listener<JSONObject> onPostsLoadedTrailler = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try{
                String results = response.getString("results");
                List<TraillerMovie.Results> posts = Arrays.asList(gson.fromJson(results, TraillerMovie.Results[].class));
                for (final TraillerMovie.Results post : posts) {
                    ivBackdrop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+post.getKey())));
                            Log.i("Video", "Video Playing....");
                        }
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private final Response.ErrorListener onPostsErrorTrailler = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Trailler : ", error.toString());
        }
    };


    private final Response.Listener<JSONObject> onPostsLoaded = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                //Toast.makeText(DetailActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                String backdrop_path = response.getString("backdrop_path");
                Glide.with(DetailActivity.this)
                        .load("http://image.tmdb.org/t/p/w500"+backdrop_path)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivBackdrop);
                int budget = response.getInt("budget");
                btnBudget.setText("BUY NOW Rp. "+String.valueOf(budget)+",-");
                int id = response.getInt("id");
                String original_title = response.getString("original_title");
                String overview = response.getString("overview");
                tvOverview.setText(overview);
                double popularity = response.getDouble("popularity");
                String poster_path = response.getString("poster_path");
                Glide.with(DetailActivity.this)
                        .load("http://image.tmdb.org/t/p/w500"+poster_path)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivPosters);
                String release_date = response.getString("release_date");
                int revenue = response.getInt("revenue");
                int runtime = response.getInt("runtime");
                tvRuntime.setText("Duration : "+String.valueOf(runtime)+"'");
                String title = response.getString("title");
                tvTitle.setText(title);
                double vote_average = response.getDouble("vote_average");
                tvRating.setText(String.valueOf(vote_average));
                progress.setVisibility(View.GONE);
                ivPlayButton.setVisibility(View.VISIBLE);
                btnBudget.setVisibility(View.VISIBLE);
                starRating.setVisibility(View.VISIBLE);
                btnStarred.setVisibility(View.VISIBLE);
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
