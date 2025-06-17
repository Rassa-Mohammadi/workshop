package com.tilldawn.model.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
    private Music music;
    private float volume = 0.5f;
    private Track currentTrack;

    public void loadMusic() {
        if (music != null)
            music.dispose();
        music = Gdx.audio.newMusic(Gdx.files.internal(currentTrack.getAddress()));
        music.setLooping(true);
        music.setVolume(volume);
    }

    public void playMusic() {
        if (music != null && !music.isPlaying())
            music.play();
    }

    public Music getMusic() {
        return music;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (music != null)
            music.setVolume(volume);
    }

    public Track getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(Track currentTrack) {
        this.currentTrack = currentTrack;
        loadMusic();
        playMusic();
    }
}
