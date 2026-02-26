package com.rpg.models.enemies;

import com.rpg.models.ActionResult;
import com.rpg.models.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gatekeeper extends Enemy {
    public Gatekeeper() {
        // 名前, HP:300, 攻撃力:15 (守備重視)
        super("門番", 300, 15);
    }

    @Override
    public List<ActionResult> performAction(List<Player> party) {
        List<ActionResult> results = new ArrayList<>();
        List<Player> aliveMembers = new ArrayList<>();
        
        // 生存しているプレイヤーを抽出
        for (Player p : party) {
            if (p.isAlive()) aliveMembers.add(p);
        }

        // 全滅している場合は何もしない
        if (aliveMembers.isEmpty()) return results;

        // ランダムにターゲットを1人選択
        Player target = aliveMembers.get(new Random().nextInt(aliveMembers.size()));
        
        // 50%の確率で攻撃を分岐
        if (new Random().nextBoolean()) {
            // 突き：アタックパワーと同等のダメージ
            int damage = this.getAttackPower();
            target.takeDamage(damage);
            results.add(new ActionResult(
                this.getName() + " の突き！",
                damage,
                target.getName()
            ));
        } else {
            // 薙ぎ払い：アタックパワーの2倍のダメージ
            int damage = this.getAttackPower() * 2;
            target.takeDamage(damage);
            results.add(new ActionResult(
                this.getName() + " の薙ぎ払い！",
                damage,
                target.getName()
            ));
        }
        
        return results;
    }
}