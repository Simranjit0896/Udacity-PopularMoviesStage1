package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvOriginalTitle = findViewById(R.id.tv_original_title);
        ImageView ivPoster = findViewById(R.id.iv_poster);
        TextView tvOverview = findViewById(R.id.tv_overview);
        TextView tvVoteAverage = findViewById(R.id.tv_vote_average);
        TextView tvReleaseDate = findViewById(R.id.tv_release_date);

        Intent intent = getIntent();

        try {
            Movie movies = intent.getParcelableExtra(getString(R.string.parcel_movie));
            tvOriginalTitle.setText(movies.getTitle());

            Picasso.with(this)
                    .load(movies.getPosterPath())
                    .resize(185,278)
                    .error(R.drawable.error)
                    .into(ivPoster);

            String overview = movies.getOverview();
            if (overview == null) {
                tvOverview.setTypeface(null, Typeface.ITALIC);
                overview = getResources().getString(R.string.no_summary_found);
            }
            tvOverview.setText(overview);

            tvVoteAverage.setText(movies.getDetailedVoteAverage());

            // Getting the release date from object and localizing it.
            String releaseDate = movies.getReleaseDate();
            if (releaseDate != null) {
                try {
                    releaseDate = DateTimeHelper.getLocalizedDate(this,
                            releaseDate, movies.getDateFormat());
                } catch (ParseException e) {
                    Log.e(LOG_TAG, "Error with parsing movie release date", e);
                }
            } else {
                tvReleaseDate.setTypeface(null, Typeface.ITALIC);
                releaseDate = getResources().getString(R.string.no_release_date_found);
            }
            tvReleaseDate.setText(releaseDate);
        } catch (Exception e) {
            Log.d("ONCREATEERROR", "onCreate: " + e.getMessage());
            Log.d("ERROR", "onCreate: " + e.getLocalizedMessage());
        }
    }
}