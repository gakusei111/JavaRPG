package com.rpg.models.enemies;

import com.rpg.models.BaseCharacter;
import com.rpg.models.Attacker;
import com.rpg.models.ActionResult;

public abstract class Enemy extends BaseCharacter implements Attacker {
    public Enemy(String name, int hp, int attackPower) {
        super(name, hp, attackPower);
    }

    @Override
    public ActionResult attack(BaseCharacter target) {
        target.takeDamage(this.getAttackPower());
        return new ActionResult(
            this.getName() + " の攻撃！",
            this.getAttackPower(),
            target.getName()
        );
    }
}