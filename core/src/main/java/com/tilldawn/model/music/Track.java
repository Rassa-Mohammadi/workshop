package com.tilldawn.model.music;

public enum Track {
    LuxAeterna("Lux Aeterna", "SFX/AudioClip/Lux Aeterna.mp3"),
    GrassWalk("Grass Walk", "SFX/AudioClip/Grasswalk.mp3"),
    CrazyDave("Crazy Dave", "SFX/AudioClip/Crazy Dave.mp3"),
    Loonboon("Loonboon", "SFX/AudioClip/Loonboon.mp3");

    private final String name;
    private final String address;

    Track(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public static Track getByName(String name) {
        for (Track track : values()) {
            if (track.getName().equals(name)) {
                return track;
            }
        }
        return null;
    }
}
