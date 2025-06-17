package com.tilldawn.controller.menus;

import com.badlogic.gdx.graphics.Color;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.client.User;
import com.tilldawn.view.menus.AppMenuView;
import com.tilldawn.view.menus.LoginMenuView;
import com.tilldawn.view.menus.MainMenuView;
import com.tilldawn.view.menus.RecoverPasswordMenu;

public class LoginMenuController {
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public Result login(String username, String password) {
        User user = App.getUser(username);
        if (user == null)
            return new Result(Output.UsernameNotFound.getString(), Color.RED);
        if (!user.getPassword().equals(password))
            return new Result(Output.IncorrectPassword.getString(), Color.RED);
        App.setLoggedInUser(user);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
        return new Result("Login Successful", Color.GREEN, false);
    }

    public Result recoverPassword(String username) {
        User user = App.getUser(username);
        if (user == null)
            return new Result(Output.UsernameNotFound.getString(), Color.RED);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new RecoverPasswordMenu(user, new RecoverPasswordController(), GameAssetManager.getInstance().getSkin()));
        return new Result("Redirecting to recover password", Color.GREEN, false);
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new AppMenuView(new AppMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
