package com.example.guardiannewslast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NameSaver extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private EditText editText;//declare edittext
    private Button applyTextButton; //declare save Button

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_saver);

        setTitle("Guardian News"); // set title

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);  //add toolbar
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);  // add Navigagtion Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editText = (EditText) findViewById(R.id.name_editview);  /// initiate edittext variable
        applyTextButton = (Button) findViewById(R.id.name_button); /// initiate Save button variable

        applyTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTextValue = editText.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("MyNamePreferences", MODE_PRIVATE);  /// on Save button slick store item in SharedPreferences and open Intent to take data to MainActivity
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", editTextValue);
                editor.commit();

                editText.setText(""); // clear edittetx

                Toast.makeText(NameSaver.this, R.string.name_saved, Toast.LENGTH_SHORT).show();  // show toast

                getSupportFragmentManager().beginTransaction().replace(R.id.name_fragment, new BlankFragment()).commit();  // load fragment from BlankFragment with thanks.png

                Intent intent=new Intent(NameSaver.this,MainActivity.class);
                intent.putExtra("name",editTextValue);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // add menu

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  // add menu actions

        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.icon_search:
                Intent intent = new Intent(NameSaver.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NameSaver.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.help_nameSaver));
                alertDialog.create().show();
                break;
                case R.id.savedArticles:
                intent = new Intent(NameSaver.this, SavedNews.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected (MenuItem item){  // add actions on Navigation Drawer

        switch (item.getItemId()) {
            case R.id.item_Home:
                Intent intent=new Intent(NameSaver.this, MainActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_saved:
                intent = new Intent(NameSaver.this, SavedNews.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.name:
                intent = new Intent(NameSaver.this, NameSaver.class);
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