package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.scores.ScoreDaoImpl;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    public static int screenWidth,screenHeight;
    public Handler mHandler = new Mhandler();
    public TextView mTextView;
    private static int score = 0;
    public static ScoreDaoImpl scoreDaoImpl;
    private int gameType = 0;
    private boolean musicOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getScreenHW();

        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            musicOn = getIntent().getBooleanExtra("musicOn",false);
        }


        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        BaseGame baseGameView = null;
        if(gameType == 0){
            baseGameView = new EasyGame(this,mHandler,musicOn);
            scoreDaoImpl = new ScoreDaoImpl(this,"easyGame.dat");
        }
        else if(gameType == 1){
            baseGameView = new MediumGame(this,mHandler,musicOn);
            scoreDaoImpl = new ScoreDaoImpl(this,"mediumGame.dat");
        }
        else{
            baseGameView = new HardGame(this,mHandler,musicOn);
            scoreDaoImpl = new ScoreDaoImpl(this,"hardGame.dat");
        }
        setContentView(baseGameView);
    }

    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getDisplay().getRealMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // 步骤1：（自定义）新创建Handler子类(继承Handler类) & 复写handleMessage（）方法
    class Mhandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(GameActivity.this, RecordActivity.class);
                    intent.putExtra("score",msg.getData().getInt("score"));
                    intent.putExtra("gameType",gameType);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

}