package com.stonewolf.game.tiles;

import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;

public abstract class TileMap {

    public abstract void render(Graphics2D g, AABB camera);

}
