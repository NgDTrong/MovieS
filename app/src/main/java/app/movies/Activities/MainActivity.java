package app.movies.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import app.movies.Adapter.CategoryAdapter;
import app.movies.Adapter.FilmListAdapter;
import app.movies.Adapter.SliderAdapter;
import app.movies.Model.Category;
import app.movies.Model.ListFilm;
import app.movies.Model.Movie;
import app.movies.Model.SliderItem;
import app.movies.R;

public class MainActivity extends AppCompatActivity {
    RecyclerView.Adapter adapterBestMovie, adapterUpcoming, adapterCategory;
    private RecyclerView rvBestMovie, rvCategory, rvUpdateNew;
    private RequestQueue requestQueue;
    private StringRequest stringRequest, stringRequest2, stringRequest3;
    private ProgressBar load1, load2, load3;
    private ViewPager2 viewPager2;
    private TextView tvSearch;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initView();
        banners();
        sendRequest();
        sendRequestCategory();
        sendRequestUpcoming();

    }


    private void sendRequest() {
        requestQueue = Volley.newRequestQueue(this);
        load1.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, "https://phimapi.com/danh-sach/phim-moi-cap-nhat-v2?page=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                load1.setVisibility(View.GONE);
                ListFilm item = gson.fromJson(s, ListFilm.class);
                adapterBestMovie = new FilmListAdapter(item);
                rvBestMovie.setAdapter(adapterBestMovie);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                load1.setVisibility(View.GONE);
                Log.e("TAG", "onrespone" + volleyError.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    private void sendRequestCategory() {
        requestQueue = Volley.newRequestQueue(this);
        load2.setVisibility(View.VISIBLE);
        stringRequest2 = new StringRequest(Request.Method.GET, "https://phimapi.com/the-loai", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                load2.setVisibility(View.GONE);
                ArrayList<Movie> item = gson.fromJson(s, new TypeToken<ArrayList<Movie>>(){}.getType());
                adapterCategory = new CategoryAdapter(item);
                rvCategory.setAdapter(adapterCategory);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                load2.setVisibility(View.GONE);
                Log.e("TAG", "onrespone" + volleyError.toString());
            }
        });
        requestQueue.add(stringRequest2);
    }

    private void sendRequestUpcoming() {
        requestQueue = Volley.newRequestQueue(this);
        load3.setVisibility(View.VISIBLE);
        stringRequest3 = new StringRequest(Request.Method.GET, "https://phimapi.com/danh-sach/phim-moi-cap-nhat-v2?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                load3.setVisibility(View.GONE);
                ListFilm item = gson.fromJson(s, ListFilm.class);
                adapterUpcoming = new FilmListAdapter(item);
                rvUpdateNew.setAdapter(adapterUpcoming);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                load3.setVisibility(View.GONE);
                Log.e("TAG", "onrespone" + volleyError.toString());
            }
        });
        requestQueue.add(stringRequest3);
    }

    private void banners() {
        List<SliderItem> list = new ArrayList<>();
        list.add(new SliderItem(R.drawable.wide));
        list.add(new SliderItem(R.drawable.wide1));
        list.add(new SliderItem(R.drawable.wide3));
        viewPager2.setAdapter(new SliderAdapter(list, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runSlider);
            }
        });
    }

    private Runnable runSlider = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPager2.getCurrentItem();
            int totalItem = viewPager2.getAdapter().getItemCount();
            if (currentItem == totalItem - 1) {
                viewPager2.setCurrentItem(0, true);
            } else {
                viewPager2.setCurrentItem(currentItem + 1, true);
            }
            handler.postDelayed(this, 3000);
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runSlider);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runSlider, 1000);
    }

    private void initView() {
        tvSearch=findViewById(R.id.edtSearch);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        viewPager2 = findViewById(R.id.viewSlider);
        rvBestMovie = findViewById(R.id.rvBestMovie);
        rvBestMovie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory = findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvUpdateNew = findViewById(R.id.rvUpdateNew);
        rvUpdateNew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        load1 = findViewById(R.id.progressBar1);
        load2 = findViewById(R.id.progressBar2);
        load3 = findViewById(R.id.progressBar3);
    }
}