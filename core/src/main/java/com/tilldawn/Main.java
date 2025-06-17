package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.tilldawn.controller.menus.AppMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.menus.AppMenuView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    private Cursor cursor;
    private boolean blackAndWhite = false;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        setCursor();
        main.setScreen(new AppMenuView(new AppMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public boolean isBlackAndWhite() {
        return blackAndWhite;
    }

    public void removeBlackAndWhiteShader() {
        blackAndWhite = false;
        batch.setShader(null);
    }

    public void setBlackAndWhiteShader() {
        blackAndWhite = true;
        String vertexShader = "attribute vec4 a_position;\n" +
            "attribute vec4 a_color;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "uniform mat4 u_projTrans;\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "void main() {\n" +
            "    v_color = a_color;\n" +
            "    v_texCoords = a_texCoord0;\n" +
            "    gl_Position = u_projTrans * a_position;\n" +
            "}";

        String fragmentShader = "#ifdef GL_ES\n" +
            "    precision mediump float;\n" +
            "#endif\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "uniform sampler2D u_texture;\n" +
            "void main() {\n" +
            "    vec4 c = texture2D(u_texture, v_texCoords) * v_color;\n" +
            "    float gray = (c.r + c.g + c.b) / 3.0;\n" +
            "    gl_FragColor = vec4(gray, gray, gray, c.a);\n" +
            "}";

        ShaderProgram grayscaleShader = new ShaderProgram(vertexShader, fragmentShader);
        batch.setShader(grayscaleShader);
    }

    public void setCursor() {
        Texture texture = GameAssetManager.getInstance().getCursorTexture();
        texture.getTextureData().prepare();
        Pixmap cursorPixmap = GameAssetManager.getInstance().getCursorTexture().getTextureData().consumePixmap();
        cursor = Gdx.graphics.newCursor(cursorPixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
        cursorPixmap.dispose();
    }

    public void removeCursor() {
        Texture texture = GameAssetManager.getInstance().getCursorTexture();
        Pixmap pixmap = new Pixmap(texture.getWidth(), texture.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }
}
