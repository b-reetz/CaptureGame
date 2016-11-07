package com.example.mwa5.project204;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by mwa5 on 1/10/15.
 */
public class Balloons {

    protected Random random = new Random();
    protected int balloonX;
    protected int balloonY;
    protected int balloonSpeed;
    protected Paint balloonColor = new Paint();
    protected int balloonRadius = 100;
    //private String difficultyLevel;
    private int colorNum;
    private int dx = 5;
    private String color;
    private String[] colorArray = {"Red", "Yellow", "Green", "Blue"};

    public Balloons(Canvas c, Context context) {
        colorNum = random.nextInt(4);
        balloonColor.setColor(context.getResources().getIntArray(R.array.rainbow)[colorNum]);
        color = colorArray[colorNum];
        balloonX = 0 - balloonRadius;
        balloonY = random.nextInt((c.getHeight() - balloonRadius) - balloonRadius + 1) + balloonRadius;
        balloonSpeed  = random.nextInt(5);
        //this.difficultyLevel = difficultyLevel;
        //this.dx = setDX();
    }

    public void drawBalloon(Canvas c) {
        c.drawCircle(balloonX, balloonY, balloonRadius, balloonColor);
        balloonX += balloonSpeed + dx;
    }

    public int getBalloonX() {
        return balloonX;
    }

    public int getBalloonY() {
        return balloonY;
    }

    public int getBalloonRadius() {

        return balloonRadius;
    }

    public String getBalloonColor() {
        return color;
    }

    /*public int setDX() {
        if(difficultyLevel.compareTo("Kitten") == 0)
            return 5;
        else if (difficultyLevel.compareTo("House Cat") == 0)
            return 10;
        else if (difficultyLevel.compareTo("Tomcat") == 0)
            return 15;
        else
            return 20;
    }*/


}
