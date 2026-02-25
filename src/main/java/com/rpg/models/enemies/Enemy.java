package com.rpg.models.enemies;

import com.rpg.models.BaseCharacter;
import com.rpg.models.Attacker;
import com.rpg.models.ActionResult;
import com.rpg.models.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    /**
     * 敵のターン行動を決定・実行する。
     * デフォルト実装：生存しているランダムな対象への単体攻撃。
     */
    public List<ActionResult> performAction(List<Player> party) {
        List<ActionResult> results = new ArrayList<>();
        List<Player> aliveMembers = new ArrayList<>();
        
        for (Player p : party) {
            if (p.isAlive()) aliveMembers.add(p);
        }

        if (!aliveMembers.isEmpty()) {
            Player target = aliveMembers.get(new Random().nextInt(aliveMembers.size()));
            results.add(this.attack(target));
        }
        return results;
    }
}