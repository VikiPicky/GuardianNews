package com.example.guardiannewslast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Guardian News");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        EditText editText = (EditText) findViewById(R.id.home_editText);
        Button button = (Button) findViewById(R.id.home_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.requestFocus();
                editText.setSelection(0);
                String value = editText.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, SearchTable.class);
                intent.putExtra("search_key", value);
                startActivity(intent);

            }
        });
        loadData();

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyNamePreferences", MODE_PRIVATE);
        String newName = sharedPreferences.getString("name", "");
        String newGreeting = "Hi! I am " + newName + ". " + "Nice to meet you!";

        TextView textViewNewName = (TextView) findViewById(R.id.name_greetingWithNewname);
        textViewNewName.setText(newGreeting);
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
                Intent intent=new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.help_main));
                alertDialog.create().show();
                break;
            case R.id.savedArticles:
                intent = new Intent(MainActivity.this, SavedNews.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected (MenuItem item){

        switch (item.getItemId()) {
            case R.id.item_Home:
                Intent intent=new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_saved:
                intent = new Intent(MainActivity.this, SavedNews.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.name:
                intent = new Intent(MainActivity.this, NameSaver.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_exit:
                finishAffinity();
                System.exit(0);
                break;
        }
        return false;
    }
}