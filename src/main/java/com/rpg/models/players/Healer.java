package com.rpg.models.players;

public class Healer extends Player {
    public Healer(String name) {
        super(name, "ヒーラー", 90, 40, 3);
    }

    public void heal(Player target) {
        if (getMp() >= 8) {
            setMp(getMp() - 8);
            
            int healPower = 20; // 基本回復量
            
            // 回復前のHPを記録
            int oldHp = target.getHp();
            
            // 回復実行（Setterが最大値を超えないように調整してくれる）
            target.setHp(oldHp + healPower);
            
            // 実際に回復した量を計算
            int actualHeal = target.getHp() - oldHp;

            System.out.println(getName() + " のヒール！");
            
            if (actualHeal > 0) {
                System.out.println("✨ " + target.getName() + " のHPが " + actualHeal + " 回復した！");
            } else {
                System.out.println("👌 " + target.getName() + " は既に満タンです！");
            }

        } else {
            System.out.println("MPが足りない！");
        }
    }
}