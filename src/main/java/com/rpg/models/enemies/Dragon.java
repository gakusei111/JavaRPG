package com.rpg.models.enemies;

import com.rpg.models.BaseCharacter;
import com.rpg.models.ActionResult; // インポートを忘れずに！

public class Dragon extends Enemy {
    public Dragon() {
        super("ドラゴン", 800, 30);
    }

    @Override
    public ActionResult attack(BaseCharacter target) {
        target.takeDamage(this.getAttackPower());
        // void ではなく、ActionResult を返却するように変更
        return new ActionResult(
            this.getName() + " の強烈な一撃！",
            this.getAttackPower(),
            target.getName()
        );
    }
}