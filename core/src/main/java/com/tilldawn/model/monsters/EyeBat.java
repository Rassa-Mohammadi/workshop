package com.tilldawn.model.monsters;

import com.badlogic.gdx.Gdx;
import com.tilldawn.model.enums.MonsterType;

public class EyeBat extends Monster {
    private float shootTimer;

    public EyeBat(MonsterType type, float posX, float posY) {
        super(type, posX, posY);
        shootTimer = 3f;
    }

    public void updateShootTimer() {
        shootTimer -= Gdx.graphics.getDeltaTime();
        if (shootTimer < 0f)
            shootTimer = 0f;
    }

    public float getShootTimer() {
        return shootTimer;
    }

    public void setShootTimer() {
        shootTimer = 3f;
    }
}
