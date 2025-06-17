package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.tilldawn.controller.menus.EndGameMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.enums.Output;

public class EndGameMenuView implements Screen {
    private EndGameMenuController controller;
    private Player player;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private TextButton continueButton;

    public EndGameMenuView(EndGameMenuController controller, Skin skin, boolean hasWon, Player player) {
        this.controller = controller;
        this.player = player;
        this.menuTitle = new Label(hasWon? Output.YouWon.getString() : Output.YouLost.getString(), skin);
        menuTitle.setFontScale(3f);
        menuTitle.setColor(hasWon? Color.GREEN: Color.RED);
        this.table = new Table();
        this.continueButton = new TextButton(Output.Continue.getString(), skin);
        setListeners();
        this.controller.setView(this);
        controller.updateStats(player);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.top().add(menuTitle).pad(50).row();
        Label label = new Label(
            Output.Username.getString() + ": " + player.getUsername(),
            GameAssetManager.getInstance().getSkin()
        );
        label.setFontScale(1.5f);
        table.add(label).pad(30).row();
        label = new Label(
            Output.TimeSurvived.getString() + player.getFormatedTime(),
            GameAssetManager.getInstance().getSkin()
        );
        label.setFontScale(1.5f);
        table.add(label).pad(30).row();
        label = new Label(
            Output.Kills.getString() + ": " +  player.getKills(),
            GameAssetManager.getInstance().getSkin()
        );
        label.setFontScale(1.5f);
        table.add(label).pad(30).row();
        label = new Label(
            Output.PointsEarned.getString() + (int) (player.getSurvivedTime() * player.getKills()),
            GameAssetManager.getInstance().getSkin()
        );
        label.setColor(Color.GREEN);
        label.setFontScale(1.5f);
        table.add(label).pad(30).row();
        table.add(continueButton).pad(30).row();

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
    public void resize(int width, int height) {

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
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToMainMenu();
            }
        });
    }
}
