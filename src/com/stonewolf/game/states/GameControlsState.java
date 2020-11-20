package com.stonewolf.game.states;

import com.stonewolf.game.GamePanel;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;

public class GameControlsState extends GameState {

    public GameControlsState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.esc.tick();

        if (key.esc.clicked) {
            gsm.pop(GameStateManager.CONTROLS);
            gsm.add(GameStateManager.MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(33, 30, 39));
        g.fillRect(0, 0, GamePanel.width,GamePanel.height);

        Sprite.drawArray(g,"Use W A S D to move", new Vector2f(GamePanel.width / 2 - 300, GamePanel.height / 2 - 128), 48,48,30,0);
        Sprite.drawArray(g,"\t Shift to defend", new Vector2f(GamePanel.width / 2 - 300, GamePanel.height / 2 - 84), 48,48,30,0);
        Sprite.drawArray(g,"and Mouse1 to attack", new Vector2f(GamePanel.width / 2 - 300, GamePanel.height / 2 - 40), 48,48,30,0);

        Sprite.drawArray(g,"Press Esc to go back to menu" , new Vector2f(GamePanel.width / 2 - 450, GamePanel.height / 2 + 192), 48,48,30,0);

    }

}
