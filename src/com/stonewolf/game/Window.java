package com.stonewolf.game;

import com.stonewolf.game.utils.MusicPlayer;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Window extends JFrame {

    public Window() {
        this.loadResources();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel(1280, 720));
        setIgnoreRepaint(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void loadResources() {
        setTitle("2D ROGUELIKE GAME");
        MusicPlayer.getInstance().playMusic("/music/DungeonBackgroundMusic.wav", Clip.LOOP_CONTINUOUSLY);
        InputStream imgStream = this.getClass().getResourceAsStream("/util/JarIcon.png");
        try {
            BufferedImage myImg = ImageIO.read(imgStream);
            setIconImage(myImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
