package com.example.guardiannewslast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SearchDetailedResult extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detailed_result);

        setTitle("Guardian News");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        String section = getIntent().getStringExtra("section");
        String newdate = getIntent().getStringExtra("date");
        String date = newdate.substring(0, 10);

        likeNews(url, title, section, date);

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setText(title);
        TextView textViewSection = (TextView) findViewById(R.id.textViewSection);
        textViewSection.setText(section);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDate.setText(date);

        ImageButton buttonWeb = (ImageButton) findViewById(R.id.btn_read_online);
        buttonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        ImageButton buttonClose = (ImageButton) findViewById(R.id.icon_close);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void likeNews(String url, String title, String section, String date) {
        ImageButton buttonLike = (ImageButton) findViewById(R.id.icon_love);
        buttonLike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(SearchDetailedResult.this, SavedNews.class);

                intent.putExtra("url", url);
                intent.putExtra("title", title);
                intent.putExtra("section", section);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.icon_search:
                Intent intent = new Intent(SearchDetailedResult.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchDetailedResult.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.help_details));
                alertDialog.create().show();
                break;
            case R.id.savedArticles:
                intent = new Intent(SearchDetailedResult.this, SavedNews.class);
                startActivity(intent);
                break;
        }
        return true;
    }


}

