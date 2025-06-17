package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.LoginMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.Result;

public class LoginMenuView implements Screen {
    private LoginMenuController controller;
    public Table table;
    private Stage stage;
    private Label menuTitle;
    private Result loginResult;
    private TextField usernameField;
    private TextField passwordField;
    private TextButton submitButton;
    private TextButton forgetPasswordButton;
    private TextButton backButton;

    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.LoginMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        loginResult = new Result();
        usernameField = new TextField("", skin);
        usernameField.setMessageText(Output.EnterUsername.getString());
        passwordField = new TextField("", skin);
        passwordField.setMessageText(Output.EnterPassword.getString());
        submitButton = new TextButton(Output.Submit.getString(), skin);
        forgetPasswordButton = new TextButton(Output.ForgotPassword.getString(), skin);
        this.backButton = new TextButton(Output.Back.getString(), skin);
        setListeners();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.top().add(menuTitle).padTop(20);
        table.row().pad(10, 0, 10, 0);
        table.add(loginResult.getMessage());
        table.row().pad(10, 0, 10, 0);
        table.add(usernameField).width(GameAssetManager.fieldLength);
        table.row().pad(10, 0, 10, 0);
        table.add(passwordField).width(GameAssetManager.fieldLength);
        table.row().pad(10, 0, 10, 0);
        table.add(forgetPasswordButton);
        table.row().pad(10, 0, 10, 0);
        table.add(submitButton);
        table.row().pad(10, 0, 10, 0);
        table.add(backButton);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        loginResult.update(delta);
        stage.act(delta);
        stage.getBatch().setShader(Main.getBatch().getShader());
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void setListeners() {
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                loginResult.set(controller.login(usernameField.getText(), passwordField.getText()));
            }
        });

        forgetPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                loginResult.set(controller.recoverPassword(usernameField.getText()));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.back();
            }
        });
    }
}
