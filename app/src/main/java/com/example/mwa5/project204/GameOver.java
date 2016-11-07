package com.example.mwa5.project204;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameOver extends ActionBarActivity {

    private final boolean HIDE_ACTION_BAR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        if (HIDE_ACTION_BAR) {
            getSupportActionBar().hide();
        }

        TextView textView = (TextView) findViewById(R.id.textViewYourScore);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        textView.setText(Integer.toString(score));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void menu(View view) {
        Intent intent = new Intent(this, StartScreen.class);
        startActivity(intent);
        finish();
    }
}

