package com.tilldawn.controller.menus;

import com.tilldawn.Main;
import com.tilldawn.controller.menus.hintmenu.HintMenuController;
import com.tilldawn.controller.menus.profile.ProfileMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.menus.*;
import com.tilldawn.view.menus.hintmenu.HintMenuView;
import com.tilldawn.view.menus.profile.ProfileMenuView;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void goToProfile() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToSetting() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SettingMenuView(new SettingMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToPregame() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PregameMenuView(new PregameMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToHint() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new HintMenuView(new HintMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToScoreboard() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ScoreboardView(new ScoreboardController(), GameAssetManager.getInstance().getSkin()));
    }

    public void logout() {
        App.setLoggedInUser(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new AppMenuView(new AppMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
