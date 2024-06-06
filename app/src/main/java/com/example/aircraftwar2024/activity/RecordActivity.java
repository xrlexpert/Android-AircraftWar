package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.scores.Score;
import com.example.aircraftwar2024.scores.ScoreAdapter;
import com.example.aircraftwar2024.scores.ScoreDaoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**游戏排行榜 */
        setContentView(R.layout.activity_record);
        int score = getIntent().getIntExtra("score",0);
        int gameType = getIntent().getIntExtra("gameType",0);
        TextView title = findViewById(R.id.game_difficulty);
        if(gameType == 0){
            title.setText("简单模式排行榜");
        }
        else if(gameType == 1){
            title.setText("中等模式排行榜");
        }
        else{
            title.setText("困难模式排行榜");
        }



        Date date = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        Score now = new Score(score,"test",ft.format(date));
        GameActivity.scoreDaoImpl.addScore(now);


        //获得Layout里面的ListView
        ListView list = (ListView) findViewById(R.id.rank_board);

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.listitem,
                new String[]{"rank","userName","score","date"},
                new int[]{R.id.rank,R.id.userName,R.id.score,R.id.date});

        //添加并且显示
        list.setAdapter(listItemAdapter);
        //添加单击监听
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Map<String, Object> clkmap = (Map<String, Object>) arg0.getItemAtPosition(arg2);
                AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                builder.setMessage("你确定要删除第" + Integer.toString(arg2) + "行数据吗");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int pos = arg2 - 1;
                        List<Score> scoreList = GameActivity.scoreDaoImpl.getAllScore();
                        if(scoreList.size() > pos){
                            System.out.println(scoreList.get(pos).getId());
                            GameActivity.scoreDaoImpl.deleteScore(scoreList.get(pos).getId());
                            SimpleAdapter listItemAdapter = new SimpleAdapter(
                                    RecordActivity.this,
                                    getData(),
                                    R.layout.listitem,
                                    new String[]{"rank","userName","score","date"},
                                    new int[]{R.id.rank,R.id.userName,R.id.score,R.id.date});
                            list.setAdapter(listItemAdapter);
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        /**返回按钮*/
        Button gameEndToHome = findViewById(R.id.GameEndToHome);
        gameEndToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.scoreDaoImpl.writeObject();
                Intent intent = new Intent(RecordActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
    private List<Map<String, Object>> getData() {
        ArrayList<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        List<Score> scoreList = GameActivity.scoreDaoImpl.getAllScore();
        Collections.sort(scoreList);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rank","排名");
        map.put("userName","用户名");
        map.put("score","游戏得分");
        map.put("date","日期");
        listitem.add(map);
        int i = 1;
        for(Score score:scoreList){
            map = new HashMap<String, Object>();
            map.put("rank",Integer.toString(i));
            map.put("userName",score.getUsername());
            map.put("score",score.getScore());
            map.put("date",score.getDate());
            listitem.add(map);
            i++;
        }
        return listitem;
    }




}
