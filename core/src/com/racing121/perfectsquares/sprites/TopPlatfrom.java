package com.racing121.perfectsquares.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by Kojo Ofori on 11/06/2016.
 */
public class TopPlatfrom extends GameObject {

    public Sprite spriteLeft;
    public Sprite spriteRight;

    private int MIN_GAP = 120;
    public int WIDTH;
    Random ran;
    Vector3 position;

    Rectangle leftBounds;
    Rectangle rightBounds;

    float targetLeftX;
    float targetRightX;

    float leftX;
    float rightX;

    float leftVelX;
    float rightVelX;

    public boolean ANIMATION_FINISHED;

    public TopPlatfrom(int x, int y, OrthographicCamera cam){
        this.x = x;
        this.y = y;
        position = new Vector3(x,y,0);
        spriteLeft = new Sprite(new Texture("square.png"));
        spriteRight = new Sprite(new Texture("square.png"));
        ran = new Random();
        WIDTH = 90 + (int)(Math.random() * (190-MIN_GAP));
        spriteLeft.setSize(WIDTH,48);
        spriteRight.setPosition(0+(240-WIDTH),y);
        spriteRight.setSize(WIDTH,48);
        leftBounds = new Rectangle(x,y,spriteLeft.getWidth(),spriteLeft.getHeight());
        rightBounds = new Rectangle(0+(240-WIDTH),y,spriteRight.getWidth(),spriteRight.getHeight());
        targetLeftX = x;
        targetRightX = (0+(240-WIDTH));
        spriteLeft.setPosition(x-WIDTH,y);
        spriteRight.setPosition((0+(240-WIDTH))+WIDTH,y);
        leftX = x-WIDTH;
        rightX = (0+(240-WIDTH))+WIDTH;
        leftVelX = 350;
        rightVelX = 350;
        ANIMATION_FINISHED = false;
    }

    @Override
    public void render(float dt, SpriteBatch batch) {
        spriteLeft.setPosition(leftX,y);
        spriteRight.setPosition(rightX,y);
        leftX += leftVelX * dt;
        rightX -= rightVelX * dt;
        if(leftX >= targetLeftX){
            leftX = targetLeftX;
            rightVelX = 0;
            ANIMATION_FINISHED = true;
        }
        if(rightX <= targetRightX){
            rightX = targetRightX;
            leftVelX = 0;
        }


        spriteLeft.draw(batch);
        //spriteLeft.setPosition(x,y);
        position = new Vector3(x,y,0);
        spriteRight.draw(batch);
    }

    @Override
    public void update(float dt) {

    }

    public int getWIDTH() {
        return WIDTH;
    }

    public Rectangle getLeftBounds() {
        return leftBounds;
    }

    public Rectangle getRightBounds() {
        return rightBounds;
    }

    public boolean isANIMATION_FINISHED() {
        return ANIMATION_FINISHED;
    }
}
