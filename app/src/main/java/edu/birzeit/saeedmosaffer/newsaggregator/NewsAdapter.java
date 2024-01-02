package edu.birzeit.saeedmosaffer.newsaggregator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    public NewsAdapter(Context context, List<NewsItem> newsItemList) {
        super(context, 0, newsItemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        NewsItem newsItem = getItem(position);

        TextView txtTitle = convertView.findViewById(R.id.txtNewsTitle);
        TextView txtDescription = convertView.findViewById(R.id.txtNewsDescription);

        txtTitle.setText(newsItem.getTitle());
        txtDescription.setText(newsItem.getDescription());

        return convertView;
    }
}
