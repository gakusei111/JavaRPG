package com.rpg.models.enemies;

import com.rpg.models.BaseCharacter;

public class Dragon extends Enemy {
    public Dragon() {
        // 名前, HP:800, 攻撃力:50 (最強)
        super("ドラゴン", 800, 50);
    }

    // ボスらしい演出
    @Override
    public void attack(BaseCharacter target) {
        System.out.println("🔥 " + this.getName() + " が火を吹いた！");
        target.takeDamage(this.getAttackPower());
    }
}