package com.stonewolf.game.tiles;

import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.tiles.blocks.Block;
import com.stonewolf.game.tiles.blocks.HoleBlock;
import com.stonewolf.game.tiles.blocks.ObjBlock;
import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;


public class TileMapObj extends TileMap {

    public static Block[] event_blocks;

    private  int tileHeight;
    private int tileWidth;

    public static int width;
    public static int height;

    public TileMapObj(String data, Sprite sprite, int width, int height,
                       int tileWidth, int tileHeight, int tileColumns) {

        Block tempBlock;

        event_blocks = new Block[width * height];

        TileMapObj.height = height;
        TileMapObj.width = width;

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        String[] block = data.split(",");
        for (int i = 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+", ""));
            if (temp != 0) {
                if (temp == 172) {
                    tempBlock =
                            new HoleBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns)),
                                    new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight),
                                    tileWidth, tileHeight);
                } else {
                    tempBlock =
                            new ObjBlock(sprite.getSprite((int) ((temp - 1) % tileColumns), (int) ((temp - 1) / tileColumns)),
                                    new Vector2f((int) (i % width) * tileWidth, (int) (i / height) * tileHeight),
                                    tileWidth, tileHeight);
                }
                event_blocks[i] = tempBlock;
            }
        }

    }

    @Override
    public void render(Graphics2D g, AABB camera) {

            int x = (int) ((camera.getPosition().getCamVar().x) / tileWidth);
            int y = (int) ((camera.getPosition().getCamVar().y) / tileHeight);
        for (int i = x; i < x + (camera.getWidth() / tileWidth); i++) {
            for (int j = y; j < y + (camera.getHeight() / tileHeight); j++) {
                if (event_blocks[i + (j * height)] != null)
                    event_blocks[i + (j * height)].render(g);
            }
        }
    }

}
