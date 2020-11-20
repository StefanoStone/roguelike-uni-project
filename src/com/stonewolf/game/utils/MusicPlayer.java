package com.stonewolf.game.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class MusicPlayer {

    private static MusicPlayer instance = new MusicPlayer();

    public static MusicPlayer getInstance() {
        return instance;
    }

    private MusicPlayer() {

    }

    public void playMusic(String path, int loopCount) {

        Clip clip = null;

        try {
            Sound sound = getSound(path);
            System.out.println("Loading: " + path + "...");
            clip = sound.play(loopCount);
        } catch (Exception ignored) {

        }
    }

    public Sound getSound(String path) {
        Sound sound = null;
        try {
            sound = new Sound(getResource(path));
        } catch (NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
        return sound;
    }

    public URL getResource(String resource) {
        return this.getClass().getResource(resource);
    }

    //DEPRECATED METHOD
    public void playMusicObsolete(String path) {
        try {
            File file = new File(path);
            System.out.println("Loading: " + file + "...");
            AudioInputStream music = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(music);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (LineUnavailableException e) {
            System.out.println("Error: Line Unavailable Exception" + e);
        } catch (IOException e) {
            System.out.println("Error: could not load music file");
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error: Unsupported audio file");
        }
    }

    class Sound {

        private final Line.Info INFO = new Line.Info(Clip.class);
        private HashMap<String, Sound> map;
        private URL soundUrl;
        public Clip readyClip;

        public Sound(String path) throws MalformedURLException {
            this(new URL(path));
        }

        public Sound(URL url) {
            this.soundUrl = url;
            this.readyClip = this.getNewClip();
        }

        public Clip play(int times) {
            Clip clip = null;
            clip = getNewClip();

            if (clip != null) {
                clip.loop(times);
            }
            return clip;
        }

        public final Clip getNewClip() {
            if (this.readyClip == null) {
                this.readyClip = this.getNewClip(this.soundUrl);
            }
            Clip c = this.readyClip;
            this.readyClip = this.getNewClip(this.soundUrl);
            return c;
        }

        public HashMap<String, Sound> getMap() {
            if (map == null) {
                map = new HashMap<>();
            }
            return map;
        }

        public Clip getNewClip(URL clipURL) {
            Clip clip = null;
            try {
                clip = (Clip) AudioSystem.getLine(INFO);
                clip.open(AudioSystem.getAudioInputStream(clipURL));
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            return clip;
        }


    }
}
