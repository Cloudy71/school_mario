/*
  User: Cloudy
  Date: 27-Dec-18
  Time: 16:21
*/

package cz.cloudy.fxengine.core;

import javafx.scene.media.AudioClip;

import java.net.URL;

public class Sound {
    private   URL       source;
    protected AudioClip audioClip;

    public Sound(URL source) {
        this.source = source;
        load();
    }

    private void load() {
        if (Cache.contains("sound", source)) {
            this.audioClip = Cache.get("sound", source);
            return;
        }
        this.audioClip = new AudioClip(source.toExternalForm());
        Cache.cache("sound", source, this.audioClip);
    }

    public void play() {
        AudioService.playSound(this);
    }

    public void playLoop() {
        AudioService.playSoundLoop(this);
    }

    public void stop() {
        AudioService.stopSound(this);
    }
}
