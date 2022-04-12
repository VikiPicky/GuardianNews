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

    private ListView listView;

    String NewsID, NewsSection, NewsTitle, NewsUrl, NewsDate;

    private DrawerLayout drawerLayout;

    private static String JSON_URL = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=";

    ArrayList<HashMap<String, String>> NewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_table);

        setTitle("SearchTable 1.0");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String value = getIntent().getStringExtra("search_key");

        NewsList = new ArrayList<>();
        listView = findViewById(R.id.listview);

        String fullURL = JSON_URL+value;

        GetData getData = new GetData();
        getData.execute(fullURL);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.icon_search:
                Intent intent=new Intent(SearchTable.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchTable.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.help_search_news));
                alertDialog.create().show();
                break;
        }
        return true;
    }

    public class GetData extends AsyncTask<String, String, String> {

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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject newsJsonResponse = jsonObject.getJSONObject("response");

                Log.d("tag", newsJsonResponse.toString()); ///

                JSONArray jsonArray = newsJsonResponse.getJSONArray("results");

                Log.d("tag", jsonArray.toString());  ///

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    NewsID = jsonObject1.getString("id");
                    NewsSection = jsonObject1.getString("sectionName");
                    NewsTitle = jsonObject1.getString("webTitle");
                    NewsUrl = jsonObject1.getString("webUrl");
                    NewsDate = jsonObject1.getString("webPublicationDate");

                    HashMap<String, String> news = new HashMap<>();
                    news.put("id", NewsID);
                    news.put("sectionName", NewsSection);
                    news.put("webTitle", NewsTitle);
                    news.put("webUrl", NewsUrl);
                    news.put("webPublicationDate", NewsDate);

                    NewsList.add(news);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // display results to new layout with rows
            ListAdapter adapter = new SimpleAdapter(
                    SearchTable.this,
                    NewsList,
                    R.layout.list_view_row_layout,
                    new String[]{"webTitle"},
                    new int[]{R.id.textViewTitle});

            listView.setAdapter(adapter);


            //OnClick event fot ListItems to date data to next activity

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    ArrayList<HashMap<String, String>> tempList = new ArrayList<>();
                    tempList.add(NewsList.get(position));

                    String currentUrl = NewsList.get(position).get("webUrl");
                    String currentTitle = NewsList.get(position).get("webTitle");
                    String currentSection = NewsList.get(position).get("sectionName");
                    String currentDate = NewsList.get(position).get("webPublicationDate");

                    Intent intent = new Intent(SearchTable.this, SearchDetailedResult.class);


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