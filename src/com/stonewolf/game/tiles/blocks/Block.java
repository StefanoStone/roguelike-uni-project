package com.stonewolf.game.tiles.blocks;

import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Block {

    protected int width;
    protected int height;

    protected BufferedImage image;
    protected Vector2f position;

    public Block(BufferedImage image, Vector2f position, int width, int height) {
        this.height = height;
        this.width = width;
        this.image = image;
        this.position = position;
    }

    public abstract boolean update(AABB p);

    public abstract boolean isInside(AABB p);

    public void render(Graphics2D g) {
        g.drawImage(image, (int) position.getWorldVar().x, (int) position.getWorldVar().y, width, height, null);
    }

    public Vector2f getPosition() {
        return position;
    }
}
