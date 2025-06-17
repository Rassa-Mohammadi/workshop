package com.tilldawn.controller.menus;

import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.menus.MainMenuView;
import com.tilldawn.view.menus.ScoreboardView;

public class ScoreboardController {
    private ScoreboardView view;

    public void setView(ScoreboardView view) {
        this.view = view;
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
