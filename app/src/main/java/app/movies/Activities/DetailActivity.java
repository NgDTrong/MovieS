package app.movies.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import app.movies.R;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest;
    private ProgressBar mProgressBar;
    private TextView tvTitle,tvMovieRatting,tvMovieTime,tvMovieSumary,tvMovieActor;
    private int idFilm;
    private ImageView imgTitle,imgBack,imgFav;
    private RecyclerView.Adapter adapterActorList, adapterCategory;
    private RecyclerView recyclerActor,recyclerCategory;
    private NestedScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        idFilm=getIntent().getIntExtra("id",0);
        initView();
        sendRequest();
    }

    private void sendRequest() {
    }

    private void initView() {
        tvTitle=findViewById(R.id.tvMovieName);
        mProgressBar=findViewById(R.id.progressBarDetail);
        scrollView=findViewById(R.id.scrollView2);
        imgTitle=findViewById(R.id.imgTitle);
        tvMovieRatting=findViewById(R.id.tvRatting);
        tvMovieTime=findViewById(R.id.tvTime);
        tvMovieSumary=findViewById(R.id.tvMovieSumary);
        tvMovieActor=findViewById(R.id.tvMovieActor);
        imgBack=findViewById(R.id.btnBack);
        imgFav=findViewById(R.id.btnFav);
        recyclerActor=findViewById(R.id.rvImg);
        recyclerActor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerCategory=findViewById(R.id.rvGenre);
        recyclerCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        imgBack.setOnClickListener(v-> finish());
    }
}