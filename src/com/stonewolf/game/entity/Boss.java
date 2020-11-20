package com.stonewolf.game.entity;

import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.states.GameStateManager;
import com.stonewolf.game.utils.AABB;
import com.stonewolf.game.utils.Camera;
import com.stonewolf.game.utils.Timer;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;


public class Boss extends Enemy {

    private static int IDLE = 0;
    private static int ATTACKING = 1;
    private static int DEAD = 2;

    private boolean shooting;
    private boolean dead;
    private ArrayList<Projectile> bulletShots;

    public Boss(Camera camera, Sprite sprite, Vector2f spawnPoint, int size, GameStateManager gsm) {
        super(camera, sprite, spawnPoint, size, gsm);

        bulletShots = new ArrayList<>();

        isPlayer = false;
        isImmune = false;
        isToBeDestroyed = false;
        shooting = false;
        dead = false;

        bounds.setHeight(117);
        bounds.setWidth(114);
        bounds.setYOffset(5);
        bounds.setXOffset(5);

        acceleration = 1f;
        maxSpeed = 3f;
        aggro = 650;
        lives = 1;
        maxHealth = 2;
        health = maxHealth;
        anim.setDelay(15);

        sense = new AABB(new Vector2f(spawnPoint.x + size / 2 - aggro / 2,
                spawnPoint.y + size / 2 - aggro / 2), aggro);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        for (Projectile projectile : bulletShots) {
            projectile.render(g);
        }

        if (gettingHit) {
            g.drawImage(damageIndicator, (int) (position.getWorldVar().x + bounds.getxOffset()),
                    (int) (position.getWorldVar().y + bounds.getyOffset()),
                    (int) bounds.getWidth() + 5, (int) bounds.getHeight() + 5, null);
        }
    }

    public void update(Player player) {

        if (lives == 0) {
            dead = true;
        }

        if (dead) {
            bounds.setWidth(0);
            bounds.setHeight(0);
            sense.setWidth(0);
            sense.setHeight(0);

            if (currentAnimation != DEAD || anim.getDelay() == -1) {
                this.setAnimation(DEAD, sprite.getSpriteArray(DEAD), 15);
                player.setBossPartIsDead();
            }
        }

        if (!dead) {
            if (sense.aggroCollision(player.getBounds())) {
                if (currentAnimation != ATTACKING || anim.getDelay() == -1) {
                    this.setAnimation(ATTACKING, sprite.getSpriteArray(ATTACKING), 15);
                }
                shooting = anim.getCurrentFrame() == 6 && anim.getCount() == 7;
            } else {
                if (currentAnimation != IDLE || anim.getDelay() == -1) {
                    this.setAnimation(IDLE, sprite.getSpriteArray(IDLE), 15);
                }
                shooting = false;
            }
        }

        if (shooting) {
            bulletShots.add(new Projectile(new Sprite("entity/ProjectileFormatted.png", 32, 32),
                    new Vector2f(position.x, position.y), 64, gsm, player.getPositionVector()));
        }

        if (this.bounds.collides(player.getBounds()) && !player.isImmune()) {
            enemyHit = true;
            player.setGettingHit(true);
            player.setImmune(true);
            player.setGettingHit(true);
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

        for (int i = 0; i < bulletShots.size(); i++) {
            Projectile projectile = bulletShots.get(i);
            projectile.update(player);
            if (projectile.isToBeDestroyed()) {
                bulletShots.remove(projectile);
            }
        }
        anim.update();

    }

    public boolean isDead() {
        return dead;
    }

}


