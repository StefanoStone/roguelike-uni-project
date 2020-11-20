package com.stonewolf.game.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Font {

    private BufferedImage FONTSHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int width;
    public int height;
    private int wLetter;
    private int hLetter;

    public Font(String file) {
        width = TILE_SIZE;
        height = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        FONTSHEET = loadFile(file);

        wLetter = FONTSHEET.getWidth() / width;
        hLetter = FONTSHEET.getHeight() / height;
        loadSpriteArray();
    }

    public Font(String file, int w, int h) {
        this.width = w;
        this.height = h;

        System.out.println("Loading: " + file + "...");
        FONTSHEET = loadFile(file);

        wLetter = FONTSHEET.getWidth() / w;
        hLetter = FONTSHEET.getHeight() / h;
        loadSpriteArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }
    private void setHeight(int height) {
        this.height = height;
        wLetter = FONTSHEET.getWidth() / width;
    }
    private void setWidth(int width) {
        this.width = width;
        hLetter = FONTSHEET.getHeight() / height;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public BufferedImage getFontSheet() {
        return FONTSHEET;
    }
    public BufferedImage getLetter(int x, int y) {
        return FONTSHEET.getSubimage(x * width, y * height, width, height);
    }
    public BufferedImage getFont(char letter) {
        int value = letter;

        int x = value % wLetter;
        int y = value / wLetter;

        return getLetter(x, y);
    }


    private BufferedImage loadFile(String file) {
        BufferedImage sprite = null;
        try{
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file)));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file" + file);
        }
        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new BufferedImage[wLetter][hLetter];

        for (int  x= 0; x < wLetter; x++) {
            for (int y = 0; y < hLetter; y++ ) {
                spriteArray[x][y] = getLetter(x, y);
            }
        }
    }

}
