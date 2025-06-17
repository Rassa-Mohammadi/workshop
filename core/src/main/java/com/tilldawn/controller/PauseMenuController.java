package com.tilldawn.controller;

import com.badlogic.gdx.Game;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.EndGameMenuController;
import com.tilldawn.controller.menus.MainMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.GameView;
import com.tilldawn.view.menus.EndGameMenuView;
import com.tilldawn.view.menus.MainMenuView;
import com.tilldawn.view.menus.PauseMenuView;

public class PauseMenuController {
    private PauseMenuView view;
    private GameView pausedGameView;

    public void setView(PauseMenuView view, GameView pausedGameView) {
        this.view = view;
        this.pausedGameView = pausedGameView;
    }

    public void resume() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(pausedGameView);
    }

    public void giveUp() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new EndGameMenuView(
            new EndGameMenuController(),
            GameAssetManager.getInstance().getSkin(),
            false, pausedGameView.getController().getPlayer()
        ));
    }

    public GameView getPausedGameView() {
        return pausedGameView;
    }
}
