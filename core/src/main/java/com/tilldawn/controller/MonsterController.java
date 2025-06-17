package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.enums.MonsterType;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.monsters.Elder;
import com.tilldawn.model.monsters.EyeBat;
import com.tilldawn.model.monsters.Monster;
import com.tilldawn.view.XpDrop;

import java.util.ArrayList;
import java.util.Random;

public class MonsterController {
    private GameController gameController;
    private Player player;
    private ArrayList<Monster> monsters;
    private ArrayList<XpDrop> xpDrops;
    private ArrayList<Bullet> bullets;
    private float tentacleMonsterSpawnTimer = 3f;
    private float eyeBatMonsterSpawnTimer = 10f;
    private float zoneTimer = 0f;
    private boolean bossFightStarted = false;
    private boolean elderIsDead = false;

    public MonsterController(GameController gameController, Player player) {
        this.gameController = gameController;
        this.player = player;
        this.monsters = new ArrayList<>();
        this.xpDrops = new ArrayList<>();
        this.bullets = new ArrayList<>();
        generateTrees();
    }

    public void update() {
        updateTimers();
        generateTentacleMonsters(player.getSurvivedTime());
        generateEyeBat(player.getSurvivedTime());
        generateElderMonster();
        cleanMonsters();
        checkZone();
        updateSprites();
        updateBullets();
        monsterAttack();
        moveMonsters();
        draw();
        setTimers();
    }

    public Monster getClosestMonster() {
        Monster closestMonster = null;
        float bestDistance = Float.MAX_VALUE;
        for (Monster monster : monsters) {
            if (monster.getType() == MonsterType.Tree)
                continue;
            float deltaX = player.getX() + Gdx.graphics.getWidth() / 2f - monster.getX();
            float deltaY = player.getY() + Gdx.graphics.getHeight() / 2f - monster.getY();
            float distance = deltaX * deltaX + deltaY * deltaY;
            if (distance < bestDistance) {
                bestDistance = distance;
                closestMonster = monster;
            }
        }
        return closestMonster;
    }

    public void clearMonsters() {
        ArrayList<Monster> removableMonsters = new ArrayList<>();
        for (Monster monster : monsters) {
            if (monster.getType() != MonsterType.Tree && monster.getType() != MonsterType.Elder)
                removableMonsters.add(monster);
        }
        monsters.removeAll(removableMonsters);
    }

    public void flipMonsters() {
        for (Monster monster : monsters) {
            if (monster.getX() > player.getX() + Gdx.graphics.getWidth() / 2f)
                monster.setFlipped(true);
            else
                monster.setFlipped(false);
        }
    }

    public void generateElderMonster() {
        if (player.getSurvivedTime() < (float) (player.getGameDuration() * 60) / 2 || bossFightStarted)
            return;
        Monster elder = new Elder(MonsterType.Elder, 0, 0);
        setPosition(elder);
        monsters.add(elder);
        bossFightStarted = true;
        gameController.getView().setMessage(new Result(Output.BossFightStarted.getString(), Color.RED));
    }

    private void updateTimers() {
        for (Monster monster : monsters) {
            monster.updateKnockback();
            if (monster instanceof EyeBat)
                ((EyeBat) monster).updateShootTimer();
            if (monster instanceof Elder)
                ((Elder) monster).updateTimer();
        }
        tentacleMonsterSpawnTimer -= Gdx.graphics.getDeltaTime();
        eyeBatMonsterSpawnTimer -= Gdx.graphics.getDeltaTime();
        if (bossFightStarted)
            zoneTimer += Gdx.graphics.getDeltaTime();
    }

    private void setTimers() {
        if (tentacleMonsterSpawnTimer <= 0f)
            tentacleMonsterSpawnTimer = 3f;

        if (eyeBatMonsterSpawnTimer <= 0f)
            eyeBatMonsterSpawnTimer = 10f;
    }

    private void checkZone() {
        float radius = getZoneSize() / 2f;
        float deltaX = player.getX() * player.getX();
        float deltaY = player.getY() * player.getY();
        if (deltaX + deltaY > radius * radius) {
            player.reduceHp(1);
        }
    }

    private void cleanMonsters() {
        ArrayList<Monster> removableMonsters = new ArrayList<>();
        for (Monster monster : monsters) {
            if (monster.isDead()) {
                removableMonsters.add(monster);
                player.addKill(1);
                xpDrops.add(new XpDrop(monster.getX(), monster.getY()));
                if (monster instanceof Elder)
                    elderIsDead = true;
            }
        }
        monsters.removeAll(removableMonsters);
    }

    private void updateSprites() {
        for (Monster monster : monsters) {
            monster.updateSprite();
        }

        for (XpDrop xpDrop : xpDrops) {
            xpDrop.updateSprite();
        }
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            if (monster instanceof Elder) {
                Elder elder = (Elder) monster;
                Vector2 direction = getMovementDirection(elder, elder.getMoveTimer() > 0f);
                ((Elder) monster).dash(direction);
            }
            else {
                Vector2 direction = getMovementDirection(monster, false);
                monster.move(direction);
            }
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

    private void monsterAttack() {
        for (Monster monster : monsters) {
            if (monster instanceof EyeBat) {
                shoot((EyeBat) monster);
            }
        }
    }


    private void shoot(EyeBat eyeBat) {
        if (eyeBat.getShootTimer() > 0f)
            return;
        float angle = calculateAngle(eyeBat);
        bullets.add(new Bullet(
            eyeBat.getX(),
            eyeBat.getY(),
            angle,
            false
        ));
        eyeBat.setShootTimer();
    }

    private float calculateAngle(EyeBat eyeBat) {
        float playerX = player.getX() + Gdx.graphics.getWidth() / 2f;
        float playerY = player.getY() + Gdx.graphics.getHeight() / 2f;
        return MathUtils.atan2(playerY - eyeBat.getY(), playerX - eyeBat.getX()) * MathUtils.radiansToDegrees;
    }

    private void draw() {
        // drawing monsters
        for (Monster monster : monsters) {
            monster.getSprite().setPosition(
                monster.getX() - player.getX(),
                monster.getY() - player.getY()
            );
            monster.getSprite().draw(Main.getBatch());
        }
        // drawing xp drops
        for (XpDrop xpDrop : xpDrops) {
            xpDrop.getSprite().setPosition(
                xpDrop.getX() - player.getX(),
                xpDrop.getY() - player.getY()
            );
            xpDrop.getSprite().draw(Main.getBatch());
        }
        // drawing bullets
        for (Bullet bullet : bullets) {
            bullet.getSprite().setPosition(
                bullet.getX() - player.getX(),
                bullet.getY() - player.getY()
            );
            bullet.getSprite().draw(Main.getBatch());
        }
        // drawing zone
        if (bossFightStarted && !elderIsDead) {
            Sprite zoneSprite = new Sprite(GameAssetManager.getInstance().getZoneTexture());
            zoneSprite.setSize(getZoneSize(), getZoneSize());
            zoneSprite.setCenter(
                Gdx.graphics.getWidth() / 2f - player.getX(),
                Gdx.graphics.getHeight() / 2f - player.getY()
            );
            zoneSprite.draw(Main.getBatch());
        }
    }

    private float getZoneSize() {
        float size = GameAssetManager.getInstance().getBackgroundTexture().getWidth() * 1.5f;
        size *= (1 - (zoneTimer / 120));
        return size;
    }

    private void generateTentacleMonsters(float timePassed) {
        if (tentacleMonsterSpawnTimer > 0f)
            return;
        for (int i = 0; i < timePassed / 90f; i++) {
            Monster tentacle = new Monster(MonsterType.TentacleMonster, 0, 0);
            setPosition(tentacle);
            monsters.add(tentacle);
        }
    }

    private void generateEyeBat(float timePassed) {
        if (eyeBatMonsterSpawnTimer > 0f)
            return;
        if (timePassed < (float) (player.getGameDuration() * 60) / 4)
            return;
        for (int i = 0; i < (4 * player.getSurvivedTime() - player.getGameDuration() * 60 + 30) / 90; i++) {
            Monster eyeBat = new EyeBat(MonsterType.EyeBat, 0, 0);
            setPosition(eyeBat);
            monsters.add(eyeBat);
        }
    }

    private void generateTrees() {
        float backgroundWidth = GameAssetManager.getInstance().getBackgroundTexture().getWidth();
        float backgroundHeight = GameAssetManager.getInstance().getBackgroundTexture().getHeight();
        float treeWidth = MonsterType.Tree.getAnimation().getKeyFrame(0f).getWidth() * 2;
        float treeHeight = MonsterType.Tree.getAnimation().getKeyFrame(0f).getHeight() * 2;
        Random random = new Random();
        int treeCount = 10 + random.nextInt(10);
        for (int i = 0; i < treeCount; i++) {
            float minX = Gdx.graphics.getWidth() / 2f - backgroundWidth / 2f + treeWidth;
            float maxX = Gdx.graphics.getWidth() / 2f + backgroundWidth / 2f - treeWidth;
            float x = minX + random.nextFloat() * (maxX - minX);

            float minY = Gdx.graphics.getHeight() / 2f - backgroundHeight / 2f + treeHeight;
            float maxY = Gdx.graphics.getHeight() / 2f + backgroundHeight / 2f - treeHeight;
            float y = minY + random.nextFloat() * (maxY - minY);

            Monster tree = new Monster(MonsterType.Tree, x, y);
            monsters.add(tree);
        }
    }

    private Vector2 getMovementDirection(Monster monster, boolean isDashing) {
        float deltaX = player.getX() + Gdx.graphics.getWidth() / 2f - monster.getX();
        float deltaY = player.getY() + Gdx.graphics.getHeight() / 2f - monster.getY();
        Vector2 direction = new Vector2(deltaX, deltaY);
        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        direction.x /= distance;
        direction.y /= distance;
        direction.x *= isDashing? monster.getType().getDashSpeed(): monster.getType().getSpeed();
        direction.y *= isDashing? monster.getType().getDashSpeed(): monster.getType().getSpeed();
        direction.x *= Gdx.graphics.getDeltaTime();
        direction.y *= Gdx.graphics.getDeltaTime();
        direction.x *= App.monsterMovementCoefficient;
        direction.y *= App.monsterMovementCoefficient;
        return direction;
    }

    private void setPosition(Monster monster) {
        float backgroundWidth = GameAssetManager.getInstance().getBackgroundTexture().getWidth();
        float backgroundHeight = GameAssetManager.getInstance().getBackgroundTexture().getHeight();
        float monsterWidth = monster.getSprite().getWidth();
        float monsterHeight = monster.getSprite().getHeight();
        Random random = new Random();
        int side = random.nextInt(4); // 0: up, 1: down, 2: right, 3: left
        float x, y;
        if (side == 0) {
            y = Gdx.graphics.getHeight() / 2f + backgroundHeight / 2f - monsterHeight;
            float minX = Gdx.graphics.getWidth() / 2f - backgroundWidth / 2f + monsterWidth;
            float maxX = Gdx.graphics.getWidth() / 2f + backgroundWidth / 2f - monsterWidth;
            x = minX + random.nextFloat() * (maxX - minX);
        }
        else if (side == 1) {
           y = Gdx.graphics.getHeight() / 2f - backgroundHeight / 2f + monsterHeight;
            float minX = Gdx.graphics.getWidth() / 2f - backgroundWidth / 2f + monsterWidth;
            float maxX = Gdx.graphics.getWidth() / 2f + backgroundWidth / 2f - monsterWidth;
            x = minX + random.nextFloat() * (maxX - minX);
        }
        else if (side == 2) {
            x = Gdx.graphics.getWidth() / 2f + backgroundWidth / 2f - monsterWidth;
            float minY = Gdx.graphics.getHeight() / 2f - backgroundHeight / 2f + monsterHeight;
            float maxY = Gdx.graphics.getHeight() / 2f + backgroundHeight / 2f - monsterHeight;
            y = minY + random.nextFloat() * (maxY - minY);
        }
        else {
            x = Gdx.graphics.getWidth() / 2f - backgroundWidth / 2f + monsterWidth;
            float minY = Gdx.graphics.getHeight() / 2f - backgroundHeight / 2f + monsterHeight;
            float maxY = Gdx.graphics.getHeight() / 2f + backgroundHeight / 2f - monsterHeight;
            y = minY + random.nextFloat() * (maxY - minY);
        }
        monster.setPosX(x);
        monster.setPosY(y);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<XpDrop> getXpDrops() {
        return xpDrops;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
