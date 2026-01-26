package com.rpg.models.enemies;

import com.rpg.models.BaseCharacter;

public class GreatGoblin extends Enemy {
    public GreatGoblin() {
        // 名前, HP:150, 攻撃力:20
        super("大ゴブリン", 150, 20);
    }

    // 攻撃の演出を少し変える（オーバーライド）
    @Override
    public void attack(BaseCharacter target) {
        System.out.println("⚠️ " + this.getName() + " がこん棒を振り回した！");
        target.takeDamage(this.getAttackPower());
    }
}