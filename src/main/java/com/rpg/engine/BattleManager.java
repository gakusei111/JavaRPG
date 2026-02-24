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
// 変更: String command → List<String> commands に変更
    public void executeTurn(List<Player> party, Enemy enemy, List<String> commands, int turn) {
        battleLogs.clear();
        addLog("【ターン " + turn + "】");

        // 1. パーティー全員の行動（リストで受け取ったコマンドを順に実行）
        for (int i = 0; i < party.size(); i++) {
            Player member = party.get(i);
            
            // 死んでいる場合は行動スキップ
            if (member.isAlive()) {
                // 安全策：万が一コマンド配列が足りない場合は通常攻撃にする
                String cmd = (commands != null && commands.size() > i) ? commands.get(i) : "attack";
                ActionResult result;
                
                if ("skill".equals(cmd)) {
                    result = member.useSkill(enemy);
                } else {
                    result = member.attack(enemy);
                }
                addLog(result.getFullLog());

                // 敵が倒れたかチェック（誰かの攻撃で倒れたら即終了）
                if (!enemy.isAlive()) {
                    addLog(enemy.getName() + " を倒した！");
                    addLog("勝者: 勇者一行");
                    return; // ターン終了
                }
            }
        }

        // 2. 敵の行動
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

        // 3. 全滅チェック
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