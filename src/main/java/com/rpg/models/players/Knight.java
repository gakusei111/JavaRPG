package com.rpg.models.players;

import com.rpg.models.ActionResult;
import com.rpg.models.BaseCharacter;

public class Knight extends Player {
    public Knight(String name) {
        // HPを高めに設定
        super(name, "騎士", 200, 10, 12);
    }

    // Knight.java と Healer.java の中に追加
    @Override
    public ActionResult useSkill(BaseCharacter target) {
        // 仮実装
        return this.attack(target);
    }
    
    // 騎士固有の守備的な振る舞いは、後のバトルシステム拡張で活かせます
}