/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 20:35
*/

package cz.cloudy.fxengine.core;

import javafx.scene.media.AudioClip;

import java.net.URL;

// TODO: AudioService
public class AudioService {
    private static double volume = 1.0;

    public static void playSound(URL url) {
        if (Cache.contains("audio", url)) {
            AudioClip audioClip = Cache.get("audio", url);
            audioClip.play(volume);
            return;
        }
        AudioClip audioClip = new AudioClip(url.toExternalForm());
        Cache.cache("audio", url, audioClip);
        audioClip.play(volume);
    }
}
