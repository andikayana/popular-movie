package com.blikadek.popularmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blikadek.popularmovie.BuildConfig;
import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.adapter.PopularMovieAdapter;
import com.blikadek.popularmovie.database.DbOpenHelper;
import com.blikadek.popularmovie.model.ApiResponse;
import com.blikadek.popularmovie.model.MovieItem;
import com.blikadek.popularmovie.rest.ApiClient;
import com.blikadek.popularmovie.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DasboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MovieClickListeners{

    private static final String TAG = DasboardActivity.class.getSimpleName();

    @BindView(R.id.rvlistPoster) RecyclerView rvlistPoster;
    @BindView(R.id.rvlistPoster_HightRate) RecyclerView rvlistPoster_HightRate;
   @BindView(R.id.rvlistPoster_Favorite) RecyclerView rvlistPoster_Favorite;
    @BindView(R.id.btnMore) TextView btnMore;
    @BindView(R.id.btnMoreHightRate) TextView btnMoreHightRate;
    @BindView(R.id.btnMoreFavorite) TextView btnMoreFavorite;
    @BindView(R.id.toolbar) Toolbar toolbar;
    LinearLayoutManager mLinearLayoutManager;
    LinearLayoutManager mLinearLayoutManager_HightRate;
   LinearLayoutManager mLinearLayoutManager_Favorite;
    PopularMovieAdapter popularMovieAdapter;
    PopularMovieAdapter popularMovieAdapter2;
   PopularMovieAdapter popularMovieAdapter3;
    private List<MovieItem> mMovieItems = new ArrayList<>();
    private List<MovieItem> mMovieItems2 = new ArrayList<>();
   private List<MovieItem> mMovieItems3 = new ArrayList<>();

    public static Boolean isMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupData();
    }

    public void setupData(){
        isMe = true;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setup_PopularMovie();
        setup_HightRate();
        setup_Favorite();
    }

    public void btnMore(View v) {
        Intent i = new Intent(this, MainActivity.class);
        isMe=false;
        i.putExtra("button", v.getId());
        startActivity(i);
    }

    public void BtnMoreHightRate(View v) {
        Intent i = new Intent(this, MainActivity.class);
        isMe=false;
        i.putExtra("button", v.getId());
        startActivity(i);
    }
    public void BtnMoreFavorite(View v) {
        Intent i = new Intent(this, MainActivity.class);
        isMe=false;
        i.putExtra("button", v.getId());
        startActivity(i);
    }



    public void setup_PopularMovie(){
        //SETUp Adapter
        popularMovieAdapter = new PopularMovieAdapter(mMovieItems);
        popularMovieAdapter.setItemClickListenr(DasboardActivity.this);

        //SETUP RECYCLERVIEW
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvlistPoster.setHasFixedSize(true);
        rvlistPoster.setLayoutManager(mLinearLayoutManager);
        rvlistPoster.setAdapter(popularMovieAdapter);

        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        Call<ApiResponse> apiResponseCall = apiService.getPopularMovieList(
                    BuildConfig.API_KEY,
                    BuildConfig.DEFAULT_LANG
        );

        if(apiResponseCall != null){
            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null){
                        mMovieItems = apiResponse.getResults();
                        popularMovieAdapter.setData(mMovieItems);

                    }
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(DasboardActivity.this, "Call failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });


        }

    }

    public void setup_HightRate(){
        //SETUp Adapter
        popularMovieAdapter2 = new PopularMovieAdapter(mMovieItems2);
        popularMovieAdapter2.setItemClickListenr(DasboardActivity.this);

        //SETUP RECYCLERVIEW
        mLinearLayoutManager_HightRate = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvlistPoster_HightRate.setLayoutManager(mLinearLayoutManager_HightRate);
        rvlistPoster_HightRate.setHasFixedSize(true);
        rvlistPoster_HightRate.setAdapter(popularMovieAdapter2);

        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        Call<ApiResponse> apiResponseCall = apiService.getTopRatedList(
                BuildConfig.API_KEY,
                BuildConfig.DEFAULT_LANG
        );

        if(apiResponseCall != null){
            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null){
                        mMovieItems2 = apiResponse.getResults();
                        popularMovieAdapter2.setData(mMovieItems2);

                    }
                }
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(DasboardActivity.this, "Call failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });


        }

    }
   public void setup_Favorite(){
        //SETUp Adapter
        popularMovieAdapter3 = new PopularMovieAdapter(mMovieItems3);

        //SETUP RECYCLERVIEW
        mLinearLayoutManager_Favorite = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvlistPoster_Favorite.setLayoutManager(mLinearLayoutManager_Favorite);
        rvlistPoster_Favorite.setHasFixedSize(true);

        DbOpenHelper db = new DbOpenHelper(this);
        mMovieItems3 = db.getFavoriteMovie();
        rvlistPoster_Favorite.setAdapter(new PopularMovieAdapter(mMovieItems3));

        popularMovieAdapter3.setItemClickListenr(DasboardActivity.this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dasboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            setupData();

            Toast.makeText(this, "Data Refresh", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular_movie) {
           btnMore.callOnClick();
        } else if (id == R.id.nav_hight_rate) {
            btnMoreHightRate.callOnClick();
        } else if (id == R.id.nav_favorite) {
            btnMoreFavorite.callOnClick();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemMovieClicked(MovieItem movieItem) {
        DetailActivity.start(this, movieItem);
    }

}
