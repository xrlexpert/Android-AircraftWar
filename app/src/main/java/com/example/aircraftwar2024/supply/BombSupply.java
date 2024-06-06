package com.example.aircraftwar2024.supply;


import com.example.aircraftwar2024.observer.Observer;

import java.util.LinkedList;
import java.util.List;

/**
 * 炸弹道具，自动触发
 * <p>
 * 使用效果：清除界面上除BOSS机外的所有敌机（包括子弹）
 * <p>
 * 【观察者模式】
 *
 * @author hitsz
 */
public class BombSupply extends AbstractFlyingSupply {

    private final List<Observer> observers = new LinkedList<>();


    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    public void registerObserver(Observer observer) {
        observers.add(observer);

    }

    @Override
    public void activate() {
        System.out.println("BombSupply active");
        notifyObservers();
    }
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }

    }

}
