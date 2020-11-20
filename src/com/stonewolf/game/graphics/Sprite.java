package com.stonewolf.game.graphics;

import com.stonewolf.game.utils.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {

    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public static Font currentFont;
    public int width;
    public int height;
    private int wSprite;
    private int hSprite;

    public Sprite(String file) {
        width = TILE_SIZE;
        height = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / width;
        hSprite = SPRITESHEET.getHeight() / height;
        loadSpriteArray();
    }

    public Sprite(String file, int w, int h) {
        this.width = w;
        this.height = h;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
    }


    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    private void setHeight(int height) {
        this.height = height;
        wSprite = SPRITESHEET.getWidth() / width;
    }
    private void setWidth(int width) {
        this.width = width;
        hSprite = SPRITESHEET.getHeight() / height;
    }


    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public BufferedImage getSpriteSheet() {
        return SPRITESHEET;
    }
    public BufferedImage getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * width, y * height, width, height);
    }
    public BufferedImage[] getSpriteArray(int i) {
        return spriteArray[i];

    }
    public BufferedImage[][] getSpriteArray() {
        return spriteArray;
    }


    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try{
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file" + file);
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++ ) {
            for (int  x= 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public static void drawArray(Graphics2D g, ArrayList<BufferedImage> img, Vector2f position,
                                 int width, int height, int xOffset, int yOffset) {
        float x = position.x;
        float y = position.y;

        for (int i = 0; i < img.size(); i++) {
            if (img.get(i) != null) {
                g.drawImage(img.get(i), (int) x, (int) y, width, height, null);
            }

            x += xOffset;
            y += yOffset;
        }
    }

    public static void drawArray(Graphics2D g, String word, Vector2f position,
                                 int size) {
        drawArray(g, word, position, size, size, size, 0);
    }

    public static void drawArray(Graphics2D g, String word, Vector2f position,
                                 int size, int xOffset) {
        drawArray(g, word, position, size, size, xOffset, 0);
    }

    public static void drawArray(Graphics2D g, String word, Vector2f position,
                                 int width, int height, int xOffset) {
        drawArray(g, word, position, width, height, xOffset, 0);
    }


    public static void drawArray(Graphics2D g, String word, Vector2f position,
                                 int width, int height, int xOffset, int yOffset) {
        float x = position.x;
        float y = position.y;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32)
                g.drawImage(currentFont.getFont(word.charAt(i)),(int) x,(int) y, width, height, null);
            x += xOffset;
            y += yOffset;
        }


    }





}
