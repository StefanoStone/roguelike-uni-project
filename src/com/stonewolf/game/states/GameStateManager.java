package com.stonewolf.game.states;

import com.stonewolf.game.GamePanel;
import com.stonewolf.game.graphics.Font;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;


public class GameStateManager {

    public static Vector2f map;

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;
    public static final int CONTROLS = 4;

    public static Font font;

    public boolean paused;

    protected int playerLives;

    private GameState states[];

    public GameStateManager() {
        map = new Vector2f(GamePanel.width, GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);

        states = new GameState[5];

        font = new Font("font/font.png", 10, 10);
        Sprite.currentFont = font;

        states[MENU] = new MenuState(this);
    }

    public void pop(int state) {
        states[state] = null;
    }

    public void add(int state) {

        if (states[state] != null) {
            return;
        }

        if(state == PLAY) {
            states[PLAY] = new PlayState(this);
        }
        if(state == MENU) {
            states[MENU] = new MenuState(this);
        }
        if(state == PAUSE) {
            states[PAUSE] = new PauseState(this);
        }
        if(state == GAMEOVER) {
            states[GAMEOVER] = new GameOverState(this);
        }

        if(state == CONTROLS) {
            states[CONTROLS] = new GameControlsState(this);
        }
    }

    public boolean getState(int state) {
        return states[state] != null;
    }


    public void update() {
        Vector2f.setWorldVar(map.x, map.y);
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null)
                states[i].update();

        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null)
                if (states[PAUSE] == null)
                states[i].input(mouse, key);
            else {
                states[PAUSE].input(mouse, key);
            }
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null)
                states[i].render(g);
        }
    }

    public void setGameIsOver(int lives) {
        playerLives = lives;
        this.pop(PLAY);
        this.add(GAMEOVER);
    }
}
