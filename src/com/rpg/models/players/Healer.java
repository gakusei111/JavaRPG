package com.rpg.models.players;

public class Healer extends Player {
    public Healer(String name) {
        super(name, "ヒーラー", 90, 40, 3);
    }

    // 回復スキル
    public void heal(Player target) {
        if (getMp() >= 8) {
            setMp(getMp() - 8);
            int recovery = 20;
            target.setHp(target.getHp() + recovery);
            System.out.println(getName() + " は " + target.getName() + " の傷を癒やした！");
        } else {
            System.out.println("MPが足りない!!");
        }
    }
}