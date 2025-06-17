package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.enums.Output;

public class StatusController { // handles ammo, hearts, timer and ...
    private Player player;
    private Sprite shadowSprite;
    private Texture heartTexture;
    private float heartTimer = 0f;
    private Animation<Texture> heart;
    private ProgressBar levelBar;

    public StatusController(Player player) {
        this.player = player;
        this.shadowSprite = new Sprite(new Texture("Images/Sprite/Hale.png"));
        shadowSprite.setCenter(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        shadowSprite.setColor(shadowSprite.getColor().r, shadowSprite.getColor().g, shadowSprite.getColor().b, 0.25f);
        this.heart = new Animation<> (
            0.5f,
            new Texture("Images/Sprite/HeartAnimation_0.png"),
            new Texture("Images/Sprite/HeartAnimation_1.png"),
            new Texture("Images/Sprite/HeartAnimation_2.png")
        );
        this.heart.setPlayMode(Animation.PlayMode.LOOP);
        this.heartTexture = heart.getKeyFrame(0f);
        initLevelBar();
    }

    public void update() {
        updateHeartTimer();
        drawShadow();
        drawAmmo();
        drawHearts();
        drawKills();
        drawTime(player.getSurvivedTime());
        drawLevel();
    }

    public ProgressBar getLevelBar() {
        return levelBar;
    }

    private void initLevelBar() {
        ProgressBar.ProgressBarStyle levelBarStyle = new ProgressBar.ProgressBarStyle();
        Pixmap pixmap = new Pixmap(30, 30, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        levelBarStyle.background = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.setColor(Color.FOREST);
        pixmap.fill();
        levelBarStyle.knobBefore = new TextureRegionDrawable(new Texture(pixmap));
        pixmap.dispose();
        levelBar = new ProgressBar(0, player.getLevel() * 20, 1, false, levelBarStyle);
        levelBar.setWidth(Gdx.graphics.getWidth());
        levelBar.setHeight(20);
        levelBar.setPosition(0, 10);
        levelBar.setValue(0);
    }

    private void drawShadow() {
        shadowSprite.draw(Main.getBatch());
    }

    private void drawAmmo() {
        for (int i = 0; i < player.getAmmo(); i++) {
            Sprite ammoSprite = new Sprite(GameAssetManager.getInstance().getAmmoTexture());
            ammoSprite.setPosition(10 + (ammoSprite.getWidth() - 10) * i, Gdx.graphics.getHeight() - ammoSprite.getHeight() - 90);
            ammoSprite.draw(Main.getBatch());
        }
    }

    private void drawHearts() {
        for (int i = 0; i < player.getHp(); i++) {
            Sprite heartSprite = new Sprite(heartTexture);
            heartSprite.setScale(2f);
            heartSprite.setPosition(20 + heartSprite.getWidth() * i, Gdx.graphics.getHeight() - heartSprite.getHeight() - 30);
            heartSprite.draw(Main.getBatch());
        }
        for (int i = player.getHp(); i < player.getMaxHp(); i++) {
            Sprite heartSprite = new Sprite(new Texture("Images/Sprite/HeartAnimation_3.png"));
            heartSprite.setScale(2f);
            heartSprite.setPosition(20 + heartSprite.getWidth() * i, Gdx.graphics.getHeight() - heartSprite.getHeight() - 30);
            heartSprite.draw(Main.getBatch());
        }
    }

    private void updateHeartTimer() {
        heartTimer += Gdx.graphics.getDeltaTime();
        if (heart.isAnimationFinished(heartTimer))
            heartTimer = 0f;
        heartTexture = heart.getKeyFrame(heartTimer);
    }

    private void drawKills() {
        Sprite killSymbol = new Sprite(GameAssetManager.getInstance().getKillTexture());
        killSymbol.setScale(2f);
        killSymbol.setPosition(20 + killSymbol.getWidth(), Gdx.graphics.getHeight() - killSymbol.getHeight() - 150);
        killSymbol.draw(Main.getBatch());
        Label numberOfKills = new Label(String.valueOf(player.getKills()), GameAssetManager.getInstance().getSkin());
        numberOfKills.setPosition(20 + killSymbol.getWidth() + 50, Gdx.graphics.getHeight() - numberOfKills.getHeight() - 140);
        numberOfKills.draw(Main.getBatch(), 1f);
    }

    private void drawLevel() {
        levelBar.draw(Main.getBatch(), 1);
        Label levelLabel = new Label(
            Output.Level.getString() + player.getLevel(),
            GameAssetManager.getInstance().getSkin()
        );
        levelLabel.setPosition(Gdx.graphics.getWidth() / 2f - 10, 0);
        levelLabel.draw(Main.getBatch(), 1f);
    }

    private void drawTime(float timePassed) {
        float remainingTime = player.getGameDuration() * 60 - timePassed;
        int seconds = (int) (remainingTime % 60);
        int minutes = (int) (remainingTime / 60);
        String sec = seconds < 10? "0" + seconds: "" + seconds;
        String min = minutes < 10? "0" + minutes: "" + minutes;
        Label timeLabel = new Label(min + " : " + sec, GameAssetManager.getInstance().getSkin());
        timeLabel.setPosition(Gdx.graphics.getWidth() / 2f - 10, Gdx.graphics.getHeight() - 50);
        timeLabel.draw(Main.getBatch(), 1);
    }
}
