package com.example.adiputra.movie_app.Activity;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adiputra.movie_app.Adapter.MovieAdapter;
import com.example.adiputra.movie_app.Database.DatabaseOperations;
import com.example.adiputra.movie_app.Model.Post;
import com.example.adiputra.movie_app.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ImageButton btnMenu;
    public Context mContext;
    public View mAnchor;
    public static TextView tvList;
    public static TextView tvToolbar;
    public int flag;
    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private List<Post.Results> modelListMovie = new ArrayList<>();

    //JSON Parse
    private static final String TOPRATE = "http://api.themoviedb.org/3/movie/top_rated?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
    private static final String POPULAR = "http://api.themoviedb.org/3/movie/popular?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
    private static final String STARRED = "";
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //JSONParse --open--
        requestQueue = Volley.newRequestQueue(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        //JSONParse --close--

        tvToolbar = (TextView) findViewById(R.id.tvToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        adapter = new MovieAdapter(getApplicationContext(), modelListMovie);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ListActivity.this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        fetchPosts(2);

        btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder =new MenuBuilder(ListActivity.this);
                MenuInflater inflater = new MenuInflater(ListActivity.this);
                inflater.inflate(R.menu.main, menuBuilder);
                MenuPopupHelper popup = new MenuPopupHelper(ListActivity.this, menuBuilder, v);
                popup.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.one:
                                flag = 1;
                                fetchPosts(flag);
                                Toast.makeText(ListActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.two:
                                flag = 2;
                                fetchPosts(flag);
                                Toast.makeText(ListActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.three:
                                tvToolbar.setText("Starred Movie");
                                prepareListData();
                                Toast.makeText(ListActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {}
                });

                popup.show();
            }
        });
    }

    private void fetchPosts(int flag) {
        if(flag==1){
            tvToolbar.setText("Top Rate Movie");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, TOPRATE, null, onPostsLoaded, onPostsError);
            requestQueue.add(request);
        }else if(flag==2){
            tvToolbar.setText("Popular Movie");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, POPULAR, null, onPostsLoaded, onPostsError);
            requestQueue.add(request);
        }
    }

    private final Response.Listener<JSONObject> onPostsLoaded = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                String results = response.getString("results");
                Log.i("result : ", results);
                List<Post.Results> posts = Arrays.asList(gson.fromJson(results, Post.Results[].class));
                modelListMovie.removeAll(modelListMovie);
                for (Post.Results post : posts) {
                    modelListMovie.add(new Post.Results(
                            post.getId(),
                            post.getPoster_path()
                    ));
                }
                adapter.notifyDataSetChanged();
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

    private void prepareListData() {
        modelListMovie.removeAll(modelListMovie);
        try{
            DatabaseOperations DOP = new DatabaseOperations(mContext);
            Cursor CR = DOP.read(DOP);
            CR.moveToFirst();
            String MOVIE_ID = "";
            String POSTER_PATH = "";
            do{
                MOVIE_ID = CR.getString(1);
                POSTER_PATH = CR.getString(2);
                modelListMovie.add(new Post.Results(Integer.parseInt(MOVIE_ID), POSTER_PATH));
                //List l = new List(Integer.parseInt(MOVIE_ID), POSTER_PATH);
                //modelListMovie.add(l);
                Collections.reverse(modelListMovie);
            }while(CR.moveToNext());
        }catch(Exception e){
            System.out.println(e);
        }
    }

}


