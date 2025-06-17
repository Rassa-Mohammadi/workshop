package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Sprite sprite;
    private float posX, posY;
    private float direction;
    private CollisionRect collisionRect;
    private boolean isFriendly;

    public Bullet(float posX, float posY, float direction, boolean isFriendly) {
        this.posX = posX;
        this.posY = posY;
        this.sprite = new Sprite(GameAssetManager.getInstance().getBulletTexture());
        sprite.setSize(30, 30);
        this.direction = direction * MathUtils.degreesToRadians;
        this.collisionRect = new CollisionRect(posX, posY, 30, 30);
        this.isFriendly = isFriendly;
    }

    public void move() {
        int coefficient = isFriendly ? App.friendlyBulletMovementCoefficient : App.nonFriendlyBulletMovementCoefficient;
        float deltaX = (float) (Math.cos(direction) * Gdx.graphics.getDeltaTime() * coefficient);
        float deltaY = (float) (Math.sin(direction) * Gdx.graphics.getDeltaTime() * coefficient);
        posX += deltaX;
        posY += deltaY;
        collisionRect.move(deltaX, deltaY);
    }

    public Sprite getSprite() {
        return sprite;
    }

    private Vector2 getDirection(float x, float y) {
        float deltaX = Gdx.input.getX() - x;
        float deltaY = Gdx.graphics.getHeight() / 2f - Gdx.input.getY() - y;
        float vectorSize = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return new Vector2(
            deltaX / vectorSize,
            deltaY / vectorSize
        );
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public boolean isOut() {
        return posX < -10000 || posX > 10000 || posY < -10000 || posY > 10000;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public boolean isFriendly() {
        return isFriendly;
    }
}
