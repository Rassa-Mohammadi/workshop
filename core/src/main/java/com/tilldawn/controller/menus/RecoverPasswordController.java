package com.tilldawn.controller.menus;

import com.badlogic.gdx.graphics.Color;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.client.User;
import com.tilldawn.view.menus.LoginMenuView;
import com.tilldawn.view.menus.RecoverPasswordMenu;

public class RecoverPasswordController {
    private RecoverPasswordMenu view;
    private User user = null;

    public void setView(RecoverPasswordMenu view) {
        this.view = view;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Result recoverPassword(String answer, String newPassword) {
        if (!user.getSecurityQuestion().getAnswer().equals(answer))
            return new Result(Output.IncorrectAnswer.getString(), Color.RED);
        Result passwordResult = User.isPasswordWeak(newPassword);
        if (passwordResult.hasError())
            return passwordResult;
        user.setPassword(newPassword);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
        return new Result("Password changed successfully", Color.GREEN, false);
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
