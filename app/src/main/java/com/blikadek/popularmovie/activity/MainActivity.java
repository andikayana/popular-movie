package com.blikadek.popularmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blikadek.popularmovie.BuildConfig;
import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.adapter.PopularMovieAdapter;
import com.blikadek.popularmovie.model.ApiResponse;
import com.blikadek.popularmovie.model.ResultsItem;
import com.blikadek.popularmovie.rest.ApiClient;
import com.blikadek.popularmovie.rest.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieClickListeners, SwipeRefreshLayout.OnRefreshListener{


    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rvPopularMovie) RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)SwipeRefreshLayout swipeRefresh;
    GridLayoutManager mGridLayoutManager;
    PopularMovieAdapter popularMovieAdapter;
    private List<ResultsItem> mResultsItems = new ArrayList<>();
    String selectMenu;
    private Call<ApiResponse> apiResponseCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getIntent button more to get movie
        int buttonid = getIntent().getIntExtra("button", 0);
        switch (buttonid){
            case R.id.btnMore:
                selectMenu = "Popular Movie";
                break;
            case R.id.btnMoreHightRate:
                selectMenu = "Hight Rated";
                break;

        }
        //SETUp Adapter
        popularMovieAdapter = new PopularMovieAdapter(mResultsItems);
        popularMovieAdapter.setItemClickListenr(MainActivity.this);

        //SETUP RECYCLERVIEW
        mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(popularMovieAdapter);

        //swipe to refresh
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(true);
                getData();

            }
        });



    }

    public void getData(){
        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        switch (selectMenu){
            case "Popular Movie":
                apiResponseCall = apiService.getPopularMovieList(
                        BuildConfig.API_KEY,
                        BuildConfig.DEFAULT_LANG
                );
                break;
            case "Hight Rated":
                apiResponseCall = apiService.getTopRatedList(
                        BuildConfig.API_KEY,
                        BuildConfig.DEFAULT_LANG
                );
                break;
        }

        Log.d(TAG, "getData: API_KEY " + BuildConfig.API_KEY);
        if(apiResponseCall != null){
            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null){
                        mResultsItems = apiResponse.getResults();
                        popularMovieAdapter.setData(mResultsItems);

                        getSupportActionBar().setTitle(selectMenu); //set title on toolbar

                        if(swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);  //stop refresh

                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Call failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });


        }

    }

    @Override
    public void onItemMovieClicked(ResultsItem movieItem) {
        //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        DetailActivity.start(this, movieItem.toJson());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                MainActivity.this.finish();
                return true;
            case R.id.menu_PopularMovie:
                //apiResponseCall=null;
                selectMenu =  item.getTitle().toString();
                getData();
                return true;
            case R.id.menu_HightRated:
                //apiResponseCall=null;
                selectMenu =  item.getTitle().toString();
                getData();
                return true;
            case R.id.menu_Favorites:
                return true;
            case R.id.menu_About:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
