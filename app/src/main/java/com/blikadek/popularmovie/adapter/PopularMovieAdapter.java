package com.blikadek.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.activity.DasboardActivity;
import com.blikadek.popularmovie.activity.MainActivity;
import com.blikadek.popularmovie.activity.MovieClickListeners;
import com.blikadek.popularmovie.model.ResultsItem;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M13x5aY on 21/08/2017.
 */

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>{

    private List<ResultsItem> resultsItemList;
    private MovieClickListeners mMovieClickListeners;

    public PopularMovieAdapter(List<ResultsItem> resultsItemList) {
        this.resultsItemList = resultsItemList;
    }

    @Override
    public PopularMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (DasboardActivity.isMe){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_poster_dasboard, parent, false);
            return new PopularMovieViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_movie, parent, false);
            return new PopularMovieViewHolder(view);
        }



    }


    @Override
    public void onBindViewHolder(PopularMovieViewHolder holder, final int position) {
        holder.bind(resultsItemList.get(position));
        holder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("test", "test");
                if (mMovieClickListeners != null){
                    mMovieClickListeners.onItemMovieClicked(
                            resultsItemList.get(position)
                    );
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsItemList.size();
    }

    public void setData(List<ResultsItem> data) {
        this.resultsItemList.clear();
        resultsItemList.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemClickListenr(MovieClickListeners clickListeners) {
        if(clickListeners != null){
            mMovieClickListeners = clickListeners;
            //Log.d("gagal klik", "gagal");
        }
    }

    static class PopularMovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPoster) ImageView imgPoster;
        //@BindView(R.id.imgPosterDasboard) ImageView imgPosterDasboard;


        public PopularMovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

        public void bind(ResultsItem resultsItem) {
            String IMG_URL = "http://image.tmdb.org/t/p/";
            String IMG_SIZE = "w185";

            Glide.with(imgPoster.getContext())
                    .load(IMG_URL + IMG_SIZE + resultsItem.getPosterPath())
                    .into(imgPoster);

            /*Glide.with(imgPosterDasboard.getContext())
                    .load(IMG_URL + IMG_SIZE + resultsItem.getPosterPath())
                    .into(imgPosterDasboard);*/

        }
    }

}
