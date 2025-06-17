package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.Result;

public class GameView implements Screen, InputProcessor {
    private final GameController controller;
    private Stage stage;
    private Table table;
    private Result message;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.message = new Result();
        this.controller.setView(this);
    }

    public GameController getController() {
        return controller;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
        table.setFillParent(true);
        table.center();
        table.add(message.getMessage()).padBottom(200);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            controller.goToPauseMenu();
        Main.getBatch().begin();
        controller.updateGame();
        Main.getBatch().end();
        message.update(Gdx.graphics.getDeltaTime());
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

    public void setMessage(Result newMessage) {
        message.set(newMessage);
        message.getMessage().setFontScale(3f);
    }
}
