package com.example.musicandmovietest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends AppCompatActivity {private static final String TAG = "VideoActivity";
    public static final int PROGRESSINDEX = 1;
    public static final int ENDINDEX = 2;
    private Timer timer;
    private SeekBar progress;
    private VideoView screen;
    private int length=0;
    private TextView showVideoLength;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case PROGRESSINDEX:
                    progress.setProgress(msg.arg1);
                    showVideoLength.setText((String)msg.obj);
                    Log.i(TAG, "handleMessage: "+msg.arg1);
                    break;
                case ENDINDEX:
                    progress.setProgress(msg.arg1);
                    showVideoLength.setText((String)msg.obj);
                    Toast.makeText(VideoActivity.this,
                            "影片放完了，记得分享哟", Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        screen = findViewById(R.id.video_screen);
        //screen.setBackgroundColor(R.color.colorPrimary);
        screen.setBackgroundColor(Color.rgb(255, 250, 250));
        //可以试试不加下面这一句看看效果（其主要用于设置图层顺序的，其目的是避免继承
        // SurfaceView的VideoView出现透明或者黑屏的现象）
        screen.setZOrderOnTop(true);
        progress = findViewById(R.id.video_progress);
        showVideoLength=findViewById(R.id.video_length);

        progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Message message=new Message();
                if(length-progress>=10000){
                    message.what = PROGRESSINDEX;
                    message.arg1 = progress;
                    message.obj=TimeDealer.getTime(length-progress);
                    handler.sendMessage(message);
                }
                else{
                    message.what = ENDINDEX;
                    message.arg1 = progress;
                    message.obj=TimeDealer.getTime(length-progress);
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        screen.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                screen.start();
            }
        });
        initVideoPlayer();
    }

    public void startPlay(View view) {
        //screen.setBackgroundColor(R.color.transparent);
        //设置视频时长
        final int videoLength = screen.getDuration();
        length=videoLength;
        Log.i(TAG, "startPlay: "+videoLength);
        progress.setMax(videoLength);
        showVideoLength.setText(TimeDealer.getTime(videoLength));
        progress.setProgress(0);
        if (!screen.isPlaying()) {
            screen.start();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int currentLength = screen.getCurrentPosition();
                if ((videoLength - currentLength) >= 10000) {
                    Message message = new Message();
                    message.what = PROGRESSINDEX;
                    message.arg1 = currentLength;
                    message.obj=TimeDealer.getTime(videoLength-currentLength);
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = ENDINDEX;
                    message.arg1 = currentLength;
                    message.obj=TimeDealer.getTime(videoLength-currentLength);
                    handler.sendMessage(message);
                }
            }
        }, 0, 1000);
    }

    public void pausePlay(View view) {

        if (screen.isPlaying()) {
            screen.pause();
        }
    }

    public void rePlay(View view) {
        //设置视频时长
        progress.setProgress(0);
        if (screen.isPlaying()) {
            screen.resume();
        } else {
            screen.start();
            screen.resume();
        }
    }

    private void initVideoPlayer() {
        File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        screen.setVideoPath(file.getPath());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (screen != null) {
            screen.suspend();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

}