package com.tilldawn.controller.menus.hintmenu;

import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.menus.hintmenu.AbilityMenuView;
import com.tilldawn.view.menus.hintmenu.HintMenuView;

public class AbilityMenuController {
    private AbilityMenuView view;

    public void setView(AbilityMenuView view) {
        this.view = view;
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new HintMenuView(new HintMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
