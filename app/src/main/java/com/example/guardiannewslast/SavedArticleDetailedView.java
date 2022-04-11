package com.example.guardiannewslast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SavedArticleDetailedView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_article_detailed_view);

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        String section = getIntent().getStringExtra("section");
        String date = getIntent().getStringExtra("date");

        TextView textViewTitle = (TextView) findViewById(R.id.saved_textViewTitle);
        textViewTitle.setText(title);
        TextView textViewSection = (TextView) findViewById(R.id.saved_textViewSection);
        textViewSection.setText(section);
        TextView textViewDate = (TextView) findViewById(R.id.saved_textViewDate);
        textViewDate.setText(date);
    }
}