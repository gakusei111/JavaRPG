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
    
    // 他のスキル（slashなど）は今のところそのままでも大丈夫です
}