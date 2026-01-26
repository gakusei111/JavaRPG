package com.rpg.engine;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.rpg.models.players.*;
import com.rpg.models.enemies.Enemy;

public class BattleManager {
    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

    public void startBattle(List<Player> party, Enemy enemy) {
        System.out.println("⚔️ バトル開始！ ⚔️");
        System.out.println("vs " + enemy.getName());

        while (isPartyAlive(party) && enemy.isAlive()) {
            
            // === 1. 味方全員のターン ===
            System.out.println("\n--- プレイヤーのターン ---");
            
            for (Player member : party) {
                if (!member.isAlive()) continue;
                if (!enemy.isAlive()) break;

                boolean actionDone = false;
                while (!actionDone) {
                    // 行動選択時はシンプル表示
                    printCurrentState(member, enemy);

                    System.out.println("\n👉 どうする？");
                    System.out.println("1:攻撃  2:スキル  3:全員のステータスを見る");
                    System.out.print("> ");
                    
                    try {
                        String input = scanner.nextLine();
                        int choice = Integer.parseInt(input);

                        if (choice == 1) {
                            member.attack(enemy);
                            actionDone = true;
                        } else if (choice == 2) {
                            useSkill(member, party, enemy);
                            actionDone = true;
                        } else if (choice == 3) {
                            // 任意で全員表示
                            printBattleStatus(party, enemy);
                            System.out.println("\n(エンターキーを押して戻る)");
                            scanner.nextLine();
                        } else {
                            System.out.println("⚠️ 1〜3を入力してください。");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ 数字を入力してください。");
                    }
                }
                
                try { Thread.sleep(500); } catch (InterruptedException e) {}
            }

            // === 2. 敵のターン ===
            if (enemy.isAlive()) {
                System.out.println("\n--- 敵のターン ---");
                Player target = getRandomLivingMember(party);
                if (target != null) {
                    enemy.attack(target);
                }

                // ★追加: 敵の行動が終わったら（＝1ターン終了時）、全員の情報を表示する
                System.out.println("\n--- ⏳ ターン終了 ⏳ ---");
                printBattleStatus(party, enemy);
                
                // 読みやすいように少しウェイトを入れるか、エンターキー待ちを入れても良いです
                // System.out.println("(エンターキーで次のターンへ)");
                // scanner.nextLine(); 
            }
        }
        
        // --- 戦闘終了後 ---
        if (!enemy.isAlive()) {
            System.out.println("\n🏆 勝利！ " + enemy.getName() + " を倒した！");
        } else {
            System.out.println("\n💀 全滅... ゲームオーバー");
        }
    }

    // --- 表示用メソッド ---

    // 行動中のキャラと敵だけの情報をシンプル表示
    private void printCurrentState(Player activePlayer, Enemy enemy) {
        System.out.println("\n------------------------");
        System.out.printf("👿 %-12s HP: %3d%n", enemy.getName(), enemy.getHp());
        System.out.printf("🗡️ %-12s HP: %3d  MP: %2d%n", 
            activePlayer.getName(), activePlayer.getHp(), activePlayer.getMp());
        System.out.println("------------------------");
    }

    // 全員のステータス表示
    private void printBattleStatus(List<Player> party, Enemy enemy) {
        System.out.println("\n=========== 🛡️ 全員の状況 🛡️ ===========");
        System.out.printf("👿 %-12s HP: %3d / %3d%n", 
            enemy.getName(), enemy.getHp(), enemy.getMaxHp());
        System.out.println("----------------------------------------");
        for (Player p : party) {
            String status = p.isAlive() ? "" : "💀 死亡";
            // 死亡している場合はHP0表示など工夫してもOK
            System.out.printf("👤 %-10s [%-6s] HP: %3d / %3d  MP: %2d / %2d  %s%n", 
                p.getName(), p.getJobName(), 
                p.getHp(), p.getMaxHp(), 
                p.getMp(), p.getMaxMp(), status);
        }
        System.out.println("========================================");
    }

    // --- 以下、補助メソッド（変更なし） ---
    private void useSkill(Player member, List<Player> party, Enemy enemy) {
        if (member instanceof Swordsman) {
            ((Swordsman) member).slash(enemy);
        } else if (member instanceof Mage) {
            ((Mage) member).fireBall(enemy);
        } else if (member instanceof Healer) {
            // 対象選択ロジックを次回実装する場合はここを変えます
            ((Healer) member).heal(member);
            System.out.println("(自分を回復しました)");
        } else {
            System.out.println("スキルがない！通常攻撃！");
            member.attack(enemy);
        }
    }

    private boolean isPartyAlive(List<Player> party) {
        for (Player p : party) {
            if (p.isAlive()) return true;
        }
        return false;
    }

    private Player getRandomLivingMember(List<Player> party) {
        List<Player> livingMembers = new ArrayList<>();
        for (Player p : party) {
            if (p.isAlive()) livingMembers.add(p);
        }
        if (livingMembers.isEmpty()) return null;
        int index = random.nextInt(livingMembers.size());
        return livingMembers.get(index);
    }
}