package com.stonewolf.game.states;

import com.stonewolf.game.GamePanel;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;

public class GameOverState extends GameState {

    public GameOverState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.enter.tick();

        if (key.enter.clicked) {
            gsm.pop(com.stonewolf.game.states.GameStateManager.GAMEOVER);
            gsm.add(com.stonewolf.game.states.GameStateManager.MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(33, 30, 39));
        g.fillRect(0, 0, GamePanel.width,GamePanel.height);

        Sprite.drawArray(g,"GAME OVER", new Vector2f(GamePanel.width / 2 - 300, GamePanel.height / 2 - 64), 96,96,64,0);
        if (gsm.playerLives == 0)
            Sprite.drawArray(g,"You Lost" , new Vector2f(GamePanel.width / 2 - 150, GamePanel.height / 2 + 100), 48,48,30,0);
        else
            Sprite.drawArray(g,"You Win" , new Vector2f(GamePanel.width / 2 - 150, GamePanel.height / 2 + 100), 48,48,30,0);

        Sprite.drawArray(g,"Press Enter to go back to menu" , new Vector2f(GamePanel.width / 2 - 450, GamePanel.height / 2 + 192), 48,48,30,0);

    }

}
