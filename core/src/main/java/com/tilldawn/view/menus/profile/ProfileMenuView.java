package com.tilldawn.view.menus.profile;

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
import com.tilldawn.controller.menus.profile.ProfileMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;

public class ProfileMenuView implements Screen {
    private ProfileMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private TextButton changeUsername;
    private TextButton changePassword;
    private TextButton deleteAccountButton;
    private TextButton avatarButton;
    private TextButton backButton;

    public ProfileMenuView(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.ProfileMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.changeUsername = new TextButton(Output.ChangeUsername.getString(), skin);
        this.changePassword = new TextButton(Output.ChangePassword.getString(), skin);
        this.avatarButton = new TextButton(Output.Avatar.getString(), skin);
        this.deleteAccountButton = new TextButton(Output.DeleteAcount.getString(), skin);
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

        Table buttonTable = new Table();
        buttonTable.add(changeUsername).pad(10).fillX().row();
        buttonTable.add(changePassword).pad(10).fillX().row();
        buttonTable.add(avatarButton).pad(10).fillX().row();
        buttonTable.add(deleteAccountButton).pad(10).fillX().row();
        buttonTable.add(backButton).pad(10).row();
        Table contentTable = App.getLoggedInUser().getInfo();

        table.add(menuTitle).colspan(2).center().pad(30).row();
        table.add(buttonTable).left().pad(20);
        table.add(contentTable).right().pad(20);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
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
        changeUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToChangeUsernameMenu();
            }
        });
        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToChangePasswordMenu();
            }
        });
        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.deleteAccount();
            }
        });
        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToAvatarMenu();
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
