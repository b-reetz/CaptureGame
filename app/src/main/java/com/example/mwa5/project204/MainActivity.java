package com.example.mwa5.project204;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private final boolean HIDE_ACTION_BAR = true;
    private SensorManager sensorManager;
    private Sensor ACCELEROMETER_SENSOR;
    private int ACC_X;
    private int ACC_Y;
    private ArrayList<Balloons> balArray = new ArrayList<Balloons>();

    private long balloonTimer = System.currentTimeMillis();
    private long playerTimer = System.currentTimeMillis();

    private Player player;
    private Balloons delete;

    //private String difficultyLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomView cv = new CustomView(this);

        setContentView(cv);

        if (HIDE_ACTION_BAR) {
            getSupportActionBar().hide();
        }

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            ACCELEROMETER_SENSOR = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        else {
            Log.e("Error: ","This device does not have a accelerometer sensor.");
        }

        //Intent intent = getIntent();
        //difficultyLevel = intent.getStringExtra("difficultyLevel");

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, ACCELEROMETER_SENSOR, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == ACCELEROMETER_SENSOR) {
            ACC_X = Math.round(sensorEvent.values[0]) * 2;
            ACC_Y = Math.round(sensorEvent.values[1]) * 2;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor s, int accuracy) {

    }

    public class CustomView extends View {
        private Paint p = new Paint();
        private boolean isInitialised = false;
        private int time;

        public CustomView(Context c) {
            super(c);
            //Set up the paint object
            p.setStyle(Paint.Style.FILL);
            p.setTypeface(Typeface.SANS_SERIF);
            p.setTextSize(50);
        }

        @Override
        protected void onDraw(final Canvas c) {

            p.setColor(Color.LTGRAY);
            c.drawPaint(p);

            if (isInitialised) {

                if (System.currentTimeMillis() > balloonTimer + 500) {
                    balloonTimer = System.currentTimeMillis();
                    Balloons bal = new Balloons(c, MainActivity.this);
                    balArray.add(bal);
                }

                for (Balloons s : balArray) {
                    s.drawBalloon(c);
                    if(player.detectCollision(s)) {
                        delete = s;
                        if(player.getHealth() == 0) {
                            gameOver(player.getPoints());
                        }
                    }
                }

                if(delete != null) {
                    balArray.remove(delete);
                    delete = null;
                }

                p.setColor(Color.WHITE);
                c.drawText("HEALTH: " + player.getHealth(), 25, 100, p);
                c.drawText("SCORE: " + player.getPoints(), 25, 150, p);
                c.drawText("MULTIPLIER: x" + player.getMultiplier(), 25, 200, p);
                //c.drawText("X: " + ACC_X, 25, 250, p);
                //c.drawText("Y: " + ACC_Y, 25, 300, p);
                //c.drawText("DIFFICULTY: " + difficultyLevel, 25, 250, p);
                //c.drawText("COLLECTED: " + player.getCollected(), 25, 300, p);

                player.getCanvas(c);
                player.drawPlayer();
                player.setAccelerometer(ACC_X, ACC_Y);

                if (System.currentTimeMillis() > playerTimer + 5000) {
                    playerTimer = System.currentTimeMillis();
                    player.setPlayerColor();
                }

                invalidate();
            }
        }

        @Override
        public void onWindowFocusChanged(boolean focused) {
            super.onWindowFocusChanged(focused);
            if (!isInitialised) {
                /*if(difficultyLevel.compareTo("Kitten") == 0)
                    time = 500;
                else if(difficultyLevel.compareTo("House Cat") == 0)
                    time = 400;
                else if(difficultyLevel.compareTo("Tomcat") == 0)
                    time = 350;
                else
                    time = 250;
                    */
                player = new Player(MainActivity.this, getWidth() / 2, getHeight() / 2);
                isInitialised = true;
            }
        }
    }

    public void gameOver(int playerScore) {
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("score", playerScore);
        startActivity(intent);
        finish();
    }
}