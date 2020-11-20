package com.stonewolf.game.tiles.blocks;

import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HoleBlock extends Block {

    public HoleBlock(BufferedImage image, Vector2f position, int width, int height) {
        super(image, position, width, height);
    }

    @Override
    public boolean update(AABB p) {

            System.out.println("i am a hole");

        return false;
    }

    @Override
    public boolean isInside(AABB p) {

        if (p.getPosition().x + p.getxOffset() < position.x)
            return false;

        if (p.getPosition().y + p.getyOffset() < position.y)
            return false;

        if (width + position.x < p.getWidth() + (p.getPosition().x + p.getxOffset()))
            return false;

        if (height + position.y < p.getHeight() + (p.getPosition().y + p.getyOffset()))
            return false;

        return true;
    }

    public void render(Graphics2D g) {
        super.render(g);
        // g.setColor(Color.green);
        // g.drawRect((int) position.getWorldVar().x, (int) position.getWorldVar().y, width, height);
    }
}
