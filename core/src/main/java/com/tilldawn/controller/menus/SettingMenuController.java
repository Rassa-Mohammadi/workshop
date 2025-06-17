package com.tilldawn.controller.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.tilldawn.Main;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.model.enums.KeyBind;
import com.tilldawn.view.menus.MainMenuView;
import com.tilldawn.view.menus.SettingMenuView;

public class SettingMenuController {
    private SettingMenuView view;
    private KeyBind keyBind = null; // key bind to change
    private TextButton button = null; // button to change

    public void setView(SettingMenuView view) {
        this.view = view;
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public int getPressedKey() {
        for (int i = 0; i < 256; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                return i;
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
            return Input.Buttons.LEFT + 1000;
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))
            return Input.Buttons.RIGHT + 1000;
        if (Gdx.input.isButtonJustPressed(Input.Buttons.MIDDLE))
            return Input.Buttons.MIDDLE + 1000;
        return -1;
    }

    public void setKey(TextButton button, KeyBind key) {
        button.setText(" ");
        int keyCode = getPressedKey();
        view.setWaiting(true);
        this.keyBind = key;
        this.button = button;
    }

    public void bindKey(int keycode) {
        button.setText(KeyBind.getKeyName(keycode));
        keyBind.setKeyCode(keycode);
        view.setWaiting(false);
    }
}
