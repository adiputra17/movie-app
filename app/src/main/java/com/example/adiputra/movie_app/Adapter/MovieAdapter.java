package com.example.adiputra.movie_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adiputra.movie_app.Activity.DetailActivity;
import com.example.adiputra.movie_app.Activity.ListActivity;
import com.example.adiputra.movie_app.Model.Post;
import com.example.adiputra.movie_app.R;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.adiputra.movie_app.Activity.ListActivity.*;

/**
 * Created by adiputra on 5/21/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context mContext;
    private java.util.List movieList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView tvId;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tvId = (TextView) view.findViewById(R.id.id);
            imageView = (ImageView) view.findViewById(R.id.ivPoster);
        }
    }

    public MovieAdapter(Context mContext, List<Post.Results> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Post.Results mlv = (Post.Results) movieList.get(position);
        holder.tvId.setText(String.valueOf(mlv.getId()));
        Glide.with(mContext.getApplicationContext())
                .load("http://image.tmdb.org/t/p/w500"+mlv.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Toast.makeText(mContext, String.valueOf(mlv.getId()), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(), DetailActivity.class);
                i.putExtra("id",String.valueOf(mlv.getId()));
                i.putExtra("poster_path",mlv.getPoster_path());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
                //ListActivity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
