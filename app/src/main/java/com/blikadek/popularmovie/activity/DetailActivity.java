package com.blikadek.popularmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blikadek.popularmovie.BuildConfig;
import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.adapter.ReviewAdapter;
import com.blikadek.popularmovie.model.ResultsItem;
import com.blikadek.popularmovie.model.review.ResultsItemReview;
import com.blikadek.popularmovie.model.review.ReviewResponse;
import com.blikadek.popularmovie.rest.ApiClient;
import com.blikadek.popularmovie.rest.ApiService;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String KEY_EXTRA_MOVIE = "movie";
    private ResultsItem mResultsItem;

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.releaseDate) TextView releaseDate;
    @BindView(R.id.rating) TextView rating;
    @BindView(R.id.synopsis) TextView synopsis;
    @BindView(R.id.vote) TextView vote;
    @BindView(R.id.language) TextView language;
    @BindView(R.id.expandedImage)ImageView backdrop;

    @BindView(R.id.rvReviews) RecyclerView rvReview;
    LinearLayoutManager mLinearLayoutManager;
    ReviewAdapter reviewAdapter;
    private List<ResultsItemReview> mResultsItemReviews = new ArrayList<>();

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setupData();
        setupReview();

    }

    public void setupData(){
        if (getIntent().hasExtra(KEY_EXTRA_MOVIE)){
            String newsJson = getIntent().getStringExtra(KEY_EXTRA_MOVIE);
            mResultsItem = new ResultsItem().fromJson(newsJson);

            String IMG_URL = "http://image.tmdb.org/t/p/";
            String IMG_SIZE = "w185";
            String IMG_SIZE_BACKDROP ="w300";

            getSupportActionBar().setTitle(mResultsItem.getOriginalTitle());
            releaseDate.setText(mResultsItem.getReleaseDate());
            rating.setText(String.valueOf(mResultsItem.getVoteAverage())+"/10");
            vote.setText(mResultsItem.getVoteCount()+" Vote");
            language.setText(mResultsItem.getOriginalLanguage());
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

    public void setupReview(){

        //SETUp Adapter
        reviewAdapter = new ReviewAdapter(mResultsItemReviews);

        //SETUP RECYCLERVIEW
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvReview.setLayoutManager(mLinearLayoutManager);
        rvReview.setAdapter(reviewAdapter);

        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        Call<ReviewResponse> apiResponseCall = apiService.getReviews(
                mResultsItem.getId(),
                BuildConfig.API_KEY
        );

        apiResponseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                ReviewResponse reviewResponse = response.body();
                if (reviewResponse != null){
                    mResultsItemReviews = reviewResponse.getResults();
                    reviewAdapter.setData(mResultsItemReviews);

                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("onFailure: ", String.valueOf(t));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detaialctivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //tombol Arrow for back
            case android.R.id.home:
                DetailActivity.this.finish();
                return true;
            //tombl share
            case R.id.menu_share:
                Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


