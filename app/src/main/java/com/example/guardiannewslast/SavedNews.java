package com.example.guardiannewslast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SavedNews extends AppCompatActivity {

    private ListView listView;
    private MyTitleAdapter news_items_Adapter;
    private ArrayList<Article> news_items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        listView = (ListView) findViewById(R.id.listview_saved_news);
        listView.setAdapter(news_items_Adapter = new MyTitleAdapter());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        loadDataFromDatabase();

        ContentValues newRowValues = new ContentValues();

        String title = getIntent().getStringExtra("title");
        if (title != null) {
            String section = getIntent().getStringExtra("section");
            String date = getIntent().getStringExtra("date");
            String url = getIntent().getStringExtra("url");

            newRowValues.put(DatabaseOpener.COLUMN_Title, title);
            newRowValues.put(DatabaseOpener.COLUMN_Section, section);
            newRowValues.put(DatabaseOpener.COLUMN_Date, date);
            newRowValues.put(DatabaseOpener.COLUMN_URL, url);

            DatabaseOpener dbOpener = new DatabaseOpener(this);
            SQLiteDatabase sqLiteDatabase = dbOpener.getWritableDatabase();
            long newID = sqLiteDatabase.insert(DatabaseOpener.DATABASE_TABLE, null, newRowValues);
            //create object
            Article likedItem = new Article(newID, title, section, date, url);

            news_items.add(likedItem);
        }
        news_items_Adapter.notifyDataSetChanged();

        Toast.makeText(this, R.string.article_saved, Toast.LENGTH_LONG).show();

        dialogCall();
        openDetails();
    }

    private class MyTitleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news_items.size();
        }

        @Override
        public Object getItem(int position) {

            return news_items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @Override
        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            if (newView == null) {
                newView = inflater.inflate(R.layout.saved_news_textview_for_listview, parent, false);
            }
            TextView textView = newView.findViewById(R.id.savedNews_TextViewforListView);
            textView.setText(news_items.get(position).getTitle());
            return newView;
        }

    }

    private void loadDataFromDatabase() {
        DatabaseOpener dbOpener = new DatabaseOpener(this);
        SQLiteDatabase sqLiteDatabase = dbOpener.getWritableDatabase();

        String[] columns = {DatabaseOpener.COLUMN_ID, DatabaseOpener.COLUMN_Title, DatabaseOpener.COLUMN_Section, DatabaseOpener.COLUMN_Date, DatabaseOpener.COLUMN_URL};
        //query all the results from the database:
        Cursor results = sqLiteDatabase.query(false, DatabaseOpener.DATABASE_TABLE, columns, null, null, null, null, null, null);

        int columnIndex = results.getColumnIndex(DatabaseOpener.COLUMN_ID);
        int columnTitle = results.getColumnIndex(DatabaseOpener.COLUMN_Title);
        int columnSection = results.getColumnIndex(DatabaseOpener.COLUMN_Section);
        int columnDate = results.getColumnIndex(DatabaseOpener.COLUMN_Date);
        int columnUrl = results.getColumnIndex(DatabaseOpener.COLUMN_URL);

        while (results.moveToNext()) {
            long id = results.getLong(columnIndex);
            String title = results.getString(columnTitle);
            String section = results.getString(columnSection);
            String date = results.getString(columnDate);
            String url = results.getString(columnUrl);


            news_items.add(new Article(
                    id, title, section, date, url));

        }
    }

    protected void deleteContact(Article article) {
        DatabaseOpener dbOpener = new DatabaseOpener(this);
        SQLiteDatabase sqLiteDatabase = dbOpener.getWritableDatabase();

        try {
            int result = sqLiteDatabase.delete(DatabaseOpener.DATABASE_TABLE, DatabaseOpener.COLUMN_ID + "= ?", new String[]{Long.toString(article.getId())});

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                Intent intent = new Intent(SavedNews.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SavedNews.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.dialog_help_saved_news));
                alertDialog.create().show();
                break;
        }
        return true;
    }

    public void openDetails() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Article selectedArticle = news_items.get(position);
                String title = selectedArticle.getTitle();
                String section = selectedArticle.getSection();
                String url = selectedArticle.getUrl();
                String date = selectedArticle.getDate();


                Intent intent = new Intent(SavedNews.this, SavedNewsDetails.class);

                intent.putExtra("url", url);
                intent.putExtra("title", title);
                intent.putExtra("section", section);
                intent.putExtra("date", date);
                startActivity(intent);

            }
        });
    }



    //create dialog on ListView long click to remove article from DB

    public void dialogCall() {
        listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

               Article selectedArticle = news_items.get(position);
               Article removedArticle = news_items.get(position);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SavedNews.this);
                alertDialog.setTitle((String) getString(R.string.dialog_delete_saved_article));

                alertDialog.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        news_items.remove(position);

                        deleteContact(selectedArticle);
                        news_items_Adapter.notifyDataSetChanged();

                        Snackbar.make(listView, R.string.snackbar_action, Snackbar.LENGTH_LONG).setAction(R.string.snackbar_undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                news_items.add(removedArticle);
                                news_items_Adapter.notifyDataSetChanged();
                                Snackbar.make(listView, R.string.snackbar_restored, Snackbar.LENGTH_LONG).show();
                            }
                        }).show();
                    }
                });

                alertDialog.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(SavedNews.this, R.string.dialog_retrun, Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.create().show();
                return true;
            }
        });
    }
}
