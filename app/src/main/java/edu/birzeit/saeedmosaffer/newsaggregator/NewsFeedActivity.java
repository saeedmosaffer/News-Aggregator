package edu.birzeit.saeedmosaffer.newsaggregator;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity {
    private final String url = "https://newsdata.io/api/1/news?apikey=pub_35690d57321c5ad812f6224fc7d698b6dcc37&q=pegasus&language=en";
    private RequestQueue queue;
    private ListView listViewNews;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        listViewNews = findViewById(R.id.listViewNews);
        queue = Volley.newRequestQueue(this);
        newsAdapter = new NewsAdapter(this, new ArrayList<>());

        listViewNews.setAdapter(newsAdapter);

        fetchNewsData();
    }

    private void fetchNewsData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");
                            List<NewsItem> newsItemList = new ArrayList<>();

                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject newsItemJson = resultsArray.getJSONObject(i);
                                NewsItem newsItem = new NewsItem();

                                newsItem.setTitle(newsItemJson.getString("title"));
                                newsItem.setDescription(newsItemJson.getString("description"));

                                newsItemList.add(newsItem);
                            }

                            // Update the adapter with the new data
                            newsAdapter.addAll(newsItemList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("NewsFeedActivity", "Error fetching news data: " + error.toString());
                        Toast.makeText(NewsFeedActivity.this, "Error fetching news data", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
