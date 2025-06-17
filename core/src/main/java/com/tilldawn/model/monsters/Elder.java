package com.tilldawn.model.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.enums.MonsterType;

public class Elder extends Monster {
    private float dashTimer = 5f;
    private float moveTimer = 0f;

    public Elder(MonsterType type, float posX, float posY) {
        super(type, posX, posY);
    }

    @Override
    public void updateSprite() {
        Animation<Texture> animation =
            dashTimer > 0f? MonsterType.Elder.getAnimation() : MonsterType.Elder.getDashAnimation();
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

    public void updateTimer() {
        if (dashTimer <= 0f) {
            if (moveTimer <= 0f)
                dashTimer = 5f;
            else
                moveTimer -= Gdx.graphics.getDeltaTime();
        }
        else {
            dashTimer -= Gdx.graphics.getDeltaTime();
            if (dashTimer <= 0f)
                moveTimer = 2f;
        }
    }

    public void dash(Vector2 direction) {
        posX += direction.x;
        posY += direction.y;
    }

    public float getMoveTimer() {
        return moveTimer;
    }
}
