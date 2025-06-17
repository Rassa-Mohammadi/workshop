package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.SettingMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.KeyBind;
import com.tilldawn.model.enums.Output;
import com.tilldawn.model.music.Track;

public class SettingMenuView implements Screen {
    private SettingMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private Slider volumeSlider;
    private SelectBox<String> musicSelectBox;
    private TextButton upButton;
    private TextButton downButton;
    private TextButton rightButton;
    private TextButton leftButton;
    private TextButton reloadButton;
    private TextButton shootButton;
    private CheckBox sfxCheckBox;
    private CheckBox blackAndWhiteCheckBox;
    private CheckBox autoReloadCheckBox;
    private TextButton backButton;
    private boolean isWaiting = false;

    {
        musicSelectBox = new SelectBox<>(GameAssetManager.getInstance().getSkin());
        Array<String> array = new Array<>();
        for (Track track : Track.values()) {
            array.add(track.getName());
        }
        musicSelectBox.setItems(array);
        musicSelectBox.setSelected(GameAssetManager.getInstance().getMusicPlayer().getCurrentTrack().getName());
    }

    public SettingMenuView(SettingMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.SettingMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.volumeSlider = new Slider(0, 1, 0.01f, false, skin);
        volumeSlider.setValue(GameAssetManager.getInstance().getMusicPlayer().getVolume());
        this.upButton = new TextButton(KeyBind.Up.getKeyName(), skin);
        this.downButton = new TextButton(KeyBind.Down.getKeyName(), skin);
        this.rightButton = new TextButton(KeyBind.Right.getKeyName(), skin);
        this.leftButton = new TextButton(KeyBind.Left.getKeyName(), skin);
        this.reloadButton = new TextButton(KeyBind.Reload.getKeyName(), skin);
        this.shootButton = new TextButton(KeyBind.Shoot.getKeyName(), skin);
        this.sfxCheckBox = new CheckBox("SFX", skin);
        sfxCheckBox.setChecked(App.isSfxEnabled());
        this.blackAndWhiteCheckBox = new CheckBox(Output.BlackAndWhite.getString(), skin);
        blackAndWhiteCheckBox.setChecked(Main.getMain().isBlackAndWhite());
        this.autoReloadCheckBox = new CheckBox(Output.AutoReload.getString(), skin);
        autoReloadCheckBox.setChecked(App.getLoggedInUser().isAutoReload());
        this.backButton = new TextButton(Output.Back.getString(), skin);
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

        table.top().add(menuTitle).row();
        Label label = new Label(Output.MusicVolume.getString(), GameAssetManager.getInstance().getSkin());
        label.setFontScale(1.5f);
        table.add(label).padTop(20).row();
        table.add(volumeSlider).width(500).pad(10).padBottom(20).row();
        label = new Label(Output.MusicTrack.getString(), GameAssetManager.getInstance().getSkin());
        label.setFontScale(1.5f);
        table.add(label).padTop(20).row();
        table.add(musicSelectBox).width(500).pad(10).padBottom(20).row();
        label = new Label(Output.KeyBinds.getString(), GameAssetManager.getInstance().getSkin());
        label.setFontScale(1.5f);
        table.add(label).padTop(20).row();
        Table buttonTable = createButtonTable();
        table.add(buttonTable).row();
        table.add(sfxCheckBox).pad(10).row();
        table.add(blackAndWhiteCheckBox).pad(10).row();
        table.add(autoReloadCheckBox).pad(10).row();
        table.add(backButton).pad(10);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        int keycode;
        if ((keycode = controller.getPressedKey()) != -1 && isWaiting)
            controller.bindKey(keycode);
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

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public SelectBox<String> getMusicSelectBox() {
        return musicSelectBox;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    private Table createButtonTable() {
        Table result = new Table();
        Label label = new Label(Output.Up.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10);
        result.add(upButton).pad(10);
        label = new Label(Output.Down.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10);
        result.add(downButton).pad(10);
        label = new Label(Output.Right.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10);
        result.add(rightButton).pad(10);
        label = new Label(Output.Left.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10);
        result.add(leftButton).pad(10);
        label = new Label(Output.Reload.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10);
        result.add(reloadButton).pad(10);
        label = new Label(Output.Shoot.getString(), GameAssetManager.getInstance().getSkin());
        result.add(label).pad(10);
        result.add(shootButton).pad(10);
        return result;
    }

    private void setListeners() {
        volumeSlider.addListener(event -> {
            GameAssetManager.getInstance().getMusicPlayer().setVolume(volumeSlider.getValue());
            return false;
        });

        musicSelectBox.addListener(event -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                GameAssetManager.getInstance().getMusicPlayer().setCurrentTrack(
                    Track.getByName(musicSelectBox.getSelected())
                );
            }
            return false;
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isWaiting)
                    return;
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.back();
            }
        });

        upButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isWaiting)
                    return;
                controller.setKey(upButton, KeyBind.Up);
            }
        });

        downButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isWaiting)
                    return;
                controller.setKey(downButton, KeyBind.Down);
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isWaiting)
                    return;
                controller.setKey(rightButton, KeyBind.Right);
            }
        });

        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isWaiting)
                    return;
                controller.setKey(leftButton, KeyBind.Left);
            }
        });

        reloadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isWaiting)
                    return;
                controller.setKey(reloadButton, KeyBind.Reload);
            }
        });

        shootButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isWaiting)
                    return;
                controller.setKey(shootButton, KeyBind.Reload);
            }
        });

        sfxCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.setSfxEnabled(sfxCheckBox.isChecked());
            }
        });

        blackAndWhiteCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (blackAndWhiteCheckBox.isChecked())
                    Main.getMain().setBlackAndWhiteShader();
                else
                    Main.getMain().removeBlackAndWhiteShader();
            }
        });

        autoReloadCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.getLoggedInUser().setAutoReload(autoReloadCheckBox.isChecked());
            }
        });
    }
}
