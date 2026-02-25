package com.rpg.models.enemies;

import com.rpg.models.BaseCharacter;
import com.rpg.models.ActionResult;
import com.rpg.models.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dragon extends Enemy {
    public Dragon() {
        super("ドラゴン", 800, 30);
    }

    @Override
    public ActionResult attack(BaseCharacter target) {
        target.takeDamage(this.getAttackPower());
        return new ActionResult(
            this.getName() + " の強烈な一撃！",
            this.getAttackPower(),
            target.getName()
        );
    }

    /**
     * 全体攻撃（炎のブレス）の判定を追加した独自行動ロジック
     */
    @Override
    public List<ActionResult> performAction(List<Player> party) {
        List<ActionResult> results = new ArrayList<>();
        List<Player> aliveMembers = new ArrayList<>();
        
        for (Player p : party) {
            if (p.isAlive()) aliveMembers.add(p);
        }

        if (aliveMembers.isEmpty()) return results;

        // 確率判定（例として30%の確率でブレスを使用）
        if (new Random().nextInt(100) < 30) {
            // 生存者全員に30の固定ダメージ
            for (Player p : aliveMembers) {
                p.takeDamage(30);
                results.add(new ActionResult(
                    this.getName() + " の炎のブレス！",
                    30,
                    p.getName()
                ));
            }
        } else {
            // 通常攻撃
            Player target = aliveMembers.get(new Random().nextInt(aliveMembers.size()));
            results.add(this.attack(target));
        }
        return results;
    }
}