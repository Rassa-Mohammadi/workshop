package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.PauseMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.client.Player;
import com.tilldawn.model.enums.Ability;
import com.tilldawn.model.enums.Output;
import com.tilldawn.view.GameView;

public class PauseMenuView implements Screen {
    private PauseMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private CheckBox blackAndWhiteCheckBox;
    private TextButton resumeButton;
    private TextButton giveUpButton;
    private TextButton saveButton;

    public PauseMenuView(PauseMenuController pauseMenuController, GameView pausedGame, Skin skin) {
        this.controller = pauseMenuController;
        this.table = new Table();
        this.menuTitle = new Label(Output.PauseMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.blackAndWhiteCheckBox = new CheckBox(Output.BlackAndWhite.getString(), skin);
        blackAndWhiteCheckBox.setChecked(Main.getMain().isBlackAndWhite());
        this.resumeButton = new TextButton(Output.Resume.getString(), skin);
        this.giveUpButton = new TextButton(Output.GiveUp.getString(), skin);
        this.saveButton = new TextButton(Output.Save.getString(), skin);
        setListeners();
        this.controller.setView(this, pausedGame);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.add(menuTitle).pad(30).row();
        Table cheatCodes = getCheatCodeTable();
        table.add(getAbilities()).padTop(20).pad(10).row();
        table.add(cheatCodes).row();
        table.add(blackAndWhiteCheckBox).pad(10).row();
        Table tempTable = new Table();
        tempTable.add(resumeButton).pad(10);
        tempTable.add(saveButton).pad(10);
        table.add(tempTable).row();
        table.add(giveUpButton).pad(10).row();

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

    private Table getCheatCodeTable() {
        Table result = new Table();
        Label label = new Label("1 -> " + Output.DecreaseTime.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10).fillX().row();
        label = new Label("2 -> " + Output.IncreaseLevel.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10).fillX().row();
        label = new Label("3 -> " + Output.IncreaseHp.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10).fillX().row();
        label = new Label("4 -> " + Output.GoToBossFight.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10).fillX().row();
        label = new Label("5 -> " + Output.DestroyMonsters.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10).fillX().row();
        return result;
    }

    private Label getAbilities() {
        StringBuilder labelMessage = new StringBuilder();
        Player player = controller.getPausedGameView().getController().getPlayer();
        for (Ability ability : player.getAbilities()) {
            labelMessage.append("    ").append(ability.name());
        }
        Label result = new Label(labelMessage, GameAssetManager.getInstance().getSkin());
        result.setColor(Color.GREEN);
        return result;
    }

    private void setListeners() {
        blackAndWhiteCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (blackAndWhiteCheckBox.isChecked())
                    Main.getMain().setBlackAndWhiteShader();
                else
                    Main.getMain().removeBlackAndWhiteShader();
            }
        });
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.resume();
            }
        });
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.giveUp();
            }
        });
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
            }
        });
    }
}
