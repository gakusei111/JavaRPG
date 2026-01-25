package com.rpg.models.enemies;

import com.rpg.models.Attacker; // 追加
import com.rpg.models.BaseCharacter;

// implements Attacker を追加
public abstract class Enemy extends BaseCharacter implements Attacker {

    public Enemy(String name, int hp, int attackPower) {
        super(name, hp, attackPower);
    }

    // 敵の共通攻撃ロジック
    @Override
    public void attack(BaseCharacter target) {
        System.out.println("⚠️ " + this.getName() + " の攻撃！");
        target.takeDamage(this.getAttackPower());
    }
}