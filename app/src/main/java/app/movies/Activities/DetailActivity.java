package app.movies.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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

import app.movies.Adapter.ActorsListAdapter;
import app.movies.Model.FilmItem;
import app.movies.R;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private ProgressBar mProgressBar;
    private TextView tvTitle, tvMovieRatting, tvMovieTime, tvMovieSumary, tvMovieActor;
    private int idFilm;
    private ImageView imgTitle, imgBack, imgFav;
    private RecyclerView.Adapter adapterActorList, adapterCategory;
    private RecyclerView recyclerActor, recyclerCategory;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        idFilm = getIntent().getIntExtra("id", 0);
        initView();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        mProgressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        stringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                mProgressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                FilmItem item=gson.fromJson(s, FilmItem.class);
                Glide.with(DetailActivity.this)
                        .load(item.getPoster()).into(imgTitle);
                tvTitle.setText(item.getTitle());
                tvMovieRatting.setText(item.getImdbRating());
                tvMovieTime.setText(item.getRuntime());
                tvMovieActor.setText(item.getActors());
                tvMovieSumary.setText(item.getPlot());
                if (item.getImages()!= null){
                    adapterActorList= new ActorsListAdapter(item.getImages());
                    recyclerActor.setAdapter(adapterActorList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(stringRequest);
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvMovieName);
        mProgressBar = findViewById(R.id.progressBarDetail);
        scrollView = findViewById(R.id.scrollView2);
        imgTitle = findViewById(R.id.imgTitle);
        tvMovieRatting = findViewById(R.id.tvRatting);
        tvMovieTime = findViewById(R.id.tvTime);
        tvMovieSumary = findViewById(R.id.tvMovieSumary);
        tvMovieActor = findViewById(R.id.tvMovieActor);
        imgBack = findViewById(R.id.btnBack);
        imgFav = findViewById(R.id.btnFav);
        recyclerActor = findViewById(R.id.rvImg);
        recyclerActor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerCategory = findViewById(R.id.rvGenre);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imgBack.setOnClickListener(v -> finish());
    }
}