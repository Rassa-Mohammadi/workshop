package com.tilldawn.controller.menus;

import com.tilldawn.Main;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.GameView;
import com.tilldawn.view.menus.MainMenuView;
import com.tilldawn.view.menus.PregameMenuView;

public class PregameMenuController {
    private PregameMenuView view;

    public void setView(PregameMenuView view) {
        this.view = view;
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToGame() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getInstance().getSkin()));
    }
}
