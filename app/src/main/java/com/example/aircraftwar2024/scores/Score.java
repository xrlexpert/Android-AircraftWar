package com.example.aircraftwar2024.scores;

import java.io.Serializable;

public class Score implements Serializable,Comparable<Score> {
    private int score;
    private int id;
    private String username;
    private String date;

    @Override
    public int compareTo(Score o) {
        return -(score - o.score);

    }
    public Score(){}
    public Score(int score, String username, String date){
        this.score = score;
        this.username = username;
        this.date = date;
        this.id = -1;

    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }
    public int getId(){return id;}

    public void setScore(int score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setId(int id){
        this.id = id;
    }
}

