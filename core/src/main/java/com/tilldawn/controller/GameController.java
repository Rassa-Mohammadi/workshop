package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.ChooseAbilityMenuController;
import com.tilldawn.controller.menus.EndGameMenuController;
import com.tilldawn.model.*;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.monsters.Monster;
import com.tilldawn.view.GameView;
import com.tilldawn.view.XpDrop;
import com.tilldawn.view.menus.ChooseAbilityMenuView;
import com.tilldawn.view.menus.EndGameMenuView;
import com.tilldawn.view.menus.PauseMenuView;

import java.util.ArrayList;

public class GameController {
    private GameView view;
    private WorldController worldController;
    private PlayerController playerController;
    private WeaponController weaponController;
    private StatusController statusController;
    private MonsterController monsterController;
    private Player player;

    public void setView(GameView view) {
        this.view = view;
        this.player = new Player(App.getLoggedInUser());
        worldController = new WorldController(player);
        playerController = new PlayerController(this, player);
        weaponController = new WeaponController(this, player);
        monsterController = new MonsterController(this, player);
        statusController = new StatusController(player);
    }

    public void updateGame() {
        if (view == null)
            return;
        if (player.getSurvivedTime() >= player.getGameDuration() * 60)
            goToEndGameMenu(true);
        if (player.getHp() <= 0)
            goToEndGameMenu(false);
        checkMonsterCollision();
        checkBulletCollision();
        harvestXp();
        handleCheatCodes();
        worldController.update();
        monsterController.update();
        playerController.update();
        weaponController.update();
        statusController.update();
    }

    public void goToPauseMenu() {
        Main.getMain().setCursor();
        player.setAutoAim(false);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PauseMenuView(new PauseMenuController(), view, GameAssetManager.getInstance().getSkin()));
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public StatusController getStatusController() {
        return statusController;
    }

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public GameView getView() {
        return view;
    }

    public Player getPlayer() {
        return player;
    }

    private void goToEndGameMenu(boolean hasWon) {
        Main.getMain().setCursor();
        player.setAutoAim(false);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new EndGameMenuView(
            new EndGameMenuController(),
            GameAssetManager.getInstance().getSkin(),
            hasWon,
            player
        ));
    }

    private void handleCheatCodes() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.addSurvivedTime(60);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            player.addLevel(1);
            upgradePlayer();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            player.addHp(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            player.setSurvivedTime(Math.max(player.getSurvivedTime(), (float) (player.getGameDuration() * 60) / 2));
            monsterController.generateElderMonster();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            monsterController.clearMonsters();
        }
    }

    private void checkMonsterCollision() {
        for (Monster monster : monsterController.getMonsters()) {
            if (monster.getCollisionRect().collide(player.getCollisionRect())) {
                player.reduceHp(1);
            }
        }
    }

    private void checkBulletCollision() {
        ArrayList<Bullet> removableBullets = new ArrayList<>();
        for (Bullet bullet : weaponController.getBullets()) {
            // bullet collision with monsters
            for (Monster monster : monsterController.getMonsters()) {
                if (bullet.getCollisionRect().collide(monster.getCollisionRect()) && bullet.isFriendly()) {
                    monster.reduceHp(player.getWeaponDamage());
                    monster.setKnockback();
                    removableBullets.add(bullet);
                }
            }
        }
        weaponController.getBullets().removeAll(removableBullets);
        // bullet collision with players
        removableBullets = new ArrayList<>();
        for (Bullet bullet : monsterController.getBullets()) {
            if (bullet.getCollisionRect().collide(player.getCollisionRect())) {
                player.reduceHp(1);
                removableBullets.add(bullet);
            }
        }
        monsterController.getBullets().removeAll(removableBullets);
    }

    private void harvestXp() {
        ArrayList<XpDrop> removableXpDrops = new ArrayList<>();
        for (XpDrop xpDrop : monsterController.getXpDrops()) {
            if (xpDrop.getCollisionRect().collide(player.getCollisionRect())) {
                removableXpDrops.add(xpDrop);
                player.addXp(3);
                statusController.getLevelBar().setValue(player.getXps());
                if (player.hasLevelUp())
                    upgradePlayer();
            }
        }
        monsterController.getXpDrops().removeAll(removableXpDrops);
    }

    private void upgradePlayer() {
        if (App.isSfxEnabled())
            GameAssetManager.getInstance().getLevelUpSfx().play(1f);
        statusController.getLevelBar().setRange(0, player.getLevel() * 20);
        statusController.getLevelBar().setValue(player.getXps());
        chooseAbility();
    }

    private void chooseAbility() {
        Main.getMain().setCursor();
        player.setAutoAim(false);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ChooseAbilityMenuView(
            new ChooseAbilityMenuController(),
            GameAssetManager.getInstance().getSkin(),
            view
        ));
    }
}
