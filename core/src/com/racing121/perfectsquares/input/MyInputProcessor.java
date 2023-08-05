package com.racing121.perfectsquares.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.racing121.perfectsquares.PerfectSquares;
import com.racing121.perfectsquares.screens.LevelUpScreen;
import com.racing121.perfectsquares.screens.MainMenuScreen;
import com.racing121.perfectsquares.screens.PlayScreen;
import com.racing121.perfectsquares.sprites.Square;
import com.racing121.perfectsquares.states.State;
import com.racing121.perfectsquares.states.Status;

/**
 * Created by Kojo Ofori on 17/06/2016.
 */
public class MyInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 mouse = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        Vector3 unmouse = MainMenuScreen.getCam().unproject(mouse);
        if(State.getSTATE() == Status.MAIN_MENU && new Rectangle(0-150/2,0+40,150,80).contains(new Vector2(unmouse.x,unmouse.y))) {
            MainMenuScreen.CLICKED = true;
            PlayScreen.ranColour = MainMenuScreen.getRanColour();
        }
        if(State.getSTATE() == Status.MAIN_MENU &&
                new Rectangle(0-150/2,(0-220)+40,150,80).contains(new Vector2(unmouse.x,unmouse.y)) &&
                !MainMenuScreen.gdxFacebook.isSignedIn()) {
            MainMenuScreen.LOGIN = true;
            Gdx.audio.newSound(Gdx.files.internal("click.wav")).play();
        }
        if(State.getSTATE() == Status.MAIN_MENU &&
                new Rectangle(0-150/2,(0-220)+40,60,80).contains(new Vector2(unmouse.x,unmouse.y)) &&
                MainMenuScreen.gdxFacebook.isSignedIn()) {
            MainMenuScreen.LOGOUT = true;
        }
        if(State.getSTATE() == Status.LEVELUP) {
            PerfectSquares.SCORE = 0;
            LevelUpScreen.setPLAY(true);
        }
        if(State.getSTATE() == Status.PLAYING) {
            Square.setReleased(true);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
