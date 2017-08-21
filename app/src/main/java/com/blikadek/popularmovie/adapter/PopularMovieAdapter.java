package com.blikadek.popularmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.pojo.ResultsItem;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M13x5aY on 21/08/2017.
 */

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>{

    List<ResultsItem> resultsItemList;

    public PopularMovieAdapter(List<ResultsItem> resultsItemList) {
        this.resultsItemList = resultsItemList;
    }

    @Override
    public PopularMovieAdapter.PopularMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie, parent, false);
        PopularMovieViewHolder viewHolder = new PopularMovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PopularMovieAdapter.PopularMovieViewHolder holder, int position) {
        holder.bind(resultsItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultsItemList.size();
    }

    static class PopularMovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.imgPoster)
        ImageView imgPoster;


        public PopularMovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ResultsItem resultsItem) {
            tvTitle.setText(resultsItem.getTitle());
            String IMG_URL = "http://image.tmdb.org/t/p/";
            String IMG_SIZE = "w185";
            Glide.with(imgPoster.getContext())
                    .load(IMG_URL + IMG_SIZE + resultsItem.getPosterPath())
                    .into(imgPoster);

        }
    }
}
