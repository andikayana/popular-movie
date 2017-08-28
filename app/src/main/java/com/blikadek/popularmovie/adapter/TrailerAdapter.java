package com.blikadek.popularmovie.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.model.review.ResultsItemReview;
import com.blikadek.popularmovie.model.trailer.TrailerItem;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M13x5aY on 26/08/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    List<TrailerItem> trailerItemList;

    public TrailerAdapter(List<TrailerItem> trailerItemList) {
        this.trailerItemList = trailerItemList;
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_trailer, parent, false);
        TrailerAdapter.TrailerViewHolder viewHolder = new TrailerAdapter.TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {
        holder.bind(trailerItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return trailerItemList.size();
    }

    public void setData(List<TrailerItem> data) {
        this.trailerItemList.clear();
        trailerItemList.addAll(data);
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTrailerTitle) TextView tvTraillerTitle;
        @BindView(R.id.imgTrailer) ImageView imgTrailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final TrailerItem trailerItem) {
            String IMG_URL = "http://img.youtube.com/vi/";
            String IMG_END = "/0.jpg";
            final String YOUTUBE_URL = "http://www.youtube.com/watch?v=";

            tvTraillerTitle.setText(trailerItem.getName());
            Glide.with(imgTrailer.getContext())
                    .load(IMG_URL + trailerItem.getKey() + IMG_END)
                    .into(imgTrailer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.getContext()
                            .startActivity(new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(YOUTUBE_URL + trailerItem.getKey())
                            ));
                }
            });
        }
    }
}
