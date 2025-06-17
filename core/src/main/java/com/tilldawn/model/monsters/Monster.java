package com.tilldawn.model.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.model.App;
import com.tilldawn.model.CollisionRect;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.MonsterType;

public class Monster {
    protected MonsterType type;
    protected float spriteTime;
    protected Sprite sprite;
    protected float posX, posY;
    protected float hp;
    protected boolean isExploding = false;
    protected float knockbackTimer = 0f;
    protected boolean isFlipped = false;

    public Monster(MonsterType type, float posX, float posY) {
        this.type = type;
        this.hp = type.getHp();
        this.spriteTime = 0f;
        this.sprite = new Sprite(type.getAnimation().getKeyFrame(0f));
        sprite.setSize(sprite.getWidth() * 2, sprite.getHeight() * 2);
        this.posX = posX;
        this.posY = posY;
    }

    public void updateSprite() {
        if (isDead())
            return;
        Animation<Texture> animation = isExploding? MonsterType.deathAnimation : type.getAnimation();
        spriteTime += Gdx.graphics.getDeltaTime();
        if (animation.isAnimationFinished(spriteTime)) {
            if (isExploding) {
                isExploding = false;
                return;
            }
            spriteTime = 0f;
        }
        sprite.setRegion(animation.getKeyFrame(spriteTime));
        if (isFlipped)
            sprite.flip(true, false);
    }

    public void move(Vector2 direction) {
        if (knockbackTimer > 0f) {
            posX -= direction.x * knockbackTimer;
            posY -= direction.y * knockbackTimer;
        } else {
            posX += direction.x;
            posY += direction.y;
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public CollisionRect getCollisionRect() {
        return new CollisionRect(posX, posY, sprite.getWidth(), sprite.getHeight());
    }

    public void reduceHp(float amount) {
        this.hp -= amount;
        if (hp <= 0f && !isExploding) {
            isExploding = true;
            spriteTime = 0f;
            if (App.isSfxEnabled())
                GameAssetManager.getInstance().getKillSfx().play(1f);
        }
    }

    public float getHp() {
        return hp;
    }

    public MonsterType getType() {
        return type;
    }

    public boolean isExploding() {
        return isExploding;
    }

    public void setExploding(boolean exploding) {
        isExploding = exploding;
    }

    public void setKnockback() {
        knockbackTimer = 1f;
    }

    public void updateKnockback() {
        if (knockbackTimer > 0f)
            knockbackTimer -= Gdx.graphics.getDeltaTime();
    }

    public boolean isDead() {
        return hp <= 0f && !isExploding;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
}
