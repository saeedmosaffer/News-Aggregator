package edu.birzeit.saeedmosaffer.newsaggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    public void onViewAllClick(View view) {
        Intent intent = new Intent(this, NewsFeedActivity.class);
        startActivity(intent);
    }

    public void onViewTodayTasksClick(View view) {
        Intent intent = new Intent(this, BooksActivity.class);
        startActivity(intent);
    }

}