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
import com.tilldawn.controller.menus.RecoverPasswordController;
import com.tilldawn.model.*;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.client.User;

public class RecoverPasswordMenu implements Screen {
    private RecoverPasswordController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private Result recoveryResult;
    private Label securityQuestion;
    private TextField questionAnswer;
    private TextField newPassword;
    private TextButton submitButton;
    private TextButton backButton;


    public RecoverPasswordMenu(User user, RecoverPasswordController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.RecoverPassword.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.recoveryResult = new Result();
        this.securityQuestion = new Label(user.getSecurityQuestion().getQuestion().getString(), skin);
        securityQuestion.setFontScale(1.75f);
        this.questionAnswer = new TextField("", skin);
        questionAnswer.setMessageText(Output.Answer.getString());
        this.newPassword = new TextField("", skin);
        newPassword.setMessageText(Output.EnterNewPassword.getString());
        this.submitButton = new TextButton(Output.Submit.getString(), skin);
        this.backButton = new TextButton(Output.Back.getString(), skin);
        setListeners();
        this.controller.setView(this);
        this.controller.setUser(user);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.top().add(menuTitle).padTop(20).padBottom(20);
        table.row().pad(10, 0, 10, 0);
        table.add(securityQuestion);
        table.row().pad(10, 0, 10, 0);
        table.add(recoveryResult.getMessage());
        table.row().pad(10, 0, 10, 0);
        table.add(questionAnswer).width(GameAssetManager.fieldLength);
        table.row().pad(10, 0, 10, 0);
        table.add(newPassword).width(GameAssetManager.fieldLength);
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
        recoveryResult.update(delta);
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
                recoveryResult.set(controller.recoverPassword(questionAnswer.getText(), newPassword.getText()));
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
