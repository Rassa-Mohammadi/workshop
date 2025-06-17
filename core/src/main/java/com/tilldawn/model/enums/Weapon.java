package com.tilldawn.model.enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.w3c.dom.Text;

public enum Weapon {
    Revolver(20, 1, 1, 6, new Texture(Gdx.files.internal("Images/Texture2D/RevolverStill.png"))),
    Shotgun(10, 4, 1, 2, new Texture("Images/Texture2D/T_Shotgun_SS_0.png")),
    SMG(8, 1, 2, 24, new Texture(Gdx.files.internal("Images/Texture2D/T_DualSMGs_Icon.png")));

    private final int damage;
    private final int projectile;
    private final float reloadTime;
    private final int maxAmmo;
    private final Texture texture;

    Weapon(int damage, int projectile, int reloadTime, int maxAmmo, Texture texture) {
        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
        this.texture = texture;
    }

    public String getName() {
        return this.name();
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return new Sprite(texture);
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public static Texture getTexture(String weaponName) {
        for (Weapon weapon : values()) {
            if (weapon.getName().equals(weaponName))
                return weapon.texture;
        }
        return null;
    }

    public static Weapon getWeapon(String weaponName) {
        for (Weapon weapon : values()) {
            if (weapon.getName().equals(weaponName))
                return weapon;
        }
        return null;
    }
}
