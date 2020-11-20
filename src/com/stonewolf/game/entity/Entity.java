package com.stonewolf.game.entity;

import com.stonewolf.game.graphics.Animation;
import com.stonewolf.game.graphics.Sprite;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.states.GameStateManager;
import com.stonewolf.game.utils.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class Entity {

    protected final int RIGHT = 0;
    protected final int LEFT = 1;
    protected final int DOWN = 2;
    protected final int UP = 3;
    protected final int FALLEN = 4;
    protected final int ATTACK_R = 5;
    protected final int ATTACK_L = 6;
    protected final int ATTACK_D = 7;
    protected final int ATTACK_U = 8;
    protected final int SHIELD_R = 9;
    protected final int SHIELD_L = 10;
    protected final int SHIELD_D = 11;
    protected final int SHIELD_U = 12;

    protected int currentAnimation;

    protected Sprite sprite;
    protected Vector2f position;
    protected int size;
    protected Animation anim;
    protected int lives = 3;
    protected int health;
    protected int maxHealth;
    protected int attackCoolDown;

    protected BufferedImage damageIndicator;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected boolean defend;
    protected boolean fallen;

    public boolean xCollided = false;
    public boolean yCollided = false;

    protected float dx;
    protected float dy;

    protected float maxSpeed;
    protected float acceleration;
    protected float deceleration = 0.3f;

    protected boolean isAttackInCD = false;
    protected boolean enemyHit = false;

    protected AABB hitBox;
    protected AABB bounds;

    protected TileCollisionManager tcm;
    protected GameStateManager gsm;

    protected boolean isPlayer;
    protected boolean isImmune;
    protected boolean gettingHit;
    protected boolean isToBeDestroyed;

    public Entity(Sprite sprite, Vector2f spawnPoint, int size, GameStateManager gsm) {

        this.sprite = sprite;
        this.position = spawnPoint;
        this.size = size;
        this.gsm = gsm;

        bounds = new AABB(spawnPoint, size, size);
        hitBox = new AABB(spawnPoint, size / 2, size);
        hitBox.setXOffset(size / 2);

        anim = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

        tcm = new TileCollisionManager(this);

        try {
            InputStream in = getClass().getResourceAsStream("/util/redBlur.png");
            damageIndicator = ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("ERROR: image not loaded.");
        }
    }

    public void setFallen(boolean fallen) {
        this.fallen = fallen;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }
    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }
    public void setImmune(boolean immune) {
        isImmune = immune;
    }
    public void setAttackInCD(boolean attackInCD) {
        isAttackInCD = attackInCD;
    }
    public void setGettingHit(boolean gettingHit) {
        this.gettingHit = gettingHit;
    }

    public AABB getBounds() {
        return bounds;
    }
    public int getSize() {
        return size;
    }
    public Animation getAnimation() {
        return anim;
    }
    public float getMaxSpeed() {
        return maxSpeed;
    }
    public float getAcceleration() {
        return acceleration;
    }
    public float getDeceleration() {
        return deceleration;
    }
    public float getDx() {
        return dx;
    }
    public float getDy() {
        return dy;
    }
    public int getLives() {
        return lives;
    }
    public int getHealth() {
        return health;
    }
    public int getCurrentAnimation() {
        return currentAnimation;
    }
    public boolean isImmune() {
        return isImmune;
    }
    public Vector2f getPositionVector() {
        return new Vector2f(position.x, position.y);
    }
    public boolean isToBeDestroyed() {
        return isToBeDestroyed;
    }


    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        anim.setFrames(frames);
        anim.setDelay(delay);
    }

    public void animate() {
        if (up && !fallen) {
            if (attack) {
                if (currentAnimation != ATTACK_U || anim.getDelay() == -1) {
                    setAnimation(ATTACK_U, sprite.getSpriteArray(ATTACK_U), 5);
                }
            } else if (defend) {
                if (currentAnimation != SHIELD_U || anim.getDelay() == -1) {
                    setAnimation(SHIELD_U, sprite.getSpriteArray(SHIELD_U), 5);
                }
            } else if (currentAnimation != UP || anim.getDelay() == -1) {
                setAnimation(UP, sprite.getSpriteArray(UP), 5);
            }
        } else if (down && !fallen) {
            if (attack) {
                if (currentAnimation != ATTACK_D || anim.getDelay() == -1) {
                    setAnimation(ATTACK_D, sprite.getSpriteArray(ATTACK_D), 5);
                }
            } else if (defend) {
                if (currentAnimation != SHIELD_D || anim.getDelay() == -1) {
                    setAnimation(SHIELD_D, sprite.getSpriteArray(SHIELD_D), 5);
                }
            } else if (currentAnimation != DOWN || anim.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN), 5);
            }
        } else if (left && !fallen) {
            if (attack) {
                if (currentAnimation != ATTACK_L || anim.getDelay() == -1) {
                    setAnimation(ATTACK_L, sprite.getSpriteArray(ATTACK_L), 5);
                }
            } else if (defend) {
                if (currentAnimation != SHIELD_L || anim.getDelay() == -1) {
                    setAnimation(SHIELD_L, sprite.getSpriteArray(SHIELD_L), 5);
                }
            } else if (currentAnimation != LEFT || anim.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT), 5);
            }
        } else if (right && !fallen) {
            if (attack) {
                if (currentAnimation != ATTACK_R || anim.getDelay() == -1) {
                    setAnimation(ATTACK_R, sprite.getSpriteArray(ATTACK_R), 5);
                }
            } else if (defend) {
                if (currentAnimation != SHIELD_R || anim.getDelay() == -1) {
                    setAnimation(SHIELD_R, sprite.getSpriteArray(SHIELD_R), 5);
                }
            } else if (currentAnimation != RIGHT || anim.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 5);
            }
        } else if (fallen) {
            if (currentAnimation != FALLEN || anim.getDelay() == -1) {
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
            }
        } else if (attack) {
            if (currentAnimation == RIGHT) {
                setAnimation(ATTACK_R, sprite.getSpriteArray(ATTACK_R), 5);
            } else if (currentAnimation == LEFT) {
                setAnimation(ATTACK_L, sprite.getSpriteArray(ATTACK_L), 5);
            } else if (currentAnimation == UP) {
                setAnimation(ATTACK_U, sprite.getSpriteArray(ATTACK_U), 5);
            }else if (currentAnimation == DOWN) {
                setAnimation(ATTACK_D, sprite.getSpriteArray(ATTACK_D), 5);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    private void setHitBoxDirection() {
        if (up) {
            hitBox.setYOffset(-size / 2);
            hitBox.setXOffset(0);
        } else if (down) {
            hitBox.setYOffset(size / 2);
            hitBox.setXOffset(0);
        } else if (left) {
            hitBox.setYOffset(0);
            hitBox.setXOffset(-size / 2);
        } else if (right) {
            hitBox.setYOffset(0);
            hitBox.setXOffset(size / 2);
        }

    }

    public void getHit() {
        health--;
        if (health == 0) {
            lives--;
            health = this.maxHealth;
        }
    }

    public void update() {
        animate();
        setHitBoxDirection();
        anim.update();
    }

    public abstract void render(Graphics2D g);

    public abstract void input (MouseHandler mouse, KeyHandler key);

}
