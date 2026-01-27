package com.rpg.models.players;

import com.rpg.models.BaseCharacter;

public class Mage extends Player {
    public Mage(String name) {
        // 名前, Job名, HP, MP, 攻撃力
        super(name, "魔法使い", 80, 50, 5);
    }

    // 魔法攻撃
    public void fireBall(BaseCharacter target) {
        if (getMp() >= 10) {
            setMp(getMp() - 10);
            System.out.println(getName() + " の火炎魔法！");
            target.takeDamage(30); // 固定の大ダメージ
        } else {
            System.out.println("MPが足りない!!");
        }
    }
}