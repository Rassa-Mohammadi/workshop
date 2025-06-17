package com.tilldawn.controller.menus;

import com.badlogic.gdx.graphics.Color;
import com.tilldawn.Main;
import com.tilldawn.model.*;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.client.User;
import com.tilldawn.view.menus.AppMenuView;
import com.tilldawn.view.menus.RegisterMenuView;

public class RegisterMenuController {
    private RegisterMenuView view;

    public void setView(RegisterMenuView view) {
        this.view = view;
    }

    public Result register(String username,
                            String password,
                            String confirmedPassword,
                            String questionString,
                            String answer) {
        if (App.getUser(username) != null)
            return new Result(Output.UsernameExists.getString(), Color.RED);
        if (password.isEmpty() || confirmedPassword.isEmpty())
            return new Result(Output.PasswordEmpty.getString(), Color.RED);
        if (answer.isEmpty())
            return new Result(Output.AnswerEmpty.getString(), Color.RED);
        if (!password.equals(confirmedPassword))
            return new Result(Output.ReenterPasswordError.getString(), Color.RED);
        Result passwordResult = User.isPasswordWeak(password);
        if (passwordResult.hasError())
            return passwordResult;
        Output question = Output.getPhrase(questionString);
        assert question != null;
        User user = new User(username, password, false);
        user.setSecurityQuestion(new SecurityQuestion(question, answer));
        App.addUser(user);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new AppMenuView(new AppMenuController(), GameAssetManager.getInstance().getSkin()));
        return new Result("Successful registration", Color.GREEN, false);
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new AppMenuView(new AppMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
