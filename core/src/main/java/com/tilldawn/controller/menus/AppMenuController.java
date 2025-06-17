package com.tilldawn.controller.menus;

import com.badlogic.gdx.Gdx;
import com.tilldawn.Main;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.client.User;
import com.tilldawn.view.GameView;
import com.tilldawn.view.menus.*;

public class AppMenuController {
    private AppMenuView view;

    public void setView(AppMenuView view) {
        this.view = view;
    }

    public void goToRegisterMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new RegisterMenuView(new RegisterMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToLoginMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void changeLanguage() {
        App.changeLanguage();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new AppMenuView(new AppMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void createGuest() {
        User user = new User("Guest", "Guest Password", true);
        App.setLoggedInUser(user);
    }

    public void goToGameMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getInstance().getSkin()));
    }

    public void exit() {
//        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new PregameMenuView(new PregameMenuController(), GameAssetManager.getInstance().getSkin()));
//        Main.getMain().setScreen(new SettingMenuView(new SettingMenuController(), GameAssetManager.getInstance().getSkin()));
//            Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getInstance().getSkin()));
//            Main.getMain().setScreen(new AvatarMenuView(new AvatarMenuController(), GameAssetManager.getInstance().getSkin()));
        App.saveUsers();
        Gdx.app.exit();
    }
}
