package com.tilldawn.model.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.model.App;
import com.tilldawn.model.CollisionRect;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Ability;

import java.util.HashSet;
import java.util.Set;

public class Player extends User {
    private Sprite playerSprite;
    private Sprite weaponSprite;
    private float spriteTime = 0f;
    private float invincibleTimer = 0f;
    private float weaponDamageTimer = 0f;
    private float speedTimer = 0f;
    private float posX, posY;
    private int maxHp;
    private int hp;
    private boolean isRunning = false;
    private boolean isAutoAim = false;
    private int maxAmmo;
    private int ammo;
    private int weaponDamage;
    private int weaponProjectile;
    private int kills;
    private int xps;
    private int level;
    private float survivedTime;
    private Set<Ability> abilities;

    public Player(User user) {
        super(user.username, user.password, user.isGuest);
        copyUser(user);
        this.playerSprite = new Sprite(hero.getIdleAnimation().getKeyFrame(0f));
        this.weaponSprite = new Sprite(weapon.getSprite());
        this.posX = 0;
        this.posY = 0;
        playerSprite.setSize(playerSprite.getWidth() * 3.5f, playerSprite.getHeight() * 3.5f);
        playerSprite.setCenter(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        weaponSprite.setCenter(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        weaponSprite.setScale(3f);
        this.maxHp = this.hp = hero.getHp();
        this.maxAmmo = this.ammo = weapon.getMaxAmmo();
        this.weaponDamage = weapon.getDamage();
        this.weaponProjectile = weapon.getProjectile();
        this.kills = 0;
        this.xps = 0;
        this.level = 1;
        this.survivedTime = 0f;
        this.abilities = new HashSet<>();
    }

    public void updateSprite() {
        spriteTime += Gdx.graphics.getDeltaTime();
        Animation<Texture> animation = isRunning? hero.getRunAnimation() : hero.getIdleAnimation();
        if (animation.isAnimationFinished(spriteTime))
            spriteTime = 0f;
        playerSprite.setRegion(animation.getKeyFrame(spriteTime));
        // setting animation when player is damaged
        float transparency;
        if (((int) (invincibleTimer / 0.1f)) % 2 == 1)
            transparency = 0.5f;
        else
            transparency = 1f;
        playerSprite.setColor(playerSprite.getColor().r, playerSprite.getColor().g, playerSprite.getColor().b, transparency);
    }

    public void updateTimers() {
        addSurvivedTime(Gdx.graphics.getDeltaTime());

        invincibleTimer -= Gdx.graphics.getDeltaTime();
        if (invincibleTimer <= 0f)
            invincibleTimer = 0f;

        weaponDamageTimer -= Gdx.graphics.getDeltaTime();
        if (weaponDamageTimer <= 0f) {
            abilities.remove(Ability.DAMAGER);
            weaponDamageTimer = 0f;
        }

        speedTimer -= Gdx.graphics.getDeltaTime();
        if (speedTimer <= 0f) {
            abilities.remove(Ability.SPEEDY);
            speedTimer = 0f;
        }
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public Sprite getWeaponSprite() {
        return weaponSprite;
    }

    public int getSpeed() {
        if (speedTimer > 0f)
            return hero.getSpeed() * 2;
        return hero.getSpeed();
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public void addX(int speed) {
        posX += Gdx.graphics.getDeltaTime() * speed * App.playerMovementCoefficient;
        posX = Math.min(posX, GameAssetManager.backgroundWidth / 2f); // background.png width is 3776
        posX = Math.max(posX, -GameAssetManager.backgroundWidth / 2f);
    }

    public void addY(int speed) {
        posY += Gdx.graphics.getDeltaTime() * speed * App.playerMovementCoefficient;
        posY = Math.min(posY, GameAssetManager.backgroundHeight / 2f); // background.png height is 2688
        posY = Math.max(posY, -GameAssetManager.backgroundHeight / 2f);
    }

    public void setSpriteTime(float spriteTime) {
        this.spriteTime = spriteTime;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void addMaxAmmo(int amount) {
        maxAmmo += amount;
    }

    public int getAmmo() {
        return ammo;
    }

    public void addAmmo(int amount) {
        ammo += amount;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public float getWeaponDamage() {
        if (weaponDamageTimer > 0f)
            return weaponDamage * 1.25f;
        return weaponDamage;
    }

    public void addWeaponDamage(int amount) {
        weaponDamage += amount;
    }

    public int getHp() {
        return hp;
    }

    public void reduceHp(int amount) {
        if (invincibleTimer > 0f)
            return;
        if (App.isSfxEnabled())
            GameAssetManager.getInstance().getHitSfx().play(1f);
        setInvincible();
        this.hp -= amount;
    }

    public void addHp(int amount) {
        hp += amount;
        hp = Math.min(maxHp, hp);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void addMaxHp(int amount) {
        hp += amount;
        maxHp += amount;
    }

    public CollisionRect getCollisionRect() {
        return new CollisionRect(
            Gdx.graphics.getWidth() / 2f - playerSprite.getWidth() / 2f + posX,
            Gdx.graphics.getHeight() / 2f - playerSprite.getHeight() / 2f + posY,
            playerSprite.getWidth(), playerSprite.getHeight()
        );
    }

    private void copyUser(User user) {
        this.isGuest = user.isGuest;
        this.weapon = user.weapon;
        this.hero = user.hero;
        this.gameDuration = user.gameDuration;
        this.autoReload = user.autoReload;
    }

    private void setInvincible() {
        if (invincibleTimer > 0f)
            return;
        invincibleTimer = 2f;
    }

    public void setWeaponDamageTimer() {
        weaponDamageTimer = 10f;
    }

    public void setSpeedTimer() {
        speedTimer = 10f;
    }

    public int getProjectile() {
        return weaponProjectile;
    }

    public void addProjectile(int amount) {
        weaponProjectile += amount;
    }

    public boolean isAutoAim() {
        return isAutoAim;
    }

    public void setAutoAim(boolean autoAim) {
        isAutoAim = autoAim;
    }

    public void addKill(int amount) {
        kills += amount;
    }

    public int getKills() {
        return kills;
    }

    public void addXp(int amount) {
        xps += amount;
    }

    public int getXps() {
        return xps;
    }

    public boolean hasLevelUp() {
        if (xps >= 20 * level) {
            xps -= 20 * level;
            ++level;
            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel(int amount) {
        level += amount;
    }

    public float getSurvivedTime() {
        return survivedTime;
    }

    public void addSurvivedTime(float amount) {
        survivedTime += amount;
    }

    public void setSurvivedTime(float survivedTime) {
        this.survivedTime = survivedTime;
    }

    public String getFormatedTime() {
        int seconds = (int) (survivedTime / 60);
        int minutes = (int) (survivedTime % 60);
        String sec = seconds < 10? "0" + seconds: "" + seconds;
        String min = minutes < 10? "0" + minutes: "" + minutes;
        return sec + ":" + min;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void reduceGameDuration(int amount) {
        gameDuration -= amount;
    }
}
