package com.tilldawn.model.enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public enum KeyBind {
    Up(Input.Keys.W), // key code of W
    Down(Input.Keys.S), // key code of S
    Left(Input.Keys.A), // key code of A
    Right(Input.Keys.D), // key code of D
    Reload(Input.Keys.R), // key code of R
    Shoot(Input.Buttons.LEFT + 1000); // mouse keys are saved +1000

    private int keyCode;

    KeyBind(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public String getKeyName() {
        return getKeyName(keyCode);
    }

    public boolean isPressed() {
        if (keyCode >= 1000)
            return Gdx.input.isButtonPressed(keyCode - 1000);
        return Gdx.input.isKeyPressed(keyCode);
    }

    public boolean isJustPressed() {
        if (keyCode >= 1000)
            return Gdx.input.isButtonJustPressed(keyCode - 1000);
        return Gdx.input.isKeyJustPressed(keyCode);
    }

    public static String getKeyName(int keyCode) {
        if (keyCode < 1000)
            return Input.Keys.toString(keyCode);
        if (keyCode - 1000 == Input.Buttons.LEFT)
            return "Left";
        if (keyCode - 1000 == Input.Buttons.RIGHT)
            return "Right";
        if (keyCode - 1000 == Input.Buttons.MIDDLE)
            return "Middle";
        return null;
    }
}
