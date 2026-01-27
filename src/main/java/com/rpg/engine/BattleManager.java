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
    addLog("⚔️ " + enemy.getName() + " との戦闘開始！");

    // テスト用に1回だけ攻撃させてみる
    if (!party.isEmpty()) {
        Player hero = party.get(0);
        addLog(hero.getName() + " の会心の一撃！");
        addLog(enemy.getName() + " を倒した！");
    }
}

    private void addLog(String log) {
        this.battleLogs.add(log);
        System.out.println(log);
    }
}