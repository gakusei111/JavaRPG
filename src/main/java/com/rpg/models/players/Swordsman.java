package com.rpg.models.players;

import com.rpg.models.BaseCharacter;
import com.rpg.models.ActionResult;

public class Swordsman extends Player {
    public Swordsman(String name) {
        super(name, "剣士", 150, 20, 12);
    }

    @Override
    public ActionResult attack(BaseCharacter target) {
        target.takeDamage(this.getAttackPower());
        return new ActionResult(
            this.getName() + " の斬りつけ！",
            this.getAttackPower(),
            target.getName()
        );
    }
    
    // ★追加: 剣士のスキル実装
    @Override
    public ActionResult useSkill(BaseCharacter target) {
        if (this.getMp() >= 10) {
            this.setMp(this.getMp() - 10);
            int damage = this.getAttackPower() * 2;
            target.takeDamage(damage);
            return new ActionResult(
                this.getName() + " の強斬り！",
                damage,
                target.getName()
            );
        } else {
            return new ActionResult(
                this.getName() + " はMPが足りない！",
                0,
                target.getName()
            );
        }
    }
}