package com.tilldawn.view.menus.hintmenu;

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
import com.tilldawn.controller.menus.hintmenu.AbilityMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;

public class AbilityMenuView implements Screen {
    private AbilityMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private TextButton backButton;

    public AbilityMenuView(AbilityMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        menuTitle = new Label(Output.AbilitiesMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.backButton = new TextButton(Output.Back.getString(), skin);
        setListeners();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.setFillParent(true);
        table.add(menuTitle).center().pad(20).row();
        Label label = new Label("VITALITY: " + Output.Vitality.getString(), GameAssetManager.getInstance().getSkin());
        label.setColor(Color.GREEN);
        table.add(label).pad(20).row();
        label = new Label("DAMAGER: " + Output.Damager.getString(), GameAssetManager.getInstance().getSkin());
        label.setColor(Color.RED);
        table.add(label).pad(20).row();
        label = new Label("PROCREASE: " + Output.Procrease.getString(), GameAssetManager.getInstance().getSkin());
        label.setColor(Color.YELLOW);
        table.add(label).pad(20).row();
        label = new Label("AMOCREASE: " + Output.Ammocrease.getString(), GameAssetManager.getInstance().getSkin());
        label.setColor(Color.GOLD);
        table.add(label).pad(20).row();
        label = new Label("SPEEDY: " + Output.Speedy.getString(), GameAssetManager.getInstance().getSkin());
        label.setColor(Color.CYAN);
        table.add(label).pad(20).row();
        table.add(backButton).pad(30);
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
