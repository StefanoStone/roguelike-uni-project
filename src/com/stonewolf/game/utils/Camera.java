package com.stonewolf.game.utils;


import com.stonewolf.game.GamePanel;
import com.stonewolf.game.entity.Entity;
import com.stonewolf.game.input.KeyHandler;
import com.stonewolf.game.input.MouseHandler;
import com.stonewolf.game.states.PlayState;

import java.awt.*;

public class Camera {

    private AABB collisionCam;
    private AABB bounds;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private float dx;
    private float dy;
    private float maxSpeed = 4f;
    private float acceleration = 1f;
    private float deceleration = 0.3f;

    private int widthLimit;
    private int heightLimit;


    private Entity e;

    public Camera(AABB collisionCam) {
        this.collisionCam = collisionCam;

        float x = collisionCam.getPosition().x;
        float y = collisionCam.getPosition().y;
        this.bounds = new AABB(new Vector2f(x, y), (int) collisionCam.getWidth(), (int) collisionCam.getHeight());
    }

    public void setDefaultBounds() {
        this.bounds = new AABB(new Vector2f(0, 0), 1344, 784);
    }

    public void setLimit(int widthLimit, int heightLimit) {
        this.widthLimit = widthLimit;
        this.heightLimit = heightLimit;
    }

    public AABB getBounds() {
        return collisionCam;
    }

    public AABB getBoundsOnScreen() {
        return bounds;
    }


    public void update() {
        move();
        if (!e.xCollided) {
            if ((e.getBounds().getPosition().getWorldVar().x + e.getDx()) < Vector2f.getWorldVarX(widthLimit - collisionCam.getWidth() / 2) - 64 &&
                    (e.getBounds().getPosition().getWorldVar().x + e.getDx()) > Vector2f.getWorldVarX(GamePanel.width / 2 - 64)) {
                PlayState.map.x += dx;
                bounds.getPosition().x += dx;
            }
        }
        if (!e.yCollided) {
            if ((e.getBounds().getPosition().getWorldVar().y + e.getDy()) < Vector2f.getWorldVarY(heightLimit - collisionCam.getHeight() / 2) - 64 &&
                    (e.getBounds().getPosition().getWorldVar().y + e.getDy()) > Vector2f.getWorldVarY(GamePanel.height / 2 - 64)) {
                PlayState.map.y += dy;
                bounds.getPosition().y += dy;

            }
        }
    }

    private void move() {

        if (up) {
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

    public void target(Entity e) {
        this.e = e;
        deceleration = e.getDeceleration();
        maxSpeed = e.getMaxSpeed();
    }

    public void input(MouseHandler mouse, KeyHandler key) {

        if (e == null) {
            up = key.up.down;
            down = key.down.down;
            left = key.left.down;
            right = key.right.down;
        } else {
            if (PlayState.map.y + GamePanel.height / 2 - e.getSize() / 2 + dy >
                e.getBounds().getPosition().y + e.getDy() + 2) {
                up = true;
                down = false;
            } else if (PlayState.map.y + GamePanel.height / 2 - e.getSize() / 2 + dy <
                    e.getBounds().getPosition().y + e.getDy() - 2) {
                up = false;
                down = true;
            } else {
                dy = 0;
                up = false;
                down = false;
            }

            if (PlayState.map.x + GamePanel.width / 2 - e.getSize() / 2 + dx >
                    e.getBounds().getPosition().x + e.getDx() + 2) {
                left = true;
                right = false;
            } else if (PlayState.map.x + GamePanel.width / 2 - e.getSize() / 2 + dx <
                    e.getBounds().getPosition().x + e.getDx() - 2) {
                left = false;
                right = true;
            } else {
                dx = 0;
                left = false;
                right = false;
            }
        }
    }

    public void render(Graphics2D g) {
//        g.setColor(Color.yellow);
//        g.drawRect((int) collisionCam.getPosition().x,
//                   (int) collisionCam.getPosition().y,
//                   (int) collisionCam.getWidth(),
//                   (int) collisionCam.getHeight());
//
//        g.drawRect((int) bounds.getPosition().x,
//                    (int) bounds.getPosition().y,
//                    (int) bounds.getWidth(),
//                    (int) bounds.getHeight());
//
//        g.drawLine(GamePanel.width / 2,0, GamePanel.width / 2 , GamePanel.height);
//        g.drawLine(0,GamePanel.height / 2,GamePanel.width, GamePanel.height / 2);
    }

}
