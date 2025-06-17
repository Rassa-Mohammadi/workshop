package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.AppMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;

public class AppMenuView implements Screen {
    public Table table;
    private Stage stage;
    private final Texture gameTitleTexture;
    private final Image gameTitleImage;
    private final TextButton registerButton;
    private final TextButton loginButton;
    private final TextButton playAsGuestButton;
    private final TextButton changeLanguageButton;
    private final TextButton exitButton;
    private final AppMenuController controller;

    public AppMenuView(AppMenuController controller, Skin skin) {
        this.controller = controller;
        this.gameTitleTexture = new Texture(Gdx.files.internal("Images/Sprite/T_20Logo.png"));
        this.gameTitleImage = new Image(gameTitleTexture);
        this.registerButton = new TextButton(Output.Register.getString(), skin);
        this.loginButton = new TextButton(Output.Login.getString(), skin);
        this.playAsGuestButton = new TextButton(Output.PlayAsGuest.getString(), skin);
        this.changeLanguageButton = new TextButton(Output.ChangeLanguage.getString(), skin);
        this.exitButton = new TextButton(Output.Exit.getString(), skin);
        this.table = new Table();
        setListeners();
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        gameTitleImage.setScaling(Scaling.fit);
        table.add(gameTitleImage).width(400).height(200).pad(10);

        table.row().pad(10, 0, 10, 0);
        table.add(registerButton).fillX();
        table.row().pad(10, 0, 10, 0);
        table.add(loginButton).fillX();
        table.row().pad(10, 0, 10, 0);
        table.add(changeLanguageButton).fillX();
        table.row().pad(10, 0, 10, 0);
        table.add(playAsGuestButton).fillX();
        table.row().pad(10, 0, 10, 0);
        table.add(exitButton).fillX();

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToRegisterMenu();
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToLoginMenu();
            }
        });

        playAsGuestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.createGuest();
                controller.goToGameMenu();
            }
        });

        changeLanguageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.changeLanguage();
            }
        });

        exitButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               if (App.isSfxEnabled())
                   GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
               controller.exit();
           }
        });
    }
}
