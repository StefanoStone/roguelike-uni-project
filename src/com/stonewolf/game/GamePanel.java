package com.stonewolf.game;

import com.stonewolf.game.states.GameStateManager;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements Runnable {

    public static int width;
    public static int height;
    public static int oldFrameCount;

    private Thread thread;
    private boolean running = false;

    private BufferedImage image;
    private Graphics2D g;

    private MouseHandler mouse;
    private KeyHandler key;

    private GameStateManager gsm;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }


    //metodo chiamato quando il JPanel viene generato
    @Override
    public void addNotify() {
        super.addNotify();

        if (thread == null)
            thread = new Thread(this, "GameThread");
        thread.start();
    }

    private void init() {
        running = true;

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();

        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        gsm = new GameStateManager();
    }

    @Override
    public void run() {
        init();

        final double GAME_HERTZ = 75.0;
        final double TIME_BEFORE_UPDATE = 1000000000 / GAME_HERTZ;

        final int MUBR = 5; // Must Update Before Render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 1000;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        oldFrameCount = 0;

        while (running) {

            double now = System.nanoTime();
            int updateCount = 0;
            while (((now - lastUpdateTime) > TIME_BEFORE_UPDATE) && (updateCount < MUBR)) {
                update();
                input(mouse, key);
                lastUpdateTime += TIME_BEFORE_UPDATE;
                updateCount++;
            }

            if(now - lastUpdateTime > TIME_BEFORE_UPDATE) {
                lastUpdateTime = now - TIME_BEFORE_UPDATE;
            }

            input(mouse, key);
            render();
            draw();

            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if(thisSecond > lastSecondTime) {
                if (frameCount != oldFrameCount) {
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }
            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TIME_BEFORE_UPDATE) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("ERROR: yielding thread");
                }
                now = System.nanoTime();
            }
        }
    }

    private void update() {
        gsm.update();
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

    private void render() {
        if(g != null) {
            g.setColor(new Color(33, 30, 39));
            g.fillRect(0, 0, width, height);

            gsm.render(g);
        }
    }

    private void draw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image,0 ,0, width, height, null);
        g2.dispose();
    }

}
