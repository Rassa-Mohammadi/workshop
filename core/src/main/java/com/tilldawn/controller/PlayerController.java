package com.tilldawn.controller;

import com.tilldawn.Main;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.enums.KeyBind;

public class PlayerController {
    private GameController gameController;
    private Player player;
    private boolean isFacedRight = true;

    public PlayerController(GameController gameController, Player player) {
        this.gameController = gameController;
        this.player = player;
    }

    public void update() {
        player.updateTimers();
        player.updateSprite();
        handleWalk();
        handleFlip();
        player.getPlayerSprite().draw(Main.getBatch());
    }

    private void handleWalk() {
        player.setRunning(false);
        if (KeyBind.Up.isPressed()) {
            player.addY(player.getSpeed());
            player.setRunning(true);
        }
        if (KeyBind.Down.isPressed()) {
            player.addY(-player.getSpeed());
            player.setRunning(true);
        }
        if (KeyBind.Left.isPressed()) {
            player.addX(-player.getSpeed());
            isFacedRight = false;
            player.setRunning(true);
        }
        if (KeyBind.Right.isPressed()) {
            player.addX(player.getSpeed());
            isFacedRight = true;
            player.setRunning(true);
        }
    }

    private void handleFlip() {
        if (!isFacedRight)
            player.getPlayerSprite().setFlip(true, false);
        if (player.isRunning())
            gameController.getMonsterController().flipMonsters();
    }

}
