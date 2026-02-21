package com.rpg.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.rpg.models.players.Player;
import com.rpg.models.enemies.Enemy;
import com.rpg.models.ActionResult;

public class BattleManager {
    private List<String> battleLogs = new ArrayList<>();
    private Random random = new Random();

    public List<String> getBattleLogs() {
        return this.battleLogs;
    }

    // 1ターン分の処理だけを行うメソッド
    public void executeTurn(List<Player> party, Enemy enemy, String command, int turn) {
        battleLogs.clear();
        addLog("【ターン " + turn + "】");

// 1. 勇者（主人公）の行動（プレイヤーの入力に基づく）
        Player hero = party.get(0);
        if (hero.isAlive()) {
            if ("skill".equals(command)) {
                // クラスが何であれ、自身のuseSkillを実行させる（ポリモーフィズム）
                ActionResult result = hero.useSkill(enemy);
                addLog(result.getFullLog());
            } else {
                // 通常攻撃
                ActionResult result = hero.attack(enemy);
                addLog(result.getFullLog());
            }
        }

        // 敵が倒れたかチェック
        if (!enemy.isAlive()) {
            addLog(enemy.getName() + " を倒した！");
            addLog("勝者: 勇者一行");
            return; // ターン終了
        }

        // 2. 仲間の行動（オート）
        for (int i = 1; i < party.size(); i++) {
            Player member = party.get(i);
            if (member.isAlive()) {
                ActionResult result = member.attack(enemy);
                addLog(result.getFullLog());
                if (!enemy.isAlive()) {
                    addLog(enemy.getName() + " を倒した！");
                    addLog("勝者: 勇者一行");
                    return; // ターン終了
                }
            }
        }

        // 3. 敵の行動
        if (enemy.isAlive()) {
            Player target = getRandomAliveMember(party);
            if (target != null) {
                ActionResult enemyResult = enemy.attack(target);
                addLog(enemyResult.getFullLog());
                
                if (!target.isAlive()) {
                    addLog(target.getName() + " (" + target.getJobName() + ") は倒れた...");
                }
            }
        }

        // 全滅チェック
        if (!isPartyAlive(party)) {
            addLog("全滅しました...");
            addLog("勝者: " + enemy.getName());
        }
    }

    private boolean isPartyAlive(List<Player> party) {
        return party.stream().anyMatch(Player::isAlive);
    }

    private Player getRandomAliveMember(List<Player> party) {
        List<Player> aliveMembers = new ArrayList<>();
        for (Player p : party) {
            if (p.isAlive()) aliveMembers.add(p);
        }
        if (aliveMembers.isEmpty()) return null;
        return aliveMembers.get(random.nextInt(aliveMembers.size()));
    }

    private void addLog(String log) {
        this.battleLogs.add(log);
        System.out.println(log);
    }
}