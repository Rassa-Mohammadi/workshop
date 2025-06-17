package com.tilldawn.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.menus.ScoreboardController;
import com.tilldawn.model.App;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.client.User;
import com.tilldawn.model.enums.Output;

import java.util.ArrayList;

public class ScoreboardView implements Screen {
    private ScoreboardController controller;
    private Stage stage;
    private Table table;
    private Table scoreboardTable;
    private Label menuTitle;
    private SelectBox<String> sortTypeSelectBox;
    private TextButton backButton;

    {
        initSortTypeSelectBox();
    }

    public ScoreboardView(ScoreboardController controller, Skin skin) {
        this.controller = controller;
        this.table = new Table();
        this.scoreboardTable = new Table();
        this.menuTitle = new Label(Output.Scoreboard.getString(), skin);
        menuTitle.setFontScale(2.5f);
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

        table.add(menuTitle).pad(30).row();
        Label label = new Label(Output.SortedBy.getString() + ": ", GameAssetManager.getInstance().getSkin());
        table.add(label).pad(10).row();
        table.add(sortTypeSelectBox).pad(20).fillX().row();
        setScoreboardTable(sortTypeSelectBox.getSelected());
        table.add(scoreboardTable).pad(10).row();
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

    private void setScoreboardTable(String sortType) {
        ArrayList<User> users = App.getSortedUsers(sortType);
        scoreboardTable.clearChildren();
        Table usernameTable = new Table();
        Table scoreTable = new Table();
        Table killTable = new Table();
        Table maxSurvivedTimeTable = new Table();
        // init headers
        Label header = new Label(Output.Username.getString(), GameAssetManager.getInstance().getSkin());
        header.setColor(Color.CYAN);
        usernameTable.add(header).pad(20).row();
        header = new Label(Output.Score.getString(), GameAssetManager.getInstance().getSkin());
        header.setColor(Color.GREEN);
        scoreTable.add(header).pad(20).row();
        header = new Label(Output.Kills.getString(), GameAssetManager.getInstance().getSkin());
        killTable.add(header).pad(20).row();
        header = new Label(Output.MaxSurvivedTime.getString(), GameAssetManager.getInstance().getSkin());
        header.setColor(Color.YELLOW);
        maxSurvivedTimeTable.add(header).pad(20).row();
        // adding users data
        for (int i = 0; i < Math.min(10, users.size()); i++) {
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = GameAssetManager.getInstance().getSkin().getFont("whitefont");
            labelStyle.fontColor = i == 0? Color.GOLD : i == 1? Color.GRAY : i == 2? Color.BROWN : Color.RED;
            Label usernameLabel = new Label(users.get(i).getUsername(), labelStyle);
            if (App.getLoggedInUser().getUsername().equals(users.get(i).getUsername()))
                usernameLabel.setText("--> " + users.get(i).getUsername());
            Label scoreLabel = new Label(users.get(i).getPoints() + "", labelStyle);
            Label killLabel = new Label(users.get(i).getTotalKills() + "", labelStyle);
            Label maxSurvivedLabel = new Label(
                (int) users.get(i).getMaxSurvivedTime() + "",
                labelStyle
            );
            usernameTable.add(usernameLabel);
            usernameTable.pad(10).row();
            scoreTable.add(scoreLabel);
            scoreTable.pad(10).row();
            killTable.add(killLabel);
            killTable.pad(10).row();
            maxSurvivedTimeTable.add(maxSurvivedLabel);
            maxSurvivedTimeTable.pad(10).row();
        }
        scoreboardTable.add(usernameTable).pad(10);
        scoreboardTable.add(scoreTable).pad(10);
        scoreboardTable.add(killTable).pad(10);
        scoreboardTable.add(maxSurvivedTimeTable).pad(10);
    }

    private void initSortTypeSelectBox() {
        sortTypeSelectBox = new SelectBox<>(GameAssetManager.getInstance().getSkin());
        Array<String> array = new Array<>();
        array.add(Output.Score.getString());
        array.add(Output.Username.getString());
        array.add(Output.Kills.getString());
        array.add(Output.MaxSurvivedTime.getString());
        sortTypeSelectBox.setItems(array);
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

        sortTypeSelectBox.addListener(event -> {
            if (event instanceof ChangeListener.ChangeEvent) {
                setScoreboardTable(sortTypeSelectBox.getSelected());
            }
            return false;
        });
    }
}
