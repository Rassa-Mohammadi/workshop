package com.tilldawn.controller.menus.hintmenu;

import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.menus.hintmenu.CheatCodeMenuView;
import com.tilldawn.view.menus.hintmenu.HintMenuView;

public class CheatCodeMenuController {
    private CheatCodeMenuView view;

    public void setView(CheatCodeMenuView view) {
        this.view = view;
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new HintMenuView(new HintMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
