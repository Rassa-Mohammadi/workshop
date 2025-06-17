package com.tilldawn.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Result {
    private Label message;
    private float displayTime;
    private boolean isError = true;

    public Result() {
        message = new Label("", GameAssetManager.getInstance().getSkin());
        message.setVisible(false);
        message.setFontScale(1f);
    }

    public Result(String message, Color color) {
        this.message = new Label(message, GameAssetManager.getInstance().getSkin());
        this.message.setVisible(true);
        this.message.setFontScale(1f);
        this.message.setColor(color);
        this.displayTime = 3f;
        this.isError = true;
    }

    public Result(String message, Color color, boolean isError) {
        this.message = new Label(message, GameAssetManager.getInstance().getSkin());
        this.message.setVisible(true);
        this.message.setFontScale(1f);
        this.message.setColor(color);
        this.displayTime = 3f;
        this.isError = isError;
    }

    public void set(Result result) {
        this.message.setText(result.getMessage().getText());
        this.message.setVisible(true);
        this.displayTime = result.displayTime;
        this.message.setColor(result.getColor());
        this.isError = result.isError;
    }

    public void update(float deltaTime) {
        if (displayTime > 0f) {
            displayTime -= deltaTime;
            if (displayTime < 1f)
                message.setColor(message.getColor().r, message.getColor().g, message.getColor().b, displayTime);
            if (displayTime <= 0f)
                this.message.setVisible(false);
        }
    }

    public Label getMessage() {
        return message;
    }

    public Color getColor() {
        return message.getColor();
    }

    public boolean hasError() {
        return isError;
    }
}
