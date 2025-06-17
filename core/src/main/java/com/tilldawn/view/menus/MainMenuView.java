package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.MainMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;

public class MainMenuView implements Screen {
    private MainMenuController controller;
    public Table table;
    private Stage stage;
    private Label menuTitle;
    private TextButton settingButton;
    private TextButton profileButton;
    private TextButton pregameButton;
    private TextButton scoreboardButton;
    private TextButton hintButton;
    private TextButton loadGameButton;
    private TextButton logoutButton;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.MainMenu.getString(), skin);
        this.menuTitle.setFontScale(2.5f);
        this.settingButton = new TextButton(Output.Settings.getString(), skin);
        this.profileButton = new TextButton(Output.Profile.getString(), skin);
        this.pregameButton = new TextButton(Output.Pregame.getString(), skin);
        this.scoreboardButton = new TextButton(Output.Scoreboard.getString(), skin);
        this.hintButton = new TextButton(Output.Hint.getString(), skin);
        this.loadGameButton = new TextButton(Output.LoadGame.getString(), skin);
        this.logoutButton = new TextButton(Output.Logout.getString(), skin);
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
        Table userInfo = App.getLoggedInUser().getInfo();
        Table buttonTable = new Table();
        buttonTable.add(settingButton).pad(10).fillX();
        buttonTable.add(profileButton).pad(10).fillX().row();
        buttonTable.add(scoreboardButton).pad(10).fillX();
        buttonTable.add(pregameButton).pad(10).fillX().row();
        buttonTable.add(hintButton).pad(10).fillX();
        buttonTable.add(loadGameButton).pad(10).fillX().row();
        buttonTable.add(logoutButton).colspan(2).center().pad(10).fillX().row();

        table.add(menuTitle).colspan(2).center().pad(30).row();
        table.add(buttonTable).left().pad(20);
        table.add(userInfo).right().pad(20);

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
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToSetting();
            }
        });
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToProfile();
            }
        });
        pregameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToPregame();
            }
        });
        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToScoreboard();
            }
        });
        hintButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToHint();
            }
        });
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.logout();
            }
        });
    }
}
