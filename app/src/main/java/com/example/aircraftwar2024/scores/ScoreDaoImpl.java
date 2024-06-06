package com.example.aircraftwar2024.scores;
import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreDaoImpl implements  ScoreDao{
    private String dataPath;
    private static List<Score> ScoreList = new ArrayList<Score>();
    public ScoreDaoImpl(Context context,String filename){
        dataPath = context.getFilesDir() + "/" + filename;
        readObject();
    }



    public void readObject(){
        File f =  new File(dataPath);
        try{
            FileInputStream in = new FileInputStream(f);
            ObjectInputStream objIn=new ObjectInputStream(in);
            ScoreList =(List<Score>)objIn.readObject();
            objIn.close();
            in.close();
        } catch (FileNotFoundException e){
            System.out.println("file not found: " + e.getMessage());
        } catch(IOException | ClassNotFoundException e){
            System.out.println("read ScoreRecord error: " + e.getMessage());
            e.printStackTrace();
        }

    }
    public void writeObject(){
        File f =  new File(dataPath);
        try{
            FileOutputStream out = new FileOutputStream(f);
            ObjectOutputStream objOut  = new ObjectOutputStream(out);
            objOut.writeObject(ScoreList);
            objOut.flush();
            objOut.close();
        } catch (IOException e){
            System.out.println("write ScoreRecord error: " + e.getMessage());
            e.printStackTrace();
        }


    }
    @Override
    public List<Score> getAllScore() {
        return ScoreList;
    }

    @Override
    public Score getScore(int id) {
        for(Score scoreRecord : ScoreList){
            if(scoreRecord.getId() == id){
                return scoreRecord;
            }
        }
        System.out.println("Score record not found!");
        return null;
    }

    @Override
    public void addScore(Score score) {
        int len = ScoreList.size();
        score.setId(len);
        ScoreList.add(score);
        sort();
    }
    public void sort(){
        Collections.sort(ScoreList);
    }
    @Override
    public boolean deleteScore(int id) {
        for(Score scoreRecord : ScoreList){
            if(scoreRecord.getId() == id){
                ScoreList.remove(scoreRecord);
                return true;
            }
        }
        Collections.sort(ScoreList);
        System.out.println("Score record not found!");
        return false;
    }
    @Override
    public void Print() {
        System.out.println("**************************************************************");
        System.out.println("AircraftWar Rank List");
        System.out.println("**************************************************************");
        int rank = 1;
        for (Score scoreRecord : ScoreList) {
            System.out.println("rank: " + rank + " " + "score: " + scoreRecord.getScore() + " " + "usrName: " + scoreRecord.getUsername() + " " + "date: " + scoreRecord.getDate());
            rank++;
        }
        System.out.println("**************************************************************");

    }

}
