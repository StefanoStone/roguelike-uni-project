package com.stonewolf.game.states;

import com.stonewolf.game.GamePanel;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.utils.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PauseState extends GameState {


    private BufferedImage blur;

    public PauseState(GameStateManager gsm) {
        super(gsm);
        try {
            InputStream in = getClass().getResourceAsStream("/util/Blur.png");
            blur = ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("ERROR: image not loaded.");
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.esc.tick();
        key.control.tick();
        if (key.esc.clicked) {
            gsm.pop(GameStateManager.PAUSE);
            gsm.paused = false;
        }
        if (key.control.clicked) {
            gsm.pop(GameStateManager.PAUSE);
            gsm.pop(GameStateManager.PLAY);
            gsm.paused = false;
            gsm.add(GameStateManager.MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(blur, 0, 0,GamePanel.width,GamePanel.height, null);
        Sprite.drawArray(g,"PAUSE", new Vector2f(GamePanel.width / 2 - 315, GamePanel.height / 2 - 64), 128,128,128,0);
        Sprite.drawArray(g,"If you want to get back to" , new Vector2f(GamePanel.width / 2 - 500, GamePanel.height / 2 + 192), 48,48,35,0);
        Sprite.drawArray(g,"\t main menu, press ctrl" , new Vector2f(GamePanel.width / 2 - 500, GamePanel.height / 2 + 256), 48,48,35,0);
    }

}
