package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.MediumGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private PrintWriter writer;
    private Mhandler mHandler = new Mhandler();
    public static int screenWidth,screenHeight;
    private AlertDialog dialog;
    private static  final String TAG = "MainActivity";
    boolean music = false;
    public static int modeType = 0;
    public static boolean online = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button singlePlayMode = findViewById(R.id.Single_Player_Mode);
        Button onlineMode = findViewById(R.id.Online_Mode);
        RadioGroup musicSelect = findViewById(R.id.musicSelect);
        RadioButton musicOn = findViewById(R.id.musicOn);
        RadioButton musicOff = findViewById(R.id.musicOff);
        musicOff.setChecked(true);
        singlePlayMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicOn.isChecked()){
                    music = true;
                }
                modeType = 0;
                Intent intent = new Intent(MainActivity.this, OfflineGameActivity.class);
                intent.putExtra("music", music);
                startActivity(intent);
            }
        });
        onlineMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicOn.isChecked()){
                    music = true;
                }
                modeType = 1;
                online = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("匹配中请稍候.....");
                dialog = builder.create();
                dialog.show();
                new ClientThread(mHandler).start();
            }
        });


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

    class Mhandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            // 根据不同线程发送过来的消息，执行不同的UI操作
            // 根据 Message对象的what属性 标识不同的消息
            switch (msg.what) {
                case 0:
                    dialog.dismiss();
                    BaseGame baseGameView = new MediumGame(MainActivity.this, mHandler,music);
                    new Thread(()->{
                        while (!BaseGame.gameOverFlag) {
                            // 游戏逻辑
                            // 模拟发送分数
                            try {
                                Thread.sleep(500); // 每隔0.5秒发送一次分数
                                writer.println(Integer.toString(BaseGame.score));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        writer.println("玩家死亡");
                    }).start();
                    setContentView(baseGameView);
                    break;
                case 1:
                    online = false;
                    Intent intent = new Intent(MainActivity.this,OnlineResultActivity.class);
                    intent.putExtra("score",BaseGame.score);
                    intent.putExtra("opscore",BaseGame.opscore);
                    startActivity(intent);
                default:
                    break;
            }
        }
    }
    protected class ClientThread extends Thread{
        private BufferedReader in;
        private Mhandler toClientHandler;

        public ClientThread(Mhandler myHandler){
            this.toClientHandler = myHandler;
        }
        @Override
        public void run(){
            try{
                socket = new Socket();

                socket.connect(new InetSocketAddress
                        ("10.0.2.2",9999),5000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),"utf-8")),true);
                Log.i(TAG,"connect to server");

                //接收服务器返回的数据
                Thread receiveServerMsg =  new Thread(){
                    @Override
                    public void run(){
                        String fromserver = null;
                        try{
                            while((fromserver = in.readLine())!=null)
                            {
                                Log.i(TAG,fromserver);
                                //发送消息给UI线程
                                if(fromserver.equals("匹配成功")){
                                    Message msg= new Message();
                                    msg.what = 0;
                                    msg.obj = fromserver;
                                    toClientHandler.sendMessage(msg);
                                }
                                else if(fromserver.equals("游戏结束")){
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = fromserver;
                                    toClientHandler.sendMessage(msg);
                                }
                                else{
                                    int opscore = Integer.parseInt(fromserver);
                                    BaseGame.opscore = opscore;

                                }
                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                        }
                    }
                };
                receiveServerMsg.start();
            }catch(UnknownHostException ex){
                ex.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

}