package com.stonewolf.game.tiles.blocks;

import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjBlock extends Block {

    public ObjBlock(BufferedImage image, Vector2f position, int width, int height) {
        super(image, position, width, height);
    }

    @Override
    public boolean update(AABB p) {
        return true;
    }

    @Override
    public boolean isInside(AABB p) {
        return false;
    }

    public void render(Graphics2D g) {
        super.render(g);
        // g.setColor(Color.white);
        // g.drawRect((int) position.getWorldVar().x, (int) position.getWorldVar().y, width, height);
    }
}
