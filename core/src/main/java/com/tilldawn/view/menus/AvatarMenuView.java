package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.AvatarMenuController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.Output;

public class AvatarMenuView implements Screen {
    private AvatarMenuController controller;
    private Stage stage;
    private Table table;
    private Label menuTitle;
    private Image currentAvatar;
    private Table avatarGrid;
    private TextButton chooseButton;
    private TextButton backButton;

    public AvatarMenuView(AvatarMenuController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.menuTitle = new Label(Output.AvatarMenu.getString(), skin);
        menuTitle.setFontScale(2.5f);
        this.currentAvatar = new Image(App.getLoggedInUser().getAvatarTexture());
        this.avatarGrid = new Table();
        this.chooseButton = new TextButton(Output.OpenFiles.getString(), skin);
        this.backButton = new TextButton(Output.Back.getString(), skin);
        setListerners();
        this.controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        GameAssetManager.getInstance().addSymmetricalLeaves(stage, table);

        table.setFillParent(true);
        currentAvatar.setScaling(Scaling.fit);

        Table previewAvatar = new Table();
        Label label = new Label("Your avatar:", GameAssetManager.getInstance().getSkin());
        label.setFontScale(1.5f);
        label.setColor(Color.GREEN);
        previewAvatar.add(label).padBottom(15).row();
        previewAvatar.add(currentAvatar).size(300);
        Table options = new Table();
        label = new Label("Choose an avatar:", GameAssetManager.getInstance().getSkin());
        label.setFontScale(1.5f);
        options.add(label).padBottom(15).row();
        options.add(avatarGrid).width(400);
        Table contentTable = new Table();
        contentTable.add(options).padRight(100);
        contentTable.add(previewAvatar).padLeft(100);
        Table buttonsTable = new Table();
        buttonsTable.add(chooseButton).pad(10);
        buttonsTable.add(backButton).pad(10);

        table.add(menuTitle).colspan(2).center().pad(30).row();
        table.add(contentTable).colspan(2).expand().fill().row();
        table.add(buttonsTable).colspan(2).pad(30).row();

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

    private void setListerners() {
        // avatar grid
        int counter = 0;
        for (String avatarFile : GameAssetManager.getInstance().getAvatarFiles()) {
            Texture avatarTexture = new Texture(Gdx.files.internal(avatarFile));
            ImageButton avatarImage = new ImageButton(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
            avatarImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (App.isSfxEnabled())
                        GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                    App.getLoggedInUser().setAvatar(avatarFile);
                    currentAvatar.setDrawable(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
                }
            });
            avatarGrid.add(avatarImage).size(200, 200);
            counter++;
            if (counter % 3 == 0)
                avatarGrid.row();
        }
        // open files
        chooseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                String avatarFile = controller.getFilePath();
                if (avatarFile == null)
                    return;
                App.getLoggedInUser().setAvatar(avatarFile);
                Texture avatarTexture = new Texture(Gdx.files.internal(avatarFile));
                currentAvatar.setDrawable(new TextureRegionDrawable(new TextureRegion(avatarTexture)));
            }
        });
        // back button
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isSfxEnabled())
                    GameAssetManager.getInstance().getButtonClickSfx().play(1.0f);
                controller.back();
            }
        });
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getChooseButton() {
        return chooseButton;
    }
}
