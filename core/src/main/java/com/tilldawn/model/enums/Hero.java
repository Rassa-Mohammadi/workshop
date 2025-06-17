package com.tilldawn.model.enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tilldawn.model.GameAssetManager;

public enum Hero {
    Shana(4, 4,
        new Texture(Gdx.files.internal("Images/Texture2D/T_Shana_Portrait.png")),
        new Animation<>(
            0.12f,
            new Texture("Images/Sprite/Idle_0 #8330.png"),
            new Texture("Images/Sprite/Idle_1 #8360.png"),
            new Texture("Images/Sprite/Idle_2 #8819.png"),
            new Texture("Images/Sprite/Idle_3 #8457.png"),
            new Texture("Images/Sprite/Idle_4 #8318.png"),
            new Texture("Images/Sprite/Idle_5 #8307.png")
        ),
        new Animation<>(
            0.1f,
            new Texture("Images/Sprite/Run_0 #8762.png"),
            new Texture("Images/Sprite/Run_1 #8778.png"),
            new Texture("Images/Sprite/Run_2 #8286.png"),
            new Texture("Images/Sprite/Run_3 #8349.png")
        )
    ),
    Diamond(7, 1,
        new Texture(Gdx.files.internal("Images/Texture2D/T_Diamond_Portrait.png")),
        new Animation<>(
            0.12f,
            new Texture("Images/Sprite/Idle_0 #8328.png"),
            new Texture("Images/Sprite/Idle_1 #8358.png"),
            new Texture("Images/Sprite/Idle_2 #8817.png"),
            new Texture("Images/Sprite/Idle_3 #8455.png"),
            new Texture("Images/Sprite/Idle_4 #8316.png"),
            new Texture("Images/Sprite/Idle_5 #8305.png")
        ),
        new Animation<>(
            0.1f,
            new Texture("Images/Sprite/Run_0 #8760.png"),
            new Texture("Images/Sprite/Run_1 #8776.png"),
            new Texture("Images/Sprite/Run_2 #8284.png"),
            new Texture("Images/Sprite/Run_3 #8347.png")
        )
    ),
    Scarlet(3, 5,
        new Texture(Gdx.files.internal("Images/Texture2D/T_Scarlett_Portrait.png")),
        new Animation<>(
            0.12f,
            new Texture("Images/Sprite/Idle_0 #8327.png"),
            new Texture("Images/Sprite/Idle_1 #8357.png"),
            new Texture("Images/Sprite/Idle_2 #8816.png"),
            new Texture("Images/Sprite/Idle_3 #8454.png"),
            new Texture("Images/Sprite/Idle_4 #8315.png"),
            new Texture("Images/Sprite/Idle_5 #8304.png")
        ),
        new Animation<>(
            0.1f,
            new Texture("Images/Sprite/Run_0 #8759.png"),
            new Texture("Images/Sprite/Run_1 #8775.png"),
            new Texture("Images/Sprite/Run_2 #8283.png"),
            new Texture("Images/Sprite/Run_3 #8346.png")
        )
    ),
    Lilith(5, 3,
        new Texture(Gdx.files.internal("Images/Texture2D/T_Lilith_Portrait.png")),
        new Animation<>(
            0.12f,
            new Texture("Images/Sprite/Idle_0 #8333.png"),
            new Texture("Images/Sprite/Idle_1 #8363.png"),
            new Texture("Images/Sprite/Idle_2 #8822.png"),
            new Texture("Images/Sprite/Idle_3 #8460.png"),
            new Texture("Images/Sprite/Idle_4 #8321.png"),
            new Texture("Images/Sprite/Idle_5 #8310.png")
        ),
        new Animation<>(
            0.1f,
            new Texture("Images/Sprite/Run_0 #8765.png"),
            new Texture("Images/Sprite/Run_1 #8781.png"),
            new Texture("Images/Sprite/Run_2 #8289.png"),
            new Texture("Images/Sprite/Run_3 #8352.png")
        )
    ),
    Dasher(2, 10,
        new Texture(Gdx.files.internal("Images/Texture2D/T_Dasher_Portrait.png")),
        new Animation<>(
            0.12f,
            new Texture("Images/Sprite/Idle_0 #8325.png"),
            new Texture("Images/Sprite/Idle_1 #8355.png"),
            new Texture("Images/Sprite/Idle_2 #8814.png"),
            new Texture("Images/Sprite/Idle_3 #8452.png"),
            new Texture("Images/Sprite/Idle_4 #8313.png"),
            new Texture("Images/Sprite/Idle_5 #8302.png")
        ),new Animation<>(
            0.1f,
            new Texture("Images/Sprite/Run_0 #8757.png"),
            new Texture("Images/Sprite/Run_1 #8773.png"),
            new Texture("Images/Sprite/Run_2 #8281.png"),
            new Texture("Images/Sprite/Run_3 #8344.png")
        )
    );

    private final int hp;
    private final int speed;
    private final Texture texture;
    private final Animation<Texture> idleAnimation;
    private final Animation<Texture> runAnimation;

    Hero(int hp, int speed, Texture texture, Animation<Texture> idleAnimation, Animation<Texture> runAnimation) {
        this.hp = hp;
        this.speed = speed;
        this.texture = texture;
        this.idleAnimation = idleAnimation;
        this.runAnimation = runAnimation;
        this.idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
        this.runAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return this.name();
    }

    public Texture getTexture() {
        return texture;
    }

    public Animation<Texture> getIdleAnimation() {
        return idleAnimation;
    }

    public Animation<Texture> getRunAnimation() {
        return runAnimation;
    }

    public Table getDescription() {
        Table result = new Table();
        result.center();
        Image heroImage = new Image(texture);
        result.add(heroImage).pad(10).row();
        result.add(new Label("HP: " + hp, GameAssetManager.getInstance().getSkin())).pad(10).row();
        result.add(new Label("Speed: " + speed, GameAssetManager.getInstance().getSkin())).pad(10).row();
        return result;
    }

    public static Texture getTexture(String heroName) {
        for (Hero hero : values()) {
            if (hero.getName().equals(heroName))
                return hero.texture;
        }
        return null;
    }

    public static Hero getHero(String heroName) {
        for (Hero hero : values()) {
            if (hero.getName().equals(heroName))
                return hero;
        }
        return null;
    }
}
