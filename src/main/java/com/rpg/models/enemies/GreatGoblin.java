package com.rpg.models.enemies;

import com.rpg.models.BaseCharacter;
import com.rpg.models.ActionResult; // インポートが必要

public class GreatGoblin extends Enemy {
    public GreatGoblin() {
        super("グレートゴブリン", 200, 15);
    }

    @Override
    public ActionResult attack(BaseCharacter target) {
        // ダメージ処理
        target.takeDamage(this.getAttackPower());
        
        // 戻り値を ActionResult に変更
        return new ActionResult(
            this.getName() + " の棍棒による強打！",
            this.getAttackPower(),
            target.getName()
        );
    }
}