package com.blikadek.popularmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blikadek.popularmovie.R;
import com.blikadek.popularmovie.model.review.ResultsItemReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M13x5aY on 22/08/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    List<ResultsItemReview> resultsItemReviewList;

    public ReviewAdapter(List<ResultsItemReview> resultsItemReviewList) {
        this.resultsItemReviewList = resultsItemReviewList;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_review, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(resultsItemReviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultsItemReviewList.size();
    }

    public void setData(List<ResultsItemReview> mResultsItemReviews) {
        this.resultsItemReviewList.clear();
        resultsItemReviewList.addAll(mResultsItemReviews);
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author) TextView author;
        @BindView(R.id.tv_content) TextView content;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ResultsItemReview resultsItemReview) {
            author.setText(resultsItemReview.getAuthor());
            content.setText(resultsItemReview.getContent());
        }
    }
}
