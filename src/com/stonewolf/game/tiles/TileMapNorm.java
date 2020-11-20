package com.stonewolf.game.tiles;

import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.tiles.blocks.Block;
import com.stonewolf.game.tiles.blocks.NormBlock;
import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class TileMapNorm extends TileMap {

    private Block[] blocks;

    private int tileWidth;
    private int tileHeight;
    private  int height;

    public TileMapNorm(String data, Sprite sprite, int width, int height,
                       int tileWidth, int tileHeight, int tileColumns) {

        blocks = new Block[width * height];

        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.height = height;

        String[] block = data.split(",");
        for (int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if (temp != 0) {
                blocks[i] = new NormBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns)),
                            new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight),
                            tileWidth, tileHeight);
            }
        }
    }

    @Override
    public void render(Graphics2D g, AABB camera) {
        int x = (int) ((camera.getPosition().getCamVar().x) / tileWidth);
        int y = (int) ((camera.getPosition().getCamVar().y) / tileHeight);
        for (int i = x; i < x + (camera.getWidth() / tileWidth); i++) {
            for (int j = y; j < y + (camera.getHeight() / tileHeight); j++) {
                if (blocks[i + (j * height)] != null)
                    blocks[i + (j * height)].render(g);
            }
        }
    }
}
