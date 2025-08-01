package app.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import app.movies.Adapter.EpisodeCategory;
import app.movies.Model.Episode;
import app.movies.Model.FilmThum;
import app.movies.Model.Movie;
import app.movies.Model.ServerDatum;
import app.movies.R;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private ProgressBar mProgressBar;
    private TextView tvTitle, tvMovieRatting, tvMovieTime, tvMovieSumary, tvMovieActor;
    private String slugFilm;
    private ImageView imgTitle, imgBack, imgFav;
    private RecyclerView.Adapter adapterActorList, adapterCategory, adapterEpisode;
    private RecyclerView recyclerActor, recyclerCategory, recyclerEpisode;
    private NestedScrollView scrollView;
    private Button btnMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        slugFilm = getIntent().getStringExtra("slug");
        initView();
        sendRequest();
        lookMovie();
    }

    private void lookMovie() {
        btnMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(new DetailActivity(), PlayerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);

        String url = "https://phimapi.com/phim/" + slugFilm;
        Log.d("API_LINK", "Request URL: " + url);

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                FilmThum item = gson.fromJson(s, FilmThum.class);

                Glide.with(DetailActivity.this)
                        .load(item.getMovie().getPosterUrl()).into(imgTitle);
                tvTitle.setText(item.getMovie().getName());
                tvMovieRatting.setText(String.valueOf(item.getMovie().getTmdb().getVoteCount()));
                tvMovieTime.setText(item.getMovie().getTime());
                tvMovieActor.setText(TextUtils.join(", ", item.getMovie().getActor()));
                tvMovieSumary.setText(item.getMovie().getContent());
                if (item.getEpisodes() != null && !item.getEpisodes().isEmpty()) {
                    adapterEpisode = new EpisodeCategory(item.getEpisodes().get(0).getServerData());
                    recyclerEpisode.setAdapter(adapterEpisode);
                    adapterEpisode.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("API_ERROR", "onErrorResponse: " + volleyError.toString());
            }
        });

        mRequestQueue.add(stringRequest);
    }

    //
    private void initView() {
        tvTitle = findViewById(R.id.tvMovieName);
        scrollView = findViewById(R.id.scrollView2);
        btnMovie=findViewById(R.id.btn_play);
        imgTitle = findViewById(R.id.imgTitle);
        tvMovieRatting = findViewById(R.id.tvRatting);
        tvMovieTime = findViewById(R.id.tvTime);
        tvMovieSumary = findViewById(R.id.tvMovieSumary);
        tvMovieActor = findViewById(R.id.tvActor);
        imgBack = findViewById(R.id.btnBack);
        imgFav = findViewById(R.id.btnFav);
        imgBack.setOnClickListener(v -> finish());
        recyclerEpisode = findViewById(R.id.rvEpisode);
        recyclerEpisode.setNestedScrollingEnabled(false);
        recyclerEpisode.setLayoutManager(new GridLayoutManager(this, 4));
    }
}