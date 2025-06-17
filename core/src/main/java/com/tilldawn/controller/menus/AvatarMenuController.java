package com.tilldawn.controller.menus;

import com.tilldawn.Main;
import com.tilldawn.controller.menus.profile.ProfileMenuController;
import com.tilldawn.model.GameAssetManager;
import com.tilldawn.view.menus.AvatarMenuView;
import com.tilldawn.view.menus.profile.ProfileMenuView;

import javax.swing.*;
import java.io.File;

public class AvatarMenuController {
    private AvatarMenuView view;

    public void setView(AvatarMenuView view) {
        this.view = view;
    }

    public String getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image files", "jpg", "png", "jpeg"
        ));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }

    public void back() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getInstance().getSkin()));
    }

}
