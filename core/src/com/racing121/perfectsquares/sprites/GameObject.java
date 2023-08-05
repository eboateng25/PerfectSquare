package com.racing121.perfectsquares.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Kojo Ofori on 11/06/2016.
 */
public abstract class GameObject {

    public float x,y;
    public Sprite sprite;

    public abstract void render(float dt, SpriteBatch batch);
    public abstract void update(float dt) throws InterruptedException;

}
