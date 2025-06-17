package com.tilldawn.controller.menus.hintmenu;

import com.tilldawn.Main;
import com.tilldawn.controller.menus.MainMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.menus.hintmenu.AbilityMenuView;
import com.tilldawn.view.menus.hintmenu.CheatCodeMenuView;
import com.tilldawn.view.menus.hintmenu.HintMenuView;
import com.tilldawn.view.menus.MainMenuView;

public class HintMenuController {
    private HintMenuView view;

    public void setView(HintMenuView view) {
        this.view = view;
    }

    public void goToAbilityMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new AbilityMenuView(new AbilityMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToCheatCodesMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new CheatCodeMenuView(new CheatCodeMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
