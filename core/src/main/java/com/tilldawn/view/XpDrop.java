package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.model.CollisionRect;
import com.tilldawn.model.GameAssetManager;

public class XpDrop {
    private float posX;
    private float posY;
    private Sprite sprite;
    private float time = 0f;

    public XpDrop(float x, float y) {
        this.posX = x;
        this.posY = y;
        this.sprite = new Sprite(GameAssetManager.getInstance().getXpDropAnimation().getKeyFrame(0f));
        this.time = 0f;
    }

    public void updateSprite() {
        time += Gdx.graphics.getDeltaTime();
        if (GameAssetManager.getInstance().getXpDropAnimation().isAnimationFinished(time))
            time = 0f;
        sprite.setRegion(GameAssetManager.getInstance().getXpDropAnimation().getKeyFrame(time));
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public CollisionRect getCollisionRect() {
        return new CollisionRect(posX, posY, sprite.getWidth(), sprite.getHeight());
    }
}
