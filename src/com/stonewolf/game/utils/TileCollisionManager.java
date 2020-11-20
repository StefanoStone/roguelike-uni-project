package com.stonewolf.game.utils;

import com.stonewolf.game.entity.Entity;
import com.stonewolf.game.tiles.TileMapObj;
import com.stonewolf.game.tiles.blocks.Block;
import com.stonewolf.game.tiles.blocks.HoleBlock;

public class TileCollisionManager {

    private Entity e;
    private Block block;

    public TileCollisionManager(Entity e) {
        this.e = e;
    }

    public boolean collisionTile(float ax, float ay) {
        for (int i = 0; i < 4; i++) {

            int xt = (int) ((e.getBounds().getPosition().x + ax) + (i % 2) * e.getBounds().getWidth() +
                             e.getBounds().getxOffset()) / 64;
            int yt = (int) ((e.getBounds().getPosition().y + ay) + (i / 2) * e.getBounds().getHeight() +
                             e.getBounds().getyOffset()) / 64;

            if (TileMapObj.event_blocks[xt + (yt * TileMapObj.height)] != null) {
                block = TileMapObj.event_blocks[xt + (yt * TileMapObj.height)];
                if (block instanceof HoleBlock) {
                    return !collisionHole(ax, ay, xt, yt);
                }
                return !block.update(e.getBounds());
            }
        }
        return true;
    }

    private boolean collisionHole(float ax, float ay, int xt, int yt) {
        int nextXt = (int) ((((e.getBounds().getPosition().x + ax) + e.getBounds().getxOffset()) / 64) +
                               e.getBounds().getWidth() / 64);
        int nextYt = (int) ((((e.getBounds().getPosition().y + ay) + e.getBounds().getyOffset()) / 64) +
                               e.getBounds().getHeight() / 64);

        if (block.isInside(e.getBounds())) {
            e.setFallen(true);
            return false;
        } else if ((nextXt == yt + 1) || (nextXt == xt + 1) || (nextYt == yt - 1) || (nextXt == xt - 1)) {
            if (TileMapObj.event_blocks[nextXt + (nextYt * TileMapObj.height)] instanceof HoleBlock) {
                if (e.getBounds().getPosition().x > block.getPosition().x) {
                    e.setFallen(true);
                }
                return false;
            }
        }
        e.setFallen(false);
        return false;
    }




}
