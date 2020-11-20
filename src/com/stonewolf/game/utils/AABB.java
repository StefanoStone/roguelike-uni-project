package com.stonewolf.game.utils;

import com.stonewolf.game.entity.Entity;


public class AABB {

    private Vector2f position;
    private float xOffset;
    private float yOffset;
    private float width;
    private float height;
    private float aggro;
    private int size;
    private Entity entity;

    public AABB(Vector2f position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;

        size = Math.max(width, height);
    }

    public AABB(Vector2f position, int aggro) {
        this.position = position;
        this.aggro = aggro;

        size = aggro;
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getAggroRadius() {
        return aggro;
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setBox(Vector2f position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;

        size = Math.max(width, height);
    }

    public void setCircle(Vector2f position, int radius, Entity entity) {
        this.position = position;
        this.aggro = radius;
        this.entity = entity;


        size = radius;
    }


    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public boolean collides(AABB otherBox) {
        float thisX = ((position.getWorldVar().x + xOffset) + (width / 2));
        float thisY = ((position.getWorldVar().y + yOffset) + (height / 2));

        float otherX = ((otherBox.getPosition().getWorldVar().x + (otherBox.xOffset)) + (otherBox.getWidth() / 2));
        float otherY = ((otherBox.getPosition().getWorldVar().y + (otherBox.yOffset)) + (otherBox.getHeight() / 2));

        if (Math.abs(thisX - otherX) < (this.width / 2) + (otherBox.getWidth() / 2)) {
            return Math.abs(thisY - otherY) < (this.height / 2) + (otherBox.getHeight() / 2);
        }
        return false;
    }

    public boolean aggroCollision(AABB otherBox) {

        float dX = Math.max(otherBox.getPosition().getWorldVar().x + otherBox.getxOffset(),
                   Math.min(position.getWorldVar().x + (aggro / 2),
                           otherBox.getPosition().getWorldVar().x + otherBox.getxOffset() + otherBox.getWidth()));
        float dY = Math.max(otherBox.getPosition().getWorldVar().y + otherBox.getyOffset(),
                Math.min(position.getWorldVar().y + (aggro / 2),
                        otherBox.getPosition().getWorldVar().y + otherBox.getyOffset() + otherBox.getHeight()));

        dX = position.getWorldVar().x + (aggro / 2) - dX;
        dY = position.getWorldVar().y + (aggro / 2) - dY;

        if (Math.sqrt(dX * dX + dY * dY) < aggro / 2) {
            return true;
        }


        return false;
    }

}

