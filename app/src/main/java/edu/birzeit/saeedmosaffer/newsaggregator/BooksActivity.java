package edu.birzeit.saeedmosaffer.newsaggregator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BooksActivity extends AppCompatActivity {
    private final String url = "https://openlibrary.org/search.json";
    private EditText edtBookTitle;
    private RequestQueue queue;
    private TextView txtResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        edtBookTitle = findViewById(R.id.edtBookTitle);
        txtResult = findViewById(R.id.txtBookResult);
        queue = Volley.newRequestQueue(this);

        Button btnShowBook = findViewById(R.id.btnShowBook);
        btnShowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnShowBook_Click(view);
            }
        });
    }

    public void btnShowBook_Click(View view) {
        String bookTitle = edtBookTitle.getText().toString();
        if (bookTitle.equals("")) {
            txtResult.setText("Enter Book Title");
        } else {
            String str = url + "?title=" + bookTitle;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, str,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray docsArray = jsonObject.getJSONArray("docs");

                                if (docsArray.length() > 0) {
                                    JSONObject bookObj = docsArray.getJSONObject(0);

                                    String title = bookObj.getString("title");
                                    String author = bookObj.getString("author_name");
                                    String publishYear = bookObj.optString("first_publish_year", "N/A");

                                    result = "Title: " + title + "\n";
                                    result += "Author: " + author + "\n";
                                    result += "First Publish Year: " + publishYear;

                                    txtResult.setText(result);

                                    InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    input.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                } else {
                                    txtResult.setText("Book not found");
                                }
                            } catch (JSONException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BooksActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(stringRequest);
        }
    }
}
