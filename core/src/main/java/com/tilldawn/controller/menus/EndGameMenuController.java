package com.tilldawn.controller.menus;

import com.tilldawn.Main;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.client.User;
import com.tilldawn.view.menus.EndGameMenuView;
import com.tilldawn.view.menus.MainMenuView;

public class EndGameMenuController {
    private EndGameMenuView view;

    public void setView(EndGameMenuView view) {
        this.view = view;

    }

    public void goToMainMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void updateStats(Player player) {
        User user = App.getLoggedInUser();
        assert user != null;
        user.addPoints((int) player.getSurvivedTime() * player.getKills());
        user.addTotalKills(player.getKills());
        user.setMaxSurvivedTime(Math.max(user.getMaxSurvivedTime(), player.getSurvivedTime()));
    }
}
