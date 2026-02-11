package com.rpg.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.rpg.models.players.Player;
import com.rpg.models.enemies.Enemy;
import com.rpg.models.ActionResult;

public class BattleManager {
    // 1. ログを保存するリスト
    private List<String> battleLogs = new ArrayList<>();
    private Random random = new Random(); //敵の攻撃のためのRandom

    // ★重要★ これが「出口」になるメソッドです
    public List<String> getBattleLogs() {
        return this.battleLogs;
    }

public void startBattle(List<Player> party, Enemy enemy) {
    battleLogs.clear();
    //勇者の取得
    Player hero = party.get(0);

    addLog("⚔️ " + enemy.getName() + " を発見！！");
    addLog(hero.getName() + "パーティーが戦闘に突入した！");

    // 戦闘ループ
    int turn = 1;

    while (isPartyAlive(party) && enemy.isAlive()) {
        addLog("【ターン" + turn + "】 -------------------");

        // --- 1. 味方全員のターン ---
            for (Player member : party) {
                // 生きているメンバーだけが行動できる
                if (member.isAlive()) {
                    ActionResult result = member.attack(enemy);
                    addLog(result.getFullLog());

                    // もし攻撃で敵が倒れたら、すぐに戦闘終了
                    if (!enemy.isAlive()) {
                        break; 
                    }
                }
            }

        // 敵が生きていれば、反撃してくる
        if (enemy.isAlive()) {
                // 生きているメンバーの中からランダムに一人狙われる！
                Player target = getRandomAliveMember(party);
                
                if (target != null) {
                    ActionResult enemyResult = enemy.attack(target);
                    addLog(enemyResult.getFullLog());
                    
                    if (!target.isAlive()) {
                        addLog("💀 " + target.getName() + " (" + target.getJobName() + ") は倒れた...");
                    }
                }
            }

            //決着の判定
    if (!enemy.isAlive()) {
        addLog("🏆 " + enemy.getName() + " を倒した！");
    } else if (!isPartyAlive(party)) {
        addLog("☠️" + enemy.getName() + "によって勇者一行は全滅した...");
    }

        addLog("");
        turn++;
    }
}

// パーティー全滅判定
    private boolean isPartyAlive(List<Player> party) {
        for (Player p : party) {
            if (p.isAlive()) return true; // 誰か一人でも生きていればOK
        }
        return false;
    }

    // 生きているメンバーからランダムに一人選ぶ
    private Player getRandomAliveMember(List<Player> party) {
        List<Player> aliveMembers = new ArrayList<>();
        for (Player p : party) {
            if (p.isAlive()) aliveMembers.add(p);
        }
        
        if (aliveMembers.isEmpty()) return null;
        
        // 生存者リストからランダムに1人取得
        return aliveMembers.get(random.nextInt(aliveMembers.size()));
    }

    private void addLog(String log) {
        this.battleLogs.add(log);
        System.out.println(log);
    }
}