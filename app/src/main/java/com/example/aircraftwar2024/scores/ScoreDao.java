package com.example.aircraftwar2024.scores;

import java.util.List;

public interface ScoreDao {
    List<Score> getAllScore();
    Score getScore(int id);
    void addScore (Score score);
    boolean deleteScore(int id);
    void Print();



}
