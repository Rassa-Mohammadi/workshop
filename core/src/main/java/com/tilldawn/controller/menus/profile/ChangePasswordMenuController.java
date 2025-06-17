package com.tilldawn.controller.menus.profile;

import com.badlogic.gdx.graphics.Color;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.client.User;
import com.tilldawn.view.menus.profile.ChangePasswordMenuView;
import com.tilldawn.view.menus.profile.ProfileMenuView;

public class ChangePasswordMenuController {
    private ChangePasswordMenuView view;

    public void setView(ChangePasswordMenuView view) {
        this.view = view;
    }

    public Result changePassword(String oldPassword, String newPassword) {
        if (!App.getLoggedInUser().getPassword().equals(oldPassword))
            return new Result(Output.IncorrectPassword.getString(), Color.RED);
        if (oldPassword.equals(newPassword))
            return new Result(Output.SamePassword.getString(), Color.RED);
        Result result = User.isPasswordWeak(newPassword);
        if (result.hasError())
            return result;
        App.getLoggedInUser().setPassword(newPassword);
        back();
        return new Result("Password changed successfully", Color.GREEN, false);
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
