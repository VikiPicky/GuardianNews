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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detailed_result);

        setTitle("Guardian News"); // set title

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // add tollbar
        setSupportActionBar(toolbar);

        String url = getIntent().getStringExtra("url"); // get data with Intent from SearchTable.class
        String title = getIntent().getStringExtra("title");
        String section = getIntent().getStringExtra("section");
        String newdate = getIntent().getStringExtra("date");
        String date = newdate.substring(0, 10); // trim date String

        likeNews(url, title, section, date);  // parameterized method specified below to add Articles to favourites

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);   // textview to display title
        textViewTitle.setText(title);
        TextView textViewSection = (TextView) findViewById(R.id.textViewSection);// textview to display section
        textViewSection.setText(section);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate); //// textview to display trimmed date
        textViewDate.setText(date);

        ImageButton buttonWeb = (ImageButton) findViewById(R.id.btn_read_online);  // open built-in browser and open URL
        buttonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ImageButton buttonClose = (ImageButton) findViewById(R.id.icon_close); // press X to close activity and return to previous activity
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void likeNews(String url, String title, String section, String date) { // press HEART to create intent to send data to Saved News where it will be turened into object and added to database
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

