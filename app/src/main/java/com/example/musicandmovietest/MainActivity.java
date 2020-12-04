package com.example.musicandmovietest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playMusic(View view){
        intent=new Intent(this,RadioActivity.class);
        startActivity(intent);
    }


    public void playVideo(View view){
        intent=new Intent(this,VideoActivity.class);
        startActivity(intent);
    }

}