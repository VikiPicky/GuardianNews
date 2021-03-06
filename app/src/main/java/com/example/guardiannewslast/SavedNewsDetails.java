package com.example.guardiannewslast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SavedNewsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news_details);

        setTitle("Guardian News");  // set title

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);  // add toolbar
        setSupportActionBar(toolbar);

        String url = getIntent().getStringExtra("url");  // start intent and get data from SavedNews
        String title = getIntent().getStringExtra("title");
        String section = getIntent().getStringExtra("section");
        String date = getIntent().getStringExtra("date");

        TextView textViewTitle = (TextView) findViewById(R.id.saved_textViewTitle); // set textviews to received Strings from Intent
        textViewTitle.setText(title);
        TextView textViewSection = (TextView) findViewById(R.id.saved_textViewSection);
        textViewSection.setText(section);
        TextView textViewDate = (TextView) findViewById(R.id.saved_textViewDate);
        textViewDate.setText(date);

        ImageButton buttonWeb = (ImageButton) findViewById(R.id.saved_btn_read_online);  // on click open browser
        buttonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ImageButton buttonClose = (ImageButton) findViewById(R.id.saved_icon_close); // on click close activity and return to previous
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // add menu

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // add menu actions by selection

        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.icon_search:
                Intent intent = new Intent(SavedNewsDetails.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SavedNewsDetails.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.help_details_small));
                alertDialog.create().show();
                break;
            case R.id.savedArticles:
                intent = new Intent(SavedNewsDetails.this, SavedNews.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}