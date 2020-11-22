package com.stonewolf.game.utils;

import com.stonewolf.game.GamePanel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AABBTest {

    @Test
    void collides() {
        AABB playerBox = new AABB(new Vector2f(0,0), 64);
        AABB projectileBox = new AABB(new Vector2f(0,0), 64);
        projectileBox.setWidth(25);
        projectileBox.setHeight(25);
        projectileBox.setXOffset(20);
        projectileBox.setYOffset(12);
        playerBox.setWidth(30);
        playerBox.setHeight(45);
        playerBox.setXOffset(20);
        playerBox.setYOffset(5);

        assertTrue(projectileBox.collides(playerBox));
    }

    @Test
    void aggroCollision() {
        AABB playerBox = new AABB(new Vector2f(0,0), 64);
        AABB enemyBox = new AABB(new Vector2f(0,0), 64);
        playerBox.setWidth(30);
        playerBox.setHeight(45);
        playerBox.setXOffset(20);
        playerBox.setYOffset(5);
        enemyBox.setWidth(25);
        enemyBox.setHeight(45);
        enemyBox.setXOffset(20);
        enemyBox.setYOffset(20);

        assertTrue(enemyBox.collides(playerBox));
    }
}