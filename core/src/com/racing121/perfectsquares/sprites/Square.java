package com.racing121.perfectsquares.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.racing121.perfectsquares.PerfectSquares;
import com.racing121.perfectsquares.input.MyInputProcessor;
import com.racing121.perfectsquares.screens.PlayScreen;

import java.util.Random;

/**
 * Created by Kojo Ofori on 11/06/2016.
 */
public class Square extends GameObject {

    private float scale;
    private float velY;

    Rectangle bounds;
    TopPlatfrom top;
    BottomPlatform bottom;

    MyInputProcessor input;
    private static boolean released;
    PerfectSquares game;

    float timer;

    public Square(int x, int y,TopPlatfrom top,BottomPlatform bottom,PerfectSquares game){
        this.x = x;
        this.y = y;
        this.top = top;
        this.bottom = bottom;
        this.game = game;
        timer = 0;
        sprite = new Sprite(new Texture("square.png"));
        sprite.setAlpha(0f); //make 0 for the actual game
        //velY = 250;
        scale = 1;
        bounds = new Rectangle(x,y,sprite.getWidth(),sprite.getHeight());
        released = false;
        input = new MyInputProcessor();
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float dt, SpriteBatch batch) {
        update(dt);
        sprite.setPosition(x-(sprite.getWidth() * sprite.getScaleX())/2,y - (sprite.getHeight() * sprite.getScaleY())/2);
        bounds = new Rectangle(x-(sprite.getWidth() * sprite.getScaleX())/2,y - (sprite.getHeight() * sprite.getScaleY())/2,sprite.getWidth() * sprite.getScaleX() ,sprite.getHeight()*sprite.getScaleY());
        sprite.draw(batch);
    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched() && released == false){
            scale += 9 * dt;
            //debug glitch
            if(sprite.getWidth() >= PlayScreen.PERFECT_WIDTH && sprite.getWidth() >= PlayScreen.PERFECT_WIDTH + 0.1f){
                float getScale = scale;
                scale = getScale;
            }
            //limit scale
            if(scale >= 9) {
                scale = 9;
            }else {
                sprite.setSize(sprite.getWidth() + scale, sprite.getHeight() + scale);
            }
        }
        if(released){
            velY = 900;
            sprite.setAlpha(1);
        }
    }

    @Override
    public void update(float dt) {
        Random ran = new Random();
        handleInput(dt);
        if(bounds.overlaps(top.getLeftBounds())){
            PlayScreen.levelText = "OH NO!";
            velY = 0;
            timer += 1 * dt;
            if(timer >= 1) {
                PlayScreen.ranColour = ran.nextInt(6);
                game.restart(false);
            }
        }else if(y <= -200){
            PlayScreen.levelText = "OH NO!";
            velY = 1500;
            timer += 1 * dt;
            if(timer >= 1) {
                PlayScreen.ranColour = ran.nextInt(6);
                game.restart(false);
            }
        }else if (bounds.overlaps(bottom.getLeftBounds())) {
            velY = 0;
            timer += 1 * dt;
            if(timer >= 1) {
                PlayScreen.ranColour = ran.nextInt(6);
                PerfectSquares.SCORE += PlayScreen.POINTS_TO_ADD;
                if(PerfectSquares.SCORE >= PerfectSquares.HIGHSCORE){
                    PerfectSquares.HIGHSCORE = PerfectSquares.SCORE;
                }
                game.restart(true);
            }
        }
        y-=velY * dt;
    }

    public static boolean isReleased() {
        return released;
    }

    public static void setReleased(boolean released) {
        Square.released = released;
    }
}
