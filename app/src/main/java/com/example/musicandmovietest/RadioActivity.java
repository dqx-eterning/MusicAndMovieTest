package com.example.musicandmovietest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

//播放音乐的activity
public class RadioActivity extends AppCompatActivity {

    private MediaPlayer musicPlayer=new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initMusicPlayer();
        }
    }

    //初始化音乐播放器
    private void initMusicPlayer() {
        File file= new File(Environment.getExternalStorageDirectory(),"music.mp3");
        try {
            musicPlayer.setDataSource(file.getPath());
            musicPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startPlay(View view){
        if(!musicPlayer.isPlaying()) {
            musicPlayer.start();
        }
    }

    public void pausePlay(View view) {
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause();
        }
    }

    //注意停止播放实现
    public void stopPlay(View view) {
        if (musicPlayer.isPlaying()) {
            musicPlayer.reset();
            initMusicPlayer();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMusicPlayer();
                } else {
                    Toast.makeText(this, "你已经拒绝了该权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if(musicPlayer!=null){
            musicPlayer.stop();
            musicPlayer.release();
        }
    }

}