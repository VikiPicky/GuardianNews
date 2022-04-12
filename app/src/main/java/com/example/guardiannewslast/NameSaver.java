package com.example.guardiannewslast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.Toast;

public class NameSaver extends AppCompatActivity {

    private TextView textViewNewName;
    private TextView textViewGreeting;
    private EditText editText;
    private Button applyTextButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_saver);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewGreeting = (TextView) findViewById(R.id.name_textview_greeting);
        textViewNewName = (TextView) findViewById(R.id.name_greetingWithNewname);


        editText = (EditText) findViewById(R.id.name_editview);
        applyTextButton = (Button) findViewById(R.id.name_button);

        applyTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editTextValue = editText.getText().toString();

                textViewGreeting.setVisibility(View.INVISIBLE);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(TEXT, editTextValue);
                editor.apply();

                loadData();
                editText.setText("");

                Toast.makeText(NameSaver.this, R.string.name_saved, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String newName = sharedPreferences.getString(TEXT, "");
        String newGreeting = "Hi! I am " + newName + ". " + "Nice to meet you!";
        textViewNewName.setText(newGreeting);

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
                Intent intent = new Intent(NameSaver.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NameSaver.this);
                alertDialog.setTitle((String) getString(R.string.dialog_title));
                alertDialog.setMessage((String) getString(R.string.help_nameSaver));
                alertDialog.create().show();
                break;
        }
        return true;
    }
}