/*
  User: Cloudy
  Date: 26-Dec-18
  Time: 20:35
*/

package cz.cloudy.fxengine.core;

import java.util.LinkedList;
import java.util.List;

// TODO: AudioService
public class AudioService {
    private static   double               volume  = 1.0;
    protected static List<SoundQueueItem> playing = new LinkedList<>();

    public static void playSound(Sound sound) {
        sound.audioClip.play(volume);
        playing.add(new SoundQueueItem(sound, false, volume));
    }

    public static void playSoundLoop(Sound sound) {
        sound.audioClip.play(volume);
        playing.add(new SoundQueueItem(sound, true, volume));
    }

    public static void stopAll() {
        for (SoundQueueItem soundQueueItem : playing) {
            soundQueueItem.sound.audioClip.stop();
        }
        playing.clear();
    }

    public static void stopSound(Sound sound) {
        for (SoundQueueItem soundQueueItem : playing) {
            if (soundQueueItem.sound.equals(sound)) {
                soundQueueItem.sound.audioClip.stop();
                playing.remove(soundQueueItem);
                return;
            }
        }
    }

    public static void setVolume(double volume) {
        AudioService.volume = volume;
    }

    public static double getVolume() {
        return AudioService.volume;
    }

    protected static class SoundQueueItem {
        protected Sound   sound;
        protected boolean loop;
        protected double  volume;

        protected SoundQueueItem(Sound sound, boolean loop, double volume) {
            this.sound = sound;
            this.loop = loop;
            this.volume = volume;
        }
    }
}
