package com.tilldawn.view.menus.profile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.profile.ChangePasswordMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.Result;

public class ChangePasswordMenuView implements Screen {
    private ChangePasswordMenuController controller;
    private Stage stage;
    private Table table;
    private Result changeResult;
    private TextField oldPasswordField;
    private TextField newPasswordField;
    private TextButton changePasswordButton;
    private TextButton backButton;

    public ChangePasswordMenuView(ChangePasswordMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.changeResult = new Result();
        this.oldPasswordField = new TextField("", skin);
        oldPasswordField.setMessageText(Output.EnterOldPassword.getString());
        this.newPasswordField = new TextField("", skin);
        newPasswordField.setMessageText(Output.EnterNewPassword.getString());
        this.changePasswordButton = new TextButton(Output.ChangePassword.getString(), skin);
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

        table.add(changeResult.getMessage()).pad(10).row();
        table.add(oldPasswordField).width(GameAssetManager.fieldLength).pad(10).row();
        table.add(newPasswordField).width(GameAssetManager.fieldLength).pad(10).row();
        table.add(changePasswordButton).pad(10).row();
        table.add(backButton).pad(10).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        changeResult.update(delta);
        stage.act(delta);
        stage.getBatch().setShader(Main.getBatch().getShader());
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

    public void setListeners() {
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                changeResult.set(controller.changePassword(oldPasswordField.getText(), newPasswordField.getText()));
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
