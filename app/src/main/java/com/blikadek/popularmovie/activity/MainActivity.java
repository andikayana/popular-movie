package com.blikadek.popularmovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.blikadek.popularmovie.BuildConfig;
import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.adapter.PopularMovieAdapter;
import com.blikadek.popularmovie.pojo.ApiResponse;
import com.blikadek.popularmovie.pojo.ResultsItem;
import com.blikadek.popularmovie.rest.ApiClient;
import com.blikadek.popularmovie.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieClickListeners{

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rvPopularMovie) RecyclerView recyclerView;
    GridLayoutManager mGridLayoutManager;
    PopularMovieAdapter popularMovieAdapter;
    private List<ResultsItem> mResultsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //SETUp Adapter
        popularMovieAdapter = new PopularMovieAdapter(mResultsItems);


        //SETUP RECYCLERVIEW
        mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(popularMovieAdapter);

        getData();

        popularMovieAdapter.setItemClickListenr(MainActivity.this);
    }

    public void getData(){
        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        Call<ApiResponse> apiResponseCall = apiService.getPopularMovieList(
                BuildConfig.API_KEY,
                BuildConfig.DEFAULT_LANG
        );
        Log.d(TAG, "getData: API_KEY " + BuildConfig.API_KEY);

        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (apiResponse != null){
                    mResultsItems = apiResponse.getResults();
                    popularMovieAdapter.setData(mResultsItems);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void onItemMovieClicked(ResultsItem movieItem) {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        DetailActivity.start(this, movieItem.toJson());

    }
}
