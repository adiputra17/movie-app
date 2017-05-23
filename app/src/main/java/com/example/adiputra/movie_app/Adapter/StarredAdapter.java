package com.example.adiputra.movie_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adiputra.movie_app.Activity.DetailActivity;
import com.example.adiputra.movie_app.Model.Post;
import com.example.adiputra.movie_app.Model.StarredMovie;
import com.example.adiputra.movie_app.R;

import java.util.List;

/**
 * Created by adiputra on 5/23/2017.
 */

public class StarredAdapter extends RecyclerView.Adapter<StarredAdapter.MyViewHolder> {

    private Context mContext;
    private java.util.List starredList;

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

    public StarredAdapter(Context mContext, List<StarredMovie> starredList) {
        this.mContext = mContext;
        this.starredList = starredList;
    }

    @Override
    public StarredAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie, parent, false);
        return new StarredAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StarredAdapter.MyViewHolder holder, int position) {
        final StarredMovie sm = (StarredMovie) starredList.get(position);
        holder.tvId.setText(String.valueOf(sm.getMovie_id()));
        Glide.with(mContext.getApplicationContext())
                .load("http://image.tmdb.org/t/p/w500"+sm.getPoster_path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent i = new Intent(v.getContext(), DetailActivity.class);
                i.putExtra("id",String.valueOf(sm.getMovie_id()));
                i.putExtra("poster_path",sm.getPoster_path());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return starredList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
