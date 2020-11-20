package com.stonewolf.game.entity;

import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.states.GameStateManager;
import com.stonewolf.game.utils.*;

import java.awt.*;


public class Enemy extends Entity {

    protected AABB sense;
    protected int aggro;
    protected Camera camera;

    public Enemy(Camera camera, Sprite sprite, Vector2f spawnPoint, int size, GameStateManager gsm) {
        super(sprite, spawnPoint, size, gsm);

        this.camera = camera;
        isPlayer = false;
        isImmune = false;
        isToBeDestroyed = false;

        acceleration = 1f;
        maxSpeed = 3f;
        aggro = 200;
        lives = 1;
        maxHealth = 1;
        health = maxHealth;

        bounds.setWidth(25);
        bounds.setHeight(45);
        bounds.setXOffset(20);
        bounds.setYOffset(20);

        sense = new AABB(new Vector2f(spawnPoint.x + size / 2 - aggro / 2,
                                      spawnPoint.y + size / 2 - aggro / 2), aggro);
    }

    private void setToBeDestroyed() {
        fallen = true;
        bounds.setWidth(0);
        bounds.setHeight(0);
        if (currentAnimation == FALLEN && anim.hasPlayedOnce())
            isToBeDestroyed = true;
    }

    public void update(Player player) {

        if (camera.getBoundsOnScreen().collides(this.bounds)) {
            super.update();
            move(player);

            if (lives <= 0) {
                setToBeDestroyed();
            }

            if (!fallen) {
                if (tcm.collisionTile(dx, 0)) {
                    sense.getPosition().x += dx;
                    position.x += dx;
                }
                if (tcm.collisionTile(0, dy)) {
                    sense.getPosition().y += dy;
                    position.y += dy;
                }
            } else {
                dx = 0;
                dy = 0;
                anim.setDelay(10);
                setToBeDestroyed();
            }
        }

        if (this.bounds.collides(player.getBounds()) && player.defend) {
            if (player.right) {
                position.x += 120;
                sense.getPosition().x += 120;
            } else if (player.left) {
                position.x -= 120;
                sense.getPosition().x -= 120;
            } else if (player.up) {
                position.y -= 120;
                sense.getPosition().y -= 120;
            } else if (player.down) {
                position.y += 120;
                sense.getPosition().y += 120;
            }
        }


        if (this.bounds.collides(player.getBounds()) && !player.isImmune()) {
            attack = true;
            enemyHit = true;
            player.setImmune(true);
            player.setGettingHit(true);
        } else {
            attack = false;
        }

        if (enemyHit && !attack) {
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
    }

    @Override
    public void render(Graphics2D g) {
        if (camera.getBoundsOnScreen().collides(this.getBounds())) {
//            g.setColor(Color.green);
//            g.drawRect((int) (position.getWorldVar().x + bounds.getxOffset()),
//                    (int) (position.getWorldVar().y + bounds.getyOffset()),
//                    (int) bounds.getWidth(), (int) bounds.getHeight());
//
//            g.setColor(Color.blue);
//            g.drawOval((int) (sense.getPosition().getWorldVar().x),
//                    (int) (sense.getPosition().getWorldVar().y), aggro, aggro);

            g.drawImage(anim.getImage(), (int) (position.getWorldVar().x), (int) (position.getWorldVar().y), size, size, null);
        }
    }

    private void move(Player player) {

        if (!fallen) {
            if (sense.aggroCollision(player.getBounds()) && !attack) {
                if (position.y > player.position.y + 10) {
                    dy -= acceleration;
                    up = true;
                    down = false;
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

                if (position.y < player.position.y - 10) {
                    dy += acceleration;
                    down = true;
                    up = false;
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

                if (position.x > player.position.x + 10) {
                    dx -= acceleration;
                    left = true;
                    right = false;
                    up = false;
                    down = false;
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

                if (position.x < player.position.x - 10) {
                    dx += acceleration;
                    right = true;
                    left = false;
                    up = false;
                    down = false;
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
            } else {
                up = false;
                down = false;
                left = false;
                right = false;
                dx = 0;
                dy = 0;
            }
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        //no input needed
    }
}
