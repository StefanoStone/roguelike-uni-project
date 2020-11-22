package com.stonewolf.game.entity;

import com.stonewolf.game.GamePanel;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.states.GameStateManager;
import com.stonewolf.game.states.PlayState;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.utils.Camera;
import com.stonewolf.game.utils.Timer;
import com.stonewolf.game.utils.Vector2f;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {

    private boolean mouseClicked;
    private Player thisInstance;
    private Camera camera;
    private int bossPartsDead;

    public Player(Camera camera, Sprite sprite, Vector2f spawnPoint, int size, GameStateManager gsm) {
        super(sprite, spawnPoint, size, gsm);

        this.camera = camera;

        bossPartsDead = 0;
        acceleration = 3f;
        maxSpeed = 4f;
        lives = 3;
        attackCoolDown = 1;
        maxHealth = 3;
        health = maxHealth;

        bounds.setWidth(30);
        bounds.setHeight(45);
        bounds.setXOffset(20);
        bounds.setYOffset(5);

        thisInstance = this;

        isToBeDestroyed = false;
        isPlayer = true;
        isImmune = false;
        gettingHit = false;
    }

    private void move() {

        if (up) {
            hitBox.setWidth(size);
            hitBox.setHeight(size / 2);
            hitBox.setYOffset(0);
            dy -= acceleration;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if (dy < 0) {
                dy += deceleration;
                if (dy > 0) {
                    dy = 0;
                }
            }
        }

        if (down) {
            hitBox.setWidth(size);
            hitBox.setHeight(size / 2);
            hitBox.setYOffset(size - 15);
            dy += acceleration;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if (dy > 0) {
                dy -= deceleration;
                if (dy < 0) {
                    dy = 0;
                }
            }
        }

        if (left) {
            hitBox.setXOffset(0);
            hitBox.setWidth(size / 2);
            hitBox.setHeight(size);
            dx -= acceleration;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if (dx < 0) {
                dx += deceleration;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        if (right) {
            hitBox.setWidth(size / 2);
            hitBox.setHeight(size);
            hitBox.setXOffset(size / 2);
            dx += acceleration;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= deceleration;
                if (dx < 0) {
                    dx = 0;
                }
            }
        }

    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Boss> bosses) {

        super.update();

        if (lives == 0 || bossPartsDead == 5)
            gsm.setGameIsOver(lives);

        for (Enemy enemy : enemies) {
                if (attack && hitBox.collides(enemy.getBounds())) {
                    enemyHit = true;
                    enemy.setGettingHit(true);
                    enemy.getHit();
                    enemyHit = false;
                }
        }

        for (Boss bossPart : bosses) {
            if (attack && hitBox.collides(bossPart.getBounds()) && !bossPart.isImmune()) {
                enemyHit = true;
                bossPart.setImmune(true);
                bossPart.setGettingHit(true);
                bossPart.getHit();
                enemyHit = false;
                new Timer(2, new Runnable() {
                    @Override
                    public void run() {
                        bossPart.setImmune(false);
                        bossPart.setGettingHit(false);
                    }
                }).start();
            }
        }

            if (!defend) {
                this.setAcceleration(3f);
                this.setMaxSpeed(4f);
            }

            if (!defend && !gettingHit) {
                isImmune = false;
            }
            if (defend) {
                isImmune = true;
                this.setAcceleration(0.5f);
                this.setMaxSpeed(1.5f);
            }


        if (!fallen) {
            move();
            if (tcm.collisionTile(dx, 0)) {
                position.x += dx;
                xCollided = false;
            } else {
                xCollided = true;
            }
            if (tcm.collisionTile(0, dy)) {
                position.y += dy;
                yCollided = false;
            } else {
                yCollided = true;
            }
        } else {
            xCollided = true;
            yCollided = true;
            if (anim.hasPlayedOnce()) {
                    dx = 0;
                    dy = 0;
                    fallen = false;
                    resetPosition();
                    lives--;
                    health = maxHealth;
                }
            }
    }

    private void resetPosition() {

        System.out.println("Resetting player position...");

        camera.setDefaultBounds();

        position.x = (GamePanel.width / 2) - 32;
        PlayState.map.x = 0;

        position.y = (GamePanel.height / 2) - 32;
        PlayState.map.y = 0;

        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);

    }


    @Override
    public void render(Graphics2D g) {
        g.drawImage(anim.getImage(), (int) (position.getWorldVar().x), (int) (position.getWorldVar().y), size, size, null);

        if (gettingHit) {
            g.drawImage(damageIndicator, (int) (position.getWorldVar().x + bounds.getxOffset()),
                       (int) (position.getWorldVar().y + bounds.getyOffset()),
                       (int) bounds.getWidth() + 5, (int) bounds.getHeight() + 5, null);
        }

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

        if (mouse.getButton() == 1)
            mouseClicked = true;

        if (mouseClicked && !isAttackInCD) {
            if (!isImmune) {
                attack = true;
                isImmune = true;
            }

            if (anim.getCurrentFrame() == 7) {
                attack = false;
                isAttackInCD = true;
                mouseClicked = false;
                isImmune = false;

                new Timer(attackCoolDown, new Runnable() {
                    @Override
                    public void run() {
                        thisInstance.setAttackInCD(false);
                    }
                }).start();
            }
        }

        if (!fallen) {
            up = key.up.down;
            down = key.down.down;
            left = key.left.down;
            right = key.right.down;
            defend = key.defend.down;

            if (up && down) {
                up = false;
                down = false;
            }

            if (left && right) {
                left = false;
                right = false;
            }

        } else {
            up = false;
            down = false;
            left = false;
            right = false;
            attack = false;
            defend = false;
        }
    }

    public void setBossPartIsDead() {
        this.bossPartsDead++;
    }
}

