package com.stonewolf.game.tiles.blocks;

import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NormBlock extends Block {

    public NormBlock(BufferedImage image, Vector2f position, int width, int height) {
        super(image, position, width, height);
    }

    @Override
    public boolean update(AABB p) {
        return false;
    }

    @Override
    public boolean isInside(AABB p) {
        return false;
    }


    public void render(Graphics2D g) {
        super.render(g);
    }
}
