package com.example.guardiannewslast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class SearchTable extends AppCompatActivity {

    private ProgressBar progressBar; // declare progressbar

    private ListView listView; // declare listView to display serach results

    String NewsID, NewsSection, NewsTitle, NewsUrl, NewsDate; //declare String to hold data from URL

    private static String JSON_URL = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q="; //declare and initiate standard part of the URL String

    ArrayList<HashMap<String, String>> NewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_table);

        progressBar = findViewById(R.id.progress_circular); // initiate progress bar for AsynkTask

        setTitle("Guardian News");  //set page title

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // declare and initiate tollbar to the top
        setSupportActionBar(toolbar);

        Intent intent = getIntent(); //declare intent to get SearchValue from MainActivity
        String value = getIntent().getStringExtra("search_key");

        NewsList = new ArrayList<>();
        listView = findViewById(R.id.listview); // initiate ListView

        String fullURL = JSON_URL + value; // form final string by compounding String from MainActivity and String JSON above

        GetData getData = new GetData();
        getData.execute(fullURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater(); // inflate menu into toolbar
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) { // declare intents to move between menu selections
            //what to do when the menu item is selected:
            case R.id.icon_search:
                Intent intent = new Intent(SearchTable.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchTable.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.help_search_news));
                alertDialog.create().show();
                break;
            case R.id.savedArticles:
                intent = new Intent(SearchTable.this, SavedNews.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    public class GetData extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProgressBar progressBar = findViewById(R.id.progress_circular);
            progressBar.setVisibility(View.VISIBLE);  //show progressbar
        }

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(strings[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    Log.d("tag", "input stream");

                    int data = inputStreamReader.read();

                    while (data != -1) {
                        current += (char) data;
                        data = inputStreamReader.read();

                        publishProgress(1);
                    }
                    return current;

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("tag", current.toString());
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar.setVisibility(View.INVISIBLE);  // progressbar set visibity to hide as the data is downloaded

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject newsJsonResponse = jsonObject.getJSONObject("response");

                Log.d("tag", newsJsonResponse.toString()); ///

                JSONArray jsonArray = newsJsonResponse.getJSONArray("results");

                Log.d("tag", jsonArray.toString());  ///

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    NewsID = jsonObject1.getString("id"); // get JSON objects
                    NewsSection = jsonObject1.getString("sectionName");
                    NewsTitle = jsonObject1.getString("webTitle");
                    NewsUrl = jsonObject1.getString("webUrl");
                    NewsDate = jsonObject1.getString("webPublicationDate");

                    HashMap<String, String> news = new HashMap<>();  // add JSON objects into HashMap
                    news.put("id", NewsID);
                    news.put("sectionName", NewsSection);
                    news.put("webTitle", NewsTitle);
                    news.put("webUrl", NewsUrl);
                    news.put("webPublicationDate", NewsDate);

                    NewsList.add(news); // add HashMap into ArrayList

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new SimpleAdapter(         // display results to ListView via rows in separate layout with SimpleAdapater
                    SearchTable.this,
                    NewsList,
                    R.layout.list_view_row_layout,
                    new String[]{"webTitle"},
                    new int[]{R.id.textViewTitle});

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//OnClick event fot ListItems to take data to next activity and show more details about selection
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    ArrayList<HashMap<String, String>> tempList = new ArrayList<>();
                    tempList.add(NewsList.get(position)); // form temporary Array by selecting position clicked

                    String currentUrl = NewsList.get(position).get("webUrl");  //get Strings from ArrayList
                    String currentTitle = NewsList.get(position).get("webTitle");
                    String currentSection = NewsList.get(position).get("sectionName");
                    String currentDate = NewsList.get(position).get("webPublicationDate");

                    Intent intent = new Intent(SearchTable.this, SearchDetailedResult.class); // open intent to take data to SearchDetailedResult for processing

                    intent.putExtra("url", currentUrl);
                    intent.putExtra("title", currentTitle);
                    intent.putExtra("section", currentSection);
                    intent.putExtra("date", currentDate);
                    startActivity(intent);
                }
            });
        }
    }
}