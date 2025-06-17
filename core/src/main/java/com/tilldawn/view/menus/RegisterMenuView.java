package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.RegisterMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.Result;

public class RegisterMenuView implements Screen {
    public Table table;
    private RegisterMenuController controller;
    private Stage stage;
    private Label menuTitle;
    private Result registerResult;
    private TextField username;
    private TextField password;
    private TextField confirmedPassword;
    private SelectBox<String> questions;
    private TextField answer;
    private TextButton submitButton;
    private TextButton backButton;

    {
        questions = new SelectBox<>(GameAssetManager.getInstance().getSkin());
        Array<String> array = new Array<>();
        array.add(Output.Turk.getString());
        array.add(Output.FatherName.getString());
        questions.setItems(array);
    }

    public RegisterMenuView(RegisterMenuController controller, Skin skin) {
        this.controller = controller;
        this.menuTitle = new Label(Output.RegisterMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.registerResult = new Result();
        this.username = new TextField("", skin);
        this.username.setMessageText(Output.EnterUsername.getString());
        this.password = new TextField("", skin);
        this.password.setMessageText(Output.EnterPassword.getString());
        this.confirmedPassword = new TextField("", skin);
        this.confirmedPassword.setMessageText(Output.EnterPasswordAgain.getString());
        this.answer = new TextField("", skin);
        this.answer.setMessageText(Output.Answer.getString());
        this.submitButton = new TextButton(Output.Submit.getString(), skin);
        this.backButton = new TextButton(Output.Back.getString(), skin);
        this.table = new Table();
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
        table.add(registerResult.getMessage());
        table.row().pad(10, 0, 10, 0);
        table.add(username).width(GameAssetManager.fieldLength);
        table.row().pad(10, 0, 10, 0);
        table.add(password).width(GameAssetManager.fieldLength);
        table.row().pad(10, 0, 10, 0);
        table.add(confirmedPassword).width(GameAssetManager.fieldLength);
        table.row().pad(10, 0, 10, 0);
        table.add(questions);
        table.row().pad(10, 0, 10, 0);
        table.add(answer).width(GameAssetManager.fieldLength);
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
        registerResult.update(delta);
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
                registerResult.set(controller.register(
                    username.getText(),
                    password.getText(),
                    confirmedPassword.getText(),
                    questions.getSelected(),
                    answer.getText()
                ));
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
