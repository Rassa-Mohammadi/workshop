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
import com.tilldawn.controller.menus.ChooseAbilityMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Ability;
import com.tilldawn.model.enums.Output;
import com.tilldawn.view.GameView;

import java.util.List;

public class ChooseAbilityMenuView implements Screen {
    private ChooseAbilityMenuController controller;
    private GameView pausedGame;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private SelectBox<String> abilitySelectBox;
    private TextButton continueButton;

    public ChooseAbilityMenuView(ChooseAbilityMenuController controller, Skin skin, GameView pausedGame) {
        this.controller = controller;
        this.table = new Table();
        this.pausedGame = pausedGame;
        this.menuTitle = new Label(Output.ChooseAbility.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.continueButton = new TextButton(Output.Continue.getString(), skin);
        initAbilitySelectBox();
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

        table.add(menuTitle).pad(30).row();
        table.add(abilitySelectBox).pad(20).row();
        table.add(continueButton).pad(20).row();

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

    private void initAbilitySelectBox() {
        abilitySelectBox = new SelectBox<>(GameAssetManager.getInstance().getSkin());
        List<Ability> abilityList = Ability.get3RandomAbility();
        Array<String> array = new Array<>();
        for (Ability ability : abilityList) {
            array.add(ability.name());
        }
        abilitySelectBox.setItems(array);
    }

    private void setListeners() {
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.back(pausedGame, abilitySelectBox.getSelected());
            }
        });

    }
}
