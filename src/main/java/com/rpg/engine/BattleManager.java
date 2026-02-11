package com.rpg.engine;

import java.util.ArrayList;
import java.util.List;
import com.rpg.models.players.Player;
import com.rpg.models.enemies.Enemy;
import com.rpg.models.ActionResult;

public class BattleManager {
    // 1. ログを保存するリスト
    private List<String> battleLogs = new ArrayList<>();

    // ★重要★ これが「出口」になるメソッドです
    public List<String> getBattleLogs() {
        return this.battleLogs;
    }

public void startBattle(List<Player> party, Enemy enemy) {
    battleLogs.clear();
    //勇者の取得
    Player hero = party.get(0);

    addLog("⚔️ " + enemy.getName() + " を発見！！");
    addLog(hero.getName() + " が戦闘に突入した！");
    
    // 戦闘ループ
    int turn = 1;

    while (hero.isAlive() && enemy.isAlive()) {
        addLog("【ターン" + turn + "】 -------------------");

        //勇者の攻撃
        ActionResult heroAction = hero.attack(enemy);
        addLog(heroAction.getFullLog());

        if (!enemy.isAlive()) {
            addLog(enemy.getName() + " を倒した！");
            addLog("勝者: " + hero.getName());
            break;
        }

        //敵の攻撃
        ActionResult enemyAction = enemy.attack(hero);
        addLog(enemyAction.getFullLog());

        if (!hero.isAlive()) {
            addLog(hero.getName() + " は力尽きた...");
            addLog("勝者: " + enemy.getName());
            break;
        }

        addLog("");
        turn++;
    }
}

    private void addLog(String log) {
        this.battleLogs.add(log);
        System.out.println(log);
    }
}