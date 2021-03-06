package com.blikadek.popularmovie.rest;

import com.blikadek.popularmovie.model.ApiResponse;
import com.blikadek.popularmovie.model.review.ReviewResponse;
import com.blikadek.popularmovie.model.trailer.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by M13x5aY on 21/08/2017.
 */

public interface ApiService {

    @GET("movie/popular")
    Call<ApiResponse> getPopularMovieList(
            @Query("api_key") String api_key,
            @Query("language") String language
    );
    @GET("movie/top_rated")
    Call<ApiResponse> getTopRatedList(
            @Query("api_key") String api_key,
            @Query("language") String language
    );
    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(
            @Path("id") long id,
            @Query("api_key") String api_key
    );
    @GET("movie/{id}/videos?")
    Call<TrailerResponse> getTrailers(
            @Path("id") long id,
            @Query("api_key") String api_key
    );

}
