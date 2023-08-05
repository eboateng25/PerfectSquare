package com.racing121.perfectsquares.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Kojo Ofori on 11/06/2016.
 */
public class BottomPlatform extends GameObject {

    public Sprite spriteLeft;
    public Sprite spriteRight;

    Random ran;

    public static int DISTANCE_WIDTH_FOR_PUBLIC;

    private int DISTANCE_WIDTH;
    public int WIDTH;

    public static float GAP;

    Rectangle leftBounds;
    Rectangle rightBounds;

    float targetLeftX;
    float targetRightX;

    float leftX;
    float rightX;

    public BottomPlatform(int x,int y,TopPlatfrom top){
        this.x = x;
        this.y = y;
        ran = new Random();
        spriteLeft = new Sprite(new Texture("square.png"));
        spriteRight = new Sprite(new Texture("square.png"));
        DISTANCE_WIDTH = 13 + (int)(Math.random() * (28));
        DISTANCE_WIDTH_FOR_PUBLIC = DISTANCE_WIDTH;
        WIDTH = top.getWIDTH() + DISTANCE_WIDTH;
        spriteLeft.setSize(WIDTH,48);
        spriteLeft.setSize(WIDTH,175);
        spriteRight.setPosition(0+(240-WIDTH),y-284);
        spriteRight.setSize(WIDTH,175);
        leftBounds = new Rectangle(x,y-284,spriteLeft.getWidth(),spriteLeft.getHeight());
        rightBounds = new Rectangle(0+(240-WIDTH),y-284,spriteRight.getWidth(),spriteRight.getHeight());
        targetLeftX = x;
        targetRightX = (0+(240-WIDTH));
        spriteLeft.setPosition(x-WIDTH,y - 284);
        spriteRight.setPosition((0+(240-WIDTH))+WIDTH,y - 284);
        leftX = (x-WIDTH) + DISTANCE_WIDTH;
        rightX = ((0+(240-WIDTH))+WIDTH) - DISTANCE_WIDTH;
        GAP = (targetLeftX + WIDTH) - ((0+(240-WIDTH)));
    }

    @Override
    public void render(float dt, SpriteBatch batch) {
        spriteLeft.setPosition(leftX,y - 284);
        spriteRight.setPosition(rightX,y - 284);
        leftX += 350 * dt;
        rightX -= 350 * dt;
        if(leftX >= targetLeftX){
            leftX = targetLeftX;
        }
        if(rightX <= targetRightX){
            rightX = targetRightX;
        }
        spriteLeft.draw(batch);
        //spriteLeft.setPosition(x,y-284);
        spriteRight.draw(batch);
    }

    @Override
    public void update(float dt) {

    }

    public Rectangle getLeftBounds() {
        return leftBounds;
    }

    public Rectangle getRightBounds() {
        return rightBounds;
    }

    public static int getDistanceWidthForPublic() {
        return DISTANCE_WIDTH_FOR_PUBLIC;
    }

    public static float getGAP() {
        return GAP;
    }
}
