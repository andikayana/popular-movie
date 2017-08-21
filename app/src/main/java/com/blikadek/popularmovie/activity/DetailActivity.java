package com.blikadek.popularmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.pojo.ResultsItem;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String KEY_EXTRA_MOVIE = "movie";
    private ResultsItem mResultsItem;

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.releaseDate) TextView releaseDate;
    @BindView(R.id.rating) TextView rating;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.synopsis) TextView synopsis;
    @BindView(R.id.expandedImage)ImageView backdrop;


    public static void start(Context context, String newsJson){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_EXTRA_MOVIE, newsJson);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupData();
    }

    public void setupData(){
        if (getIntent().hasExtra(KEY_EXTRA_MOVIE)){
            String newsJson = getIntent().getStringExtra(KEY_EXTRA_MOVIE);
            mResultsItem = new ResultsItem().fromJson(newsJson);

            //Toast.makeText(this, "Show movie " + mResultsItem.getTitle(), Toast.LENGTH_SHORT).show();
            String IMG_URL = "http://image.tmdb.org/t/p/";
            String IMG_SIZE = "w185";
            String IMG_SIZE_BACKDROP ="w300";

            getSupportActionBar().setTitle(mResultsItem.getOriginalTitle());
            releaseDate.setText(mResultsItem.getReleaseDate());
            rating.setText(String.valueOf(mResultsItem.getVoteAverage()));
            ratingBar.setRating(Float.parseFloat(String.valueOf(mResultsItem.getVoteAverage())));
            synopsis.setText(mResultsItem.getOverview());

            Glide.with(backdrop.getContext())
                    .load(IMG_URL + IMG_SIZE_BACKDROP + mResultsItem.getBackdropPath())
                    .into(backdrop);
            Glide.with(poster.getContext())
                    .load(IMG_URL + IMG_SIZE + mResultsItem.getPosterPath())
                    .into(poster);

        } else {
            finish();
        }
    }

}
