package com.tilldawn.model.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import org.w3c.dom.Text;

public enum MonsterType {
    Tree(new Animation<>(
        1f,
        new Texture("Images/Sprite/T_TreeMonster_0.png"),
        new Texture("Images/Sprite/T_TreeMonster_1.png"),
        new Texture("Images/Sprite/T_TreeMonster_2.png")
    ), null, Float.MAX_VALUE, 0, null),
    TentacleMonster(new Animation<>(
        0.2f,
        new Texture("Images/Sprite/BrainMonster_0.png"),
        new Texture("Images/Sprite/BrainMonster_1.png"),
        new Texture("Images/Sprite/BrainMonster_2.png"),
        new Texture("Images/Sprite/BrainMonster_3.png")
    ), null, 25, 2, null),
    EyeBat(new Animation<>(
        0.2f,
        new Texture("Images/Sprite/T_EyeBat_0.png"),
        new Texture("Images/Sprite/T_EyeBat_1.png"),
        new Texture("Images/Sprite/T_EyeBat_2.png")
    ), null, 50, 1, null),
    Elder(new Animation<>(
        0.2f,
        new Texture("Images/Sprite/T_HasturBoss_1.png"),
        new Texture("Images/Sprite/T_HasturBoss_2.png"),
        new Texture("Images/Sprite/T_HasturBoss_3.png"),
        new Texture("Images/Sprite/T_HasturBoss_4.png"),
        new Texture("Images/Sprite/T_HasturBoss_5.png")
    ), new Animation<>(
        0.12f,
        new Texture("Images/Sprite/T_HasturBossAttack_0.png"),
        new Texture("Images/Sprite/T_HasturBossAttack_1.png")
    ), 400, 0, 20);

    public static final Animation<Texture> deathAnimation = new Animation<>(
        0.12f,
        new Texture("Images/Sprite/DeathFX_0.png"),
        new Texture("Images/Sprite/DeathFX_1.png"),
        new Texture("Images/Sprite/DeathFX_2.png"),
        new Texture("Images/Sprite/DeathFX_3.png")
    );
    private final Animation<Texture> animation;
    private final Animation<Texture> dashAnimation;
    private final float hp;
    private final int speed;
    private final Integer dashSpeed;

    MonsterType(Animation<Texture> animation, Animation<Texture> dashAnimation, float hp, int speed, Integer dashSpeed) {
        this.animation = animation;
        this.dashAnimation = dashAnimation;
        this.hp = hp;
        this.speed = speed;
        this.dashSpeed = dashSpeed;
    }

    public Animation<Texture> getAnimation() {
        return animation;
    }

    public Animation<Texture> getDashAnimation() {
        return dashAnimation;
    }

    public float getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public Integer getDashSpeed() {
        return dashSpeed;
    }
}
