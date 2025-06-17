package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.PregameMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Hero;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.enums.Weapon;

public class PregameMenuView implements Screen {
    private PregameMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private Image weaponImage;
    private SelectBox<String> weaponsSelectBox;
    private Image heroImage;
    private SelectBox<String> heroesSelectBox;
    private Label gameDuration;
    private Slider gameDurationSlider;
    private final int[] durations = {2, 5, 10, 20};
    private TextButton playButton;
    private TextButton backButton;

    {
        initWeaponSelectBox();
        initHeroesSelectBox();
    }

    public PregameMenuView(PregameMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.PregameMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.backButton = new TextButton(Output.Back.getString(), skin);
        this.playButton = new TextButton(Output.Play.getString(), skin);
        this.gameDurationSlider = new Slider(0, 3, 1, false, skin);
        setListeners();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.setFillParent(true);
        table.defaults().pad(10);

        table.add(menuTitle).colspan(3).row();
        table.add(getWeaponTable()).pad(50).left().width(350);
        table.add(getDuraionTable()).pad(50).center().width(350);
        table.add(getHeroesTable()).pad(50).right().width(350);
        table.row();
        table.add(playButton).colspan(3).pad(10).center().width(GameAssetManager.backButtonLength).row();
        table.add(backButton).colspan(3).center().width(GameAssetManager.backButtonLength);
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

    private Table getWeaponTable() {
        Table result = new Table();
        weaponImage = new Image(Weapon.getTexture(weaponsSelectBox.getSelected()));
        weaponImage.setScaling(Scaling.fit);
        Label label = new Label(Output.SelectWeapon.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).center().pad(10).row();
        result.add(weaponImage).size(200, 200).padBottom(30).row();
        result.add(weaponsSelectBox).fillX().pad(10).row();
        return result;
    }

    private Table getHeroesTable() {
        Table result = new Table();
        heroImage = new Image(Hero.getTexture(heroesSelectBox.getSelected()));
        heroImage.setScaling(Scaling.fit);
        Label label = new Label(Output.SelectHero.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).center().pad(10).row();
        result.add(heroImage).size(350, 350).padBottom(30).row();
        result.add(heroesSelectBox).fillX().pad(10).row();
        return result;
    }

    private Table getDuraionTable() {
        Table result = new Table();
        int currentDuration = durations[(int) gameDurationSlider.getValue()];
        gameDuration = new Label(
            Output.GameDuration.getString() + currentDuration,
            GameAssetManager.getInstance().getSkin()
        );
        gameDuration.setColor(Color.GREEN);
        gameDuration.setFontScale(1.5f);
        Label label = new Label(Output.SelectGameDuration.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).center().pad(10).row();
        result.add(gameDuration).padBottom(30).row();
        result.add(gameDurationSlider).fillX().pad(10).row();
        return result;
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

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.goToGame();
            }
        });

        weaponsSelectBox.addListener(event -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                Weapon newWeapon = Weapon.getWeapon(weaponsSelectBox.getSelected());
                App.getLoggedInUser().setWeapon(newWeapon);
                weaponImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newWeapon.getTexture())));
            }
            return false;
        });
        heroesSelectBox.addListener(event -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                Hero newHero = Hero.getHero(heroesSelectBox.getSelected());
                App.getLoggedInUser().setHero(newHero);
                heroImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newHero.getTexture())));
            }
            return false;
        });
        gameDurationSlider.addListener(event -> {
            Integer newGameDuration = durations[(int) gameDurationSlider.getValue()];
            App.getLoggedInUser().setGameDuration(newGameDuration);
            gameDuration.setText(Output.GameDuration.getString() + newGameDuration);
            return false;
        });
    }

    private void initWeaponSelectBox() {
        weaponsSelectBox = new SelectBox<>(GameAssetManager.getInstance().getSkin());
        Array<String> array = new Array<>();
        for (Weapon weapon : Weapon.values()) {
            array.add(weapon.getName());
        }
        weaponsSelectBox.setItems(array);
        weaponsSelectBox.setSelected(App.getLoggedInUser().getWeapon().getName());
    }

    private void initHeroesSelectBox() {
        heroesSelectBox = new SelectBox<>(GameAssetManager.getInstance().getSkin());
        Array<String> array = new Array<>();
        for (Hero hero : Hero.values()) {
            array.add(hero.getName());
        }
        heroesSelectBox.setItems(array);
        heroesSelectBox.setSelected(App.getLoggedInUser().getHero().getName());
    }
}
