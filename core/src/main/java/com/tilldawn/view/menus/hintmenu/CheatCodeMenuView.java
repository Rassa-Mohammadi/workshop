package com.tilldawn.view.menus.hintmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.tilldawn.controller.menus.hintmenu.CheatCodeMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;

public class CheatCodeMenuView implements Screen {
    private CheatCodeMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private TextButton backButton;

    public CheatCodeMenuView(CheatCodeMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.CheatCodesMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.backButton = new TextButton(Output.Back.toString(), skin);
        setListeners();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.setFillParent(true);
        table.center();
        table.add(menuTitle).pad(30).row();
        Label label = new Label("1 -> " + Output.DecreaseTime.getString(), GameAssetManager.getInstance().getSkin());
        table.add(label).pad(20).fillX().row();
        label = new Label("2 -> " + Output.IncreaseLevel.getString(), GameAssetManager.getInstance().getSkin());
        table.add(label).pad(20).fillX().row();
        label = new Label("3 -> " + Output.IncreaseHp.getString(), GameAssetManager.getInstance().getSkin());
        table.add(label).pad(20).fillX().row();
        label = new Label("4 -> " + Output.GoToBossFight.getString(), GameAssetManager.getInstance().getSkin());
        table.add(label).pad(20).fillX().row();
        label = new Label("5 -> " + Output.DestroyMonsters.getString(), GameAssetManager.getInstance().getSkin());
        table.add(label).pad(20).fillX().row();
        table.add(backButton).pad(20).row();

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
