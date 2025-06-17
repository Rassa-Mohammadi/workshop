package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.model.enums.KeyBind;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.monsters.Monster;

import java.util.ArrayList;

public class WeaponController {
    private GameController gameController;
    private Player player;
    private ArrayList<Bullet> bullets;
    private float currentAngle;
    private float reloadTime = 0f;
    private boolean isReloading = false;

    public WeaponController(GameController gameController, Player player) {
        this.gameController = gameController;
        this.player = player;
        this.bullets = new ArrayList<>();
        this.currentAngle = calcCurrentAngle();
    }

    public void update() {
        currentAngle = calcCurrentAngle();
        player.getWeaponSprite().setRotation(currentAngle);
        checkAutoAim();
        handleReloading();
        updateReloadTime();
        handleShoot();
        updateBullets();
        player.getWeaponSprite().draw(Main.getBatch());
        drawBullets();
    }

    private void checkAutoAim() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (player.isAutoAim()) {
                player.setAutoAim(false);
                Main.getMain().setCursor();
            }
            else {
                player.setAutoAim(true);
                Main.getMain().removeCursor();
            }
        }
        if (player.isAutoAim()) {
            Monster monster = gameController.getMonsterController().getClosestMonster();
            if (monster != null) {
                Gdx.input.setCursorPosition(
                    (int) (monster.getX() - player.getX()),
                    Gdx.graphics.getHeight() - (int) (monster.getY() - player.getY())
                );
                Main.getBatch().draw(
                    GameAssetManager.getInstance().getCursorTexture(),
                    monster.getX() - player.getX() + monster.getSprite().getWidth() / 2f,
                    monster.getY() - player.getY() + monster.getSprite().getHeight() / 2f
                );
            }
        }
    }

    private void handleReloading() {
        if (KeyBind.Reload.isJustPressed())
            startReload();
        else if (player.isAutoReload() && player.getAmmo() == 0 && !isReloading)
            startReload();
    }

    private void startReload() {
        isReloading = true;
        int amount = player.getMaxAmmo() - player.getAmmo();
        reloadTime = player.getWeapon().getReloadTime() * amount / player.getMaxAmmo();
    }

    private void updateReloadTime() {
        if (!isReloading)
            return;
        reloadTime -= Gdx.graphics.getDeltaTime();
        if (reloadTime <= 0f) {
            player.setAmmo(player.getMaxAmmo());
            isReloading = false;
        }
    }

    private void handleShoot() {
        if (player.getAmmo() == 0 || isReloading)
            return;
        if (KeyBind.Shoot.isJustPressed()) {
            if (App.isSfxEnabled())
                GameAssetManager.getInstance().getShootSfx().play(1f);
            float[] directions = getBulletDirections();
            for (int i = 0; i < directions.length; i++) {
                bullets.add(new Bullet(
                    Gdx.graphics.getWidth() / 2f + player.getX(),
                    Gdx.graphics.getHeight() / 2f + player.getY(),
                    directions[i],
                    true)
                );
            }
            player.addAmmo(-1);
        }
    }

    private void updateBullets() {
        ArrayList<Bullet> removableBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.move();
            if (bullet.isOut())
                removableBullets.add(bullet);
        }
        bullets.removeAll(removableBullets);
    }

    private void drawBullets() {
        for (Bullet bullet : bullets) {
            bullet.getSprite().setPosition(
                bullet.getX() - player.getX(),
                bullet.getY() - player.getY()
            );
            bullet.getSprite().draw(Main.getBatch());
        }
    }

    private float calcCurrentAngle() {
        float cursorX = Gdx.input.getX();
        float cursorY = Gdx.input.getY();
        return (float) Math.atan2(
            -cursorY + Gdx.graphics.getHeight() / 2f,
            cursorX - Gdx.graphics.getWidth() / 2f
        ) * MathUtils.radiansToDegrees;
    }

    private float[] getBulletDirections() {
        int bulletCount = player.getProjectile();
        float[] result = new float[bulletCount];
        for (int i = 0; i < bulletCount / 2; i++) {
            float offset = i == 0 && bulletCount % 2 == 0? 5: 10;
            result[i] = currentAngle - (i + 1) * offset;
        }
        if (bulletCount % 2 == 1)
            result[bulletCount / 2] = currentAngle;
        for (int i = (bulletCount + 1) / 2; i < bulletCount; i++) {
            float offset = i == (bulletCount + 1) / 2 && bulletCount % 2 == 0? 5: 10;
            result[i] = currentAngle + (i - (bulletCount + 1) / 2 + 1) * offset;
        }
        return result;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
