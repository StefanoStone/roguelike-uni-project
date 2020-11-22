package com.stonewolf.game.entity;

import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.states.GameStateManager;
import com.stonewolf.game.utils.Timer;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;

public class Projectile extends Entity {

    private float deltaX;
    private float deltaY;
    private double speed;
    private double direction;
    private Vector2f playerPosition;

    public Projectile(Sprite sprite, Vector2f spawnPoint, int size, GameStateManager gsm, Vector2f playerPosition) {
        super(sprite, spawnPoint, size, gsm);

        isPlayer = false;
        isImmune = true;
        isToBeDestroyed = false;
        this.playerPosition = playerPosition;

        bounds.setWidth(25);
        bounds.setHeight(25);
        bounds.setXOffset(20);
        bounds.setYOffset(12);

        speed = 5;
        anim.setDelay(10);

        deltaX = (playerPosition.x - this.position.x);
        deltaY = (playerPosition.y - this.position.y);
        direction = Math.atan(deltaY / deltaX);

        new Timer(5, new Runnable() {
            @Override
            public void run() {
                isToBeDestroyed = true;
            }
        }).start();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(anim.getImage(), (int) (position.getWorldVar().x), (int) (position.getWorldVar().y), size, size, null);
    }

    public void update(Player player) {

        if (this.bounds.collides(player.getBounds()) && !player.isImmune()) {
            enemyHit = true;
            player.setImmune(true);
            player.setGettingHit(true);
            isToBeDestroyed = true;
        }

        if (enemyHit) {
            enemyHit = false;
            player.getHit();
            new Timer(2, new Runnable() {
                @Override
                public void run() {
                    player.setImmune(false);
                    player.setGettingHit(false);
                }
            }).start();
        }

        move();
        anim.update();
    }

    private void move() {

        if (deltaX < 0) {
            this.position.setX((float) (this.position.x - (speed * Math.cos(direction))));
            this.position.setY((float) (this.position.y - (speed * Math.sin(direction))));
        } else {
            this.position.setX((float) (this.position.x + (speed * Math.cos(direction))));
            this.position.setY((float) (this.position.y + (speed * Math.sin(direction))));
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }
}
