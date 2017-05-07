package com.example.adiputra.movie_app;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    ImageButton btnMenu;
    public Context mContext;
    public View mAnchor;
    public static TextView tvList;

    //JSON Parse
    //private  static final String ENDPOINT = "https://api.themoviedb.org/3/movie/550?api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
    private static final String ENDPOINT = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc?&api_key=a6e05a69bd85f4eba7ec8e9db66cd4ef";
    //private static final String ENDPOINT = "https://kylewbanks.com/rest/posts.json";
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
        fetchPosts();
        //JSONParse --close--

        tvList = (TextView) findViewById(R.id.tvList);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.two:
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.three:
                                Toast.makeText(ListActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
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

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("ListActivity", error.toString());
            tvList.append("Please Check Intenet Connection\n");
        }
    };

    //public class YourClassList extends ArrayList<Post> {}

//    public static final <T> List<T> getList(String json) throws Exception {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        Type typeOfList = new TypeToken<List<T>>(){}.getType();
//        return gson.fromJson(json, typeOfList);
//    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.i("PostActivity", response);
            tvList.append(response +"\n\n");

            Type type = new TypeToken<List<Post>>(){}.getType();
            Post posts = gson.fromJson(response, type);
            tvList.append(posts.toString() +"\n\n");

//            for (Post.Container container : posts) {
//                String innerJson = gson.toJson(container.result);
//                Post.Result[] posts2 = gson.fromJson(innerJson, Post.Result[].class);
//                for (Post.Result post : posts2) {
//                    Log.i("PostActivity", post.id + " : " + post.title);
//                    tvList.append(post.id + " : " + post.title);
//                }
//            }
//            List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));
//
//            Log.i("PostActivity", posts.size() + " posts loaded.");
//            for (Post post : posts) {
//                Log.i("PostActivity", post.ID + ": " + post.title);
//            }

//                Type listType = new TypeToken<List<Post>>(){}.getType();
//                List<Post> posts = gson.fromJson(response, listType);
                //Post[] mcArray = gson.fromJson(response, Post[].class);
                //List<Post> posts = Arrays.asList(mcArray);
                //List<Post> posts = new ArrayList<>(Arrays.asList(mcArray));
                //List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));
                //final Post posts = gson.fromJson(response, Post.class);

//                List<Post> posts = null;
//                try {
//                    posts = getList(response);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String jsonDuplicatedItems = request.getSession().getAttribute("jsonDuplicatedItems").toString();
//                List<Map.Entry<Product, Integer>> posts = gson.fromJson(jsonDuplicatedItems, Post.class);

            //Response posts = gson.fromJson(response, Response.class);
            //String json = gson.toJson(response);
//                List<Post> posts = gson.fromJson(response, new TypeToken<List<Post>>(){}.getType());

//            Type listType = new TypeToken<List<String>>() {}.getType();
//            List<String> target = new LinkedList<String>();
//
//            String json = gson.toJson(target, listType);
//            List<Post> posts = gson.fromJson(json, listType);
//
//                Log.i("PostActivity", posts.size() + " posts loaded.");
////                tvList.append(posts.toString()+"\n\n");
////                int currentTotal = posts.results.size();
//                for (Post post : posts) {
//                    //Log.i("PostActivity", post.page);
//                    tvList.append(post.title+"\n\n");
//                }

        }

    };

}


