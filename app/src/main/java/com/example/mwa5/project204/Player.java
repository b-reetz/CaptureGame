package com.example.mwa5.project204;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by mwa5 on 1/10/15.
 */
public class Player {

    private Canvas c;
    private int size = 50;
    private int posX = 0;
    private int posY = 0;
    private int ACC_X = 0;
    private int ACC_Y = 0;
    private Paint p = new Paint();

    private int colorNum;
    private Context context;
    private String playerColor;
    private int points = 0;
    private int health = 100;
    private int multiplier = 1;
    private int collected = 10;
    private Random random = new Random();
    private String[] colorArray = {"Red", "Yellow", "Green", "Blue"};

    public Player(Context context, int x, int y) {
        posX = x;
        posY = y;
        this.context = context;
        colorNum = random.nextInt(4);
        p.setColor(context.getResources().getIntArray(R.array.rainbow)[colorNum]);
        playerColor = colorArray[colorNum];
    }

    public void getCanvas(Canvas c) {
        this.c = c;
    }

    public void drawPlayer() {
        c.drawCircle(posX, posY, size, p);
        this.updateX();
        this.updateY();
    }

    public void updateX() {
        if(ACC_X > 0) {
            if(((posX - size) - ACC_X) <= 0) {
                posX = size;
            }
            else {
                posX -= ACC_X;
            }
        }
        else if(ACC_X < 0) {
            if(((posX + size) + ACC_X) >= c.getWidth()) {
                posX = c.getWidth() - size;
            }
            else {
                posX -= ACC_X;
            }
        }
    }

    public void updateY() {
        if(ACC_Y > 0) {
            if(((posY + size) + ACC_Y) >= c.getHeight()) {
                posY = c.getHeight() - size;
            }
            else {
                posY += ACC_Y;
            }
        }
        else if(ACC_Y < 0) {
            if(((posY - size) - ACC_Y) <= 0) {
                posY = size;
            }
            else {
                posY += ACC_Y;
            }
        }
    }

    public void setAccelerometer(int x, int y) {
        ACC_X = x;
        ACC_Y = y;
    }

    public boolean detectCollision(Balloons balloon) {
        int balX = balloon.getBalloonX();
        int balY = balloon.getBalloonY();
        int balRad = balloon.getBalloonRadius();

        if (Math.pow((balX - posX), 2) + Math.pow((posY - balY), 2) <= Math.pow((size + balRad), 2)) {

            if(balloon.getBalloonColor().compareTo(this.playerColor) != 0) {
                this.setCollected(false);
                this.setMultiplier(false);
                this.setHealth();
            }
            else {
                this.setCollected(true);
                if(collected == 0) {
                    this.setMultiplier(true);
                    this.setCollected(false);
                }
                this.setPoints();
            }
            return true;
        }

        return false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth() {
        this.health -= 10;
    }

    public void setPlayerColor() {
        colorNum = random.nextInt(4);
        this.p.setColor(context.getResources().getIntArray(R.array.rainbow)[colorNum]);
        this.playerColor = colorArray[colorNum];
    }

    public void setPoints() {
        points += 100 * this.multiplier;
    }

    public void setCollected(boolean collect) {
        if(collect)
            this.collected -= 1;
        else
            this.collected = 10;
    }

    public void setMultiplier(boolean multiply) {
        if(multiply)
            this.multiplier += 1;
        else
            this.multiplier = 1;
    }

    public int getPoints() {
        return this.points;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public int getCollected() {
        return this.collected;
    }
}
