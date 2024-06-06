package com.example.aircraftwar2024.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.aircraftwar2024.R;

import java.util.HashMap;

public class MyMediaPlayer {
    private MediaPlayer backgroundMediaPlayer;
    private MediaPlayer bossMediaPlayer;
    private SoundPool soundPool;
    private final HashMap<Integer,Integer> soundPoolMap;
    private AudioAttributes audioAttributes;
    private boolean musicOn;
    public MyMediaPlayer(Context context,boolean musicOn){
        if(backgroundMediaPlayer == null){
            backgroundMediaPlayer = MediaPlayer.create(context, R.raw.bgm);
            backgroundMediaPlayer.setLooping(true);
            backgroundMediaPlayer.setVolume(3,3);
        }
        if(bossMediaPlayer == null){
            bossMediaPlayer = MediaPlayer.create(context, R.raw.bgm_boss);
            bossMediaPlayer.setLooping(true);
            bossMediaPlayer.setVolume(3,3);
        }
        if(audioAttributes == null){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
        }
        if(soundPool == null){
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        this.musicOn = musicOn;
        soundPoolMap = new HashMap<>();
        soundPoolMap.put(1,soundPool.load(context, R.raw.bomb_explosion,1));
        soundPoolMap.put(2,soundPool.load(context, R.raw.bullet_hit,1));
        soundPoolMap.put(3,soundPool.load(context, R.raw.game_over,1));
        soundPoolMap.put(4,soundPool.load(context, R.raw.get_supply,1));
    }
    public void playBackgroundMusic(){
        if(musicOn){
            synchronized(bossMediaPlayer) {
                if ( bossMediaPlayer != null &&  bossMediaPlayer.isPlaying()) {
                    bossMediaPlayer.pause();
                }
            }
            backgroundMediaPlayer.start();

        }

    }

    public void playBossMusic() {
        if(musicOn){
            synchronized( backgroundMediaPlayer) {
                if (  backgroundMediaPlayer != null &&   backgroundMediaPlayer.isPlaying()) {
                    backgroundMediaPlayer.pause();
                }
            }
            bossMediaPlayer.start();
        }
    }
    public void pauseBossMusic(){
        bossMediaPlayer.pause();
    }
    public void pauseBGM(){
        backgroundMediaPlayer.pause();
    }
    public void playBomb_ExplosionMusic(){
        if(musicOn){
            soundPool.play(soundPoolMap.get(1),3,3,1,0,1.2f);
        }
    }
    public void playBullet_HitMusic(){
        if(musicOn){
            soundPool.play(soundPoolMap.get(2),3,3,1,0,1.2f);
        }
    }
    public void playGameOverMusic(){
        if(musicOn){
            if(backgroundMediaPlayer.isPlaying()){
                backgroundMediaPlayer.stop();
            }
            if(bossMediaPlayer.isPlaying()){
                bossMediaPlayer.stop();
            }
            soundPool.play(soundPoolMap.get(3),3,3,1,0,1.2f);
        }

    }
    public void playSupplyMusic(){
        if(musicOn){
            soundPool.play(soundPoolMap.get(4),3,3,1,0,1.2f);
        }
    }

}
