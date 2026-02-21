package com.rpg.models.players;

import com.rpg.models.BaseCharacter;
import com.rpg.models.ActionResult;

public class Mage extends Player {
    public Mage(String name) {
        super(name, "魔法使い", 80, 50, 5);
    }

    // ★追加: 魔法使いの通常攻撃
    @Override
    public ActionResult attack(BaseCharacter target) {
        target.takeDamage(this.getAttackPower());
        return new ActionResult(
            this.getName() + " の杖による打撃！",
            this.getAttackPower(),
            target.getName()
        );
    }

    // ★追加: 魔法使いのスキル実装（古い fireBall メソッドは削除）
    @Override
    public ActionResult useSkill(BaseCharacter target) {
        if (this.getMp() >= 15) {
            this.setMp(this.getMp() - 15);
            int damage = this.getAttackPower() * 3;
            target.takeDamage(damage);
            return new ActionResult(
                this.getName() + " のファイアボール！",
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