package com.stonewolf.game.states;

import com.stonewolf.game.GamePanel;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MenuState extends GameState {

    private BufferedImage img;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        try {
            InputStream in = getClass().getResourceAsStream("/util/MenuImage.png");
            img = ImageIO.read(in);
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
        key.enter.tick();
        key.control.tick();

        if (key.esc.clicked) {
            System.exit(0);
        }
        if (key.control.clicked) {
            gsm.pop(GameStateManager.MENU);
            gsm.paused = false;
            gsm.add(GameStateManager.CONTROLS);
        }
        if (key.enter.clicked) {
            gsm.pop(GameStateManager.MENU);
            gsm.paused = false;
            gsm.add(GameStateManager.PLAY);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(img, 0, 0,GamePanel.width,GamePanel.height, null);
        Sprite.drawArray(g,"2D DUNGEON", new Vector2f(GamePanel.width / 2 - 400, GamePanel.height / 2 - 300), 128,128,80,0);
        Sprite.drawArray(g,"Press Enter to play" , new Vector2f(GamePanel.width / 2 - 350, GamePanel.height / 2 + 200), 48,48,35,0);
        Sprite.drawArray(g,"Press Ctrl for bindings" , new Vector2f(GamePanel.width / 2 - 400, GamePanel.height / 2 + 250), 48,48,35,0);
        Sprite.drawArray(g,"Press Esc to exit" , new Vector2f(GamePanel.width / 2 - 310, GamePanel.height / 2 + 300), 48,48,35,0);
    }
}
