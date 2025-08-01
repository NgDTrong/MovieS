package app.movies.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.movies.Adapter.SearchAdapter;
import app.movies.Model.Item;
import app.movies.Model.Search;
import app.movies.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SearchActivity extends AppCompatActivity {
    private EditText edtSearch;
    private RecyclerView rvSearch;
    private SearchAdapter searchAdapter;
    private Handler handler = new Handler();
    private Runnable debounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        edtSearch = findViewById(R.id.edtSearch);
        rvSearch = findViewById(R.id.rv_search);
        searchAdapter = new SearchAdapter(new ArrayList<>(), this);
        rvSearch.setAdapter(searchAdapter);
        rvSearch.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String keyword = edtSearch.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    callApi(keyword);
                }
                return true;
            }
            return false;
        });
//        edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                handler.removeCallbacks(debounce);
//                String query = s.toString().trim();
//                debounce = new Runnable() {
//
//                    @Override
//                    public void run() {
//                        if (!query.isEmpty()) {
//
//                            callApi(query);
//
//                        } else {
//                            runOnUiThread(() -> {
//                                rvSearch.setAdapter(null);
//                            });
//
//                        }
//
//                    }
//                };
//                handler.postDelayed(debounce, 500);
//
//
//            }
//
//        });
    }

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build();

    private void callApi(String query) {
        long totalStart = System.currentTimeMillis();
        int page = 1;
        int limit = 10;
        String sortField = "";
        String sortType = "";
        String sortLang = "";
        String category = "";
        String country = "";
        String year = "";

        String url = "https://phimapi.com/v1/api/tim-kiem"
                + "?keyword=" + Uri.encode(query)
                + "&page=" + page
                + "&sort_field=" + sortField
                + "&sort_type=" + sortType
                + "&sort_lang=" + sortLang
                + "&category=" + category
                + "&country=" + country
                + "&year=" + year
                + "&limit=" + limit;

        Log.d("PERF", "🔍 Call API started at: " + totalStart);
        Log.d("PERF", "🔗 Request URL: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        long apiCallStart = System.currentTimeMillis();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("API", "❌ Request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                long apiResponseTime = System.currentTimeMillis() - apiCallStart;
                Log.d("PERF", "✅ API responded in: " + apiResponseTime + "ms");

                if (!response.isSuccessful()) {
                    Log.e("API", "❌ Response not successful: " + response.code());
                    return;
                }

                String jsonString = response.body().string();

                try {
                    long parseStart = System.currentTimeMillis();
                    Gson gson = new Gson();
                    Search responseData = gson.fromJson(jsonString, Search.class);
                    long parseTime = System.currentTimeMillis() - parseStart;
                    Log.d("PERF", "📦 JSON parsed in: " + parseTime + "ms");

                    runOnUiThread(() -> {
                        long startTime = System.currentTimeMillis();
                        if (responseData != null && responseData.getData() != null && responseData.getData().getItems() != null) {
                            List<Item> list = responseData.getData().getItems();
                            searchAdapter.updateData(list);
                            Log.d("PERF", "🔄 Adapter updated in: " + (System.currentTimeMillis() - startTime) + "ms");
                        } else {
                            Log.w("API", "⚠️ No data or items returned.");
                            searchAdapter.updateData(new ArrayList<>());
                        }
                    });
                } catch (Exception e) {
                    Log.e("API", "❌ JSON parsing error: " + e.getMessage());
                }
            }
        });
    }


}