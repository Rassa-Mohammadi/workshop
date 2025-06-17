package com.tilldawn.model;

public class CollisionRect {
    private float x, y;
    private float width, height;

    public CollisionRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean collide(CollisionRect other) {
        return x < other.x + other.width && y < other.y + other.height && x + width > other.x && y + height > other.y;
    }

    public void move(float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
    }
}
