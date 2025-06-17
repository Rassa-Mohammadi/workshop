package com.tilldawn.view.menus.hintmenu;

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
import com.tilldawn.controller.menus.hintmenu.HintMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Hero;
import com.tilldawn.model.enums.KeyBind;
import com.tilldawn.model.enums.Output;

public class HintMenuView implements Screen {
    private HintMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private TextButton abilityDescriptionButton;
    private TextButton cheatCodesButton;
    private TextButton backButton;

    public HintMenuView(HintMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.HintMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.abilityDescriptionButton = new TextButton(Output.AbilityDescription.getString(), skin);
        this.cheatCodesButton = new TextButton(Output.CheatCodes.getString(), skin);
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
        table.add(getHeroesDescription()).pad(20).row();
        table.add(getKeyBindsDescription()).pad(20).row();
        Table buttonTable = new Table();
        buttonTable.add(abilityDescriptionButton).pad(10);
        buttonTable.add(cheatCodesButton).pad(10);
        table.add(buttonTable).center().row();
        table.add(backButton).center();
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
        abilityDescriptionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToAbilityMenu();
            }
        });
        cheatCodesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToCheatCodesMenu();
            }
        });
    }

    private Table getHeroesDescription() {
        Table result = new Table();
        for (Hero hero : Hero.values()) {
            result.add(hero.getDescription()).pad(10);
        }
        return result;
    }

    private Table getKeyBindsDescription() {
        Table result = new Table();
        result.add(new Label(Output.Up.getString(), GameAssetManager.getInstance().getSkin())).padRight(5);
        result.add(new TextButton(KeyBind.Up.getKeyName(), GameAssetManager.getInstance().getSkin())).padRight(20);

        result.add(new Label(Output.Down.getString(), GameAssetManager.getInstance().getSkin())).padRight(5);
        result.add(new TextButton(KeyBind.Down.getKeyName(), GameAssetManager.getInstance().getSkin())).padRight(20);

        result.add(new Label(Output.Right.getString(), GameAssetManager.getInstance().getSkin())).padRight(5);
        result.add(new TextButton(KeyBind.Right.getKeyName(), GameAssetManager.getInstance().getSkin())).padRight(20);

        result.add(new Label(Output.Left.getString(), GameAssetManager.getInstance().getSkin())).padRight(5);
        result.add(new TextButton(KeyBind.Left.getKeyName(), GameAssetManager.getInstance().getSkin())).padRight(20);

        result.add(new Label(Output.Reload.getString(), GameAssetManager.getInstance().getSkin())).padRight(5);
        result.add(new TextButton(KeyBind.Reload.getKeyName(), GameAssetManager.getInstance().getSkin())).padRight(20);

        result.add(new Label(Output.Shoot.getString(), GameAssetManager.getInstance().getSkin())).padRight(5);
        result.add(new TextButton(KeyBind.Shoot.getKeyName(), GameAssetManager.getInstance().getSkin())).padRight(20);
        return result;
    }
}
