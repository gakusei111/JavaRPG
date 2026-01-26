package com.rpg.engine;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random; // ランダム攻撃用

import com.rpg.models.BaseCharacter;
import com.rpg.models.players.*;
import com.rpg.models.enemies.Enemy;

public class BattleManager {
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    // 引数を List<Player> に変更
    public void startBattle(List<Player> party, Enemy enemy) {
        System.out.println("⚔️ バトル開始！ ⚔️");
        System.out.println("vs " + enemy.getName());

        // --- バトルループ ---
        // 「パーティーの誰かが生きている」かつ「敵が生きている」間つづく
        while (isPartyAlive(party) && enemy.isAlive()) {
            
            // === 1. 味方全員のターン ===
            System.out.println("\n--- プレイヤーのターン ---");
            
            // for-each文で一人ずつ行動させる
            for (Player member : party) {
                // 死んでいるキャラは行動できない
                if (!member.isAlive()) continue;
                // 敵が既に死んでいたらループ終了
                if (!enemy.isAlive()) break;

                System.out.println("\n[" + member.getJobName() + "] " + member.getName() + " の行動");
                System.out.println("HP: " + member.getHp() + " / MP: " + member.getMp());
                System.out.println("1:攻撃  2:スキル");
                System.out.print("> ");
                
                int choice = scanner.nextInt();

                if (choice == 1) {
                    member.attack(enemy);
                } else if (choice == 2) {
                    // ジョブごとのスキル分岐
                    useSkill(member, party, enemy);
                }
            }

            // === 2. 敵のターン ===
            if (enemy.isAlive()) {
                System.out.println("\n--- 敵のターン ---");
                // 生きているメンバーからランダムにターゲットを決める
                Player target = getRandomLivingMember(party);
                if (target != null) {
                    enemy.attack(target);
                }
            }
        }
        
        // --- 戦闘終了判定 ---
        if (!enemy.isAlive()) {
            System.out.println("\n🏆 勝利！敵を倒した！");
        } else {
            System.out.println("\n💀 全滅... ゲームオーバー");
        }
    }

    // --- 補助メソッド: スキル使用ロジック ---
    private void useSkill(Player member, List<Player> party, Enemy enemy) {
        if (member instanceof Swordsman) {
            ((Swordsman) member).slash(enemy);
        } else if (member instanceof Mage) {
            ((Mage) member).fireBall(enemy);
        } else if (member instanceof Healer) {
            // ヒーラーは味方を回復させる（今回は簡易的に、HPが減っている人を自動選択などのロジックも可）
            // ここではシンプルに「自分」を回復させてみます（拡張の余地あり）
            ((Healer) member).heal(member); 
            System.out.println("(※簡易実装: 自分を回復しました)");
        } else {
            System.out.println("スキルがない！通常攻撃！");
            member.attack(enemy);
        }
    }

    // --- 補助メソッド: 全滅チェック ---
    private boolean isPartyAlive(List<Player> party) {
        for (Player p : party) {
            if (p.isAlive()) return true; // 誰か一人でも生きていればOK
        }
        return false;
    }

    // --- 補助メソッド: 生存者からランダムにターゲットを選ぶ ---
    private Player getRandomLivingMember(List<Player> party) {
        // 生きているメンバーだけのリストを一時的に作る
        List<Player> livingMembers = new ArrayList<>();
        for (Player p : party) {
            if (p.isAlive()) livingMembers.add(p);
        }
        
        if (livingMembers.isEmpty()) return null;
        
        // ランダムに1人選ぶ
        int index = random.nextInt(livingMembers.size());
        return livingMembers.get(index);
    }
}