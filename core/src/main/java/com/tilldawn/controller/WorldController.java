package com.tilldawn.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.client.Player;

public class WorldController {
    private Player player;
    private Sprite background;

    public WorldController(Player player) {
        this.player = player;
        this.background = new Sprite(GameAssetManager.getInstance().getBackgroundTexture());
    }

    public void update() {
        background.setCenter(
            Gdx.graphics.getWidth() / 2f - player.getX(),
            Gdx.graphics.getHeight() / 2f - player.getY()
        );
        background.draw(Main.getBatch());
    }
}
