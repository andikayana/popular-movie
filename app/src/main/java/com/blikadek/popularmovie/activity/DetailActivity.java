package com.blikadek.popularmovie.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blikadek.popularmovie.BuildConfig;
import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.adapter.ReviewAdapter;
import com.blikadek.popularmovie.adapter.TrailerAdapter;
import com.blikadek.popularmovie.database.DbOpenHelper;
import com.blikadek.popularmovie.model.MovieItem;
import com.blikadek.popularmovie.model.review.ResultsItemReview;
import com.blikadek.popularmovie.model.review.ReviewResponse;
import com.blikadek.popularmovie.model.trailer.TrailerItem;
import com.blikadek.popularmovie.model.trailer.TrailerResponse;
import com.blikadek.popularmovie.rest.ApiClient;
import com.blikadek.popularmovie.rest.ApiService;
import com.blikadek.popularmovie.rest.DateFormater;
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

    private MovieItem mMovieItem;

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.releaseDate) TextView releaseDate;
    @BindView(R.id.rating) TextView rating;
    @BindView(R.id.synopsis) TextView synopsis;
    @BindView(R.id.vote) TextView vote;
    @BindView(R.id.language) TextView language;
    @BindView(R.id.expandedImage)ImageView backdrop;
    @BindView(R.id.fab) FloatingActionButton fabFavorite;
    @BindView(R.id.nestedScroolView) NestedScrollView nestedScrollView;
    @BindView(R.id.rvReviews) RecyclerView rvReview;
    @BindView(R.id.rvTrailer) RecyclerView rvTrailer;
    private boolean mIsFavorite = false;
    private DbOpenHelper mDbOpenHelper;
    LinearLayoutManager mLinearLayoutManager, mLinearLayoutManagerTrailer;
    ReviewAdapter reviewAdapter;
    TrailerAdapter trailerAdapter;
    private List<ResultsItemReview> mResultsItemReviews = new ArrayList<>();
    private List<TrailerItem> mTrailerItem = new ArrayList<>();

    public static void start(Context context, MovieItem movieItem){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_EXTRA_MOVIE, movieItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mDbOpenHelper= new DbOpenHelper(getApplicationContext());

        if (!getIntent().hasExtra(KEY_EXTRA_MOVIE)) {
            finish();
        }

        setupData();
        setupReview();
        setupActionBar();
        setupFab();
        setupTrailer();

    }

    public void setupData(){
        if (getIntent().hasExtra(KEY_EXTRA_MOVIE)){
            mMovieItem = getIntent().getParcelableExtra(KEY_EXTRA_MOVIE);

            String IMG_URL = "http://image.tmdb.org/t/p/";
            String IMG_SIZE = "w185";
            String IMG_SIZE_BACKDROP ="w300";


            releaseDate.setText(DateFormater.getDate(mMovieItem.getReleaseDate()));
            rating.setText(String.valueOf(mMovieItem.getVoteAverage())+"/10");
            vote.setText(mMovieItem.getVoteCount()+" Vote");
            language.setText(mMovieItem.getOriginalLanguage());
            synopsis.setText(mMovieItem.getOverview());

            Glide.with(backdrop.getContext())
                    .load(IMG_URL + IMG_SIZE_BACKDROP + mMovieItem.getBackdropPath())
                    .into(backdrop);
            Glide.with(poster.getContext())
                    .load(IMG_URL + IMG_SIZE + mMovieItem.getPosterPath())
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
                mMovieItem.getId(),
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

    public void setupActionBar(){

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mMovieItem.getOriginalTitle());
        //back home
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    public void setupFab(){
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY){
                    if(fabFavorite.isShown()){
                        fabFavorite.hide();
                    }
                } else {
                    if(!fabFavorite.isShown()){
                        fabFavorite.show();
                    }
                }
            }
        });
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFavorite){
                    boolean isDeleteSuccess = mDbOpenHelper.deleteMovieItem(mMovieItem.getId());
                    mIsFavorite = !isDeleteSuccess;
                    fabFavorite.setImageResource(R.drawable.ic_favorite_select);
                } else {
                    mIsFavorite = mDbOpenHelper.saveMovieItem(mMovieItem) > 0;
                    String snackBarText = mIsFavorite ? "News saved as favorite" : "Failed to save news";
                    Snackbar.make(fabFavorite, snackBarText, Snackbar.LENGTH_SHORT)
                            .show();
                    fabFavorite.setImageResource(R.drawable.ic_favorite_unselected);
                }

                //fabFavorite.setImageResource(mIsFavorite ? R.drawable.ic_favorite_select : R.drawable.ic_favorite_unselected);


            }
        });
    }

    public void setupTrailer(){
        //SETUp Adapter
        trailerAdapter = new TrailerAdapter(mTrailerItem);

        //SETUP RECYCLERVIEW
        mLinearLayoutManagerTrailer = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTrailer.setLayoutManager(mLinearLayoutManagerTrailer);
        rvTrailer.setAdapter(trailerAdapter);

        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        Call<TrailerResponse> apiResponseCall = apiService.getTrailers(
                mMovieItem.getId(),
                BuildConfig.API_KEY
        );

        apiResponseCall.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                TrailerResponse trailerResponse = response.body();
                if (trailerResponse != null){
                    mTrailerItem = trailerResponse.getResults();
                    trailerAdapter.setData(mTrailerItem);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
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


