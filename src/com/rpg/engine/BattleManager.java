package com.rpg.engine;

import java.util.Scanner;
import com.rpg.models.players.Player;
import com.rpg.models.players.Swordsman; // スキル使用のためキャスト用
import com.rpg.models.enemies.Enemy;

public class BattleManager {
    // コンソール入力用
    private Scanner scanner = new Scanner(System.in);

    // バトル開始メソッド
    public void startBattle(Player player, Enemy enemy) {
        System.out.println("⚔️ バトル開始！ ⚔️");
        System.out.println("vs " + enemy.getName());

        // どちらかが倒れるまでループ (While Loop)
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n========================");
            System.out.println(player); // プレイヤー状態表示
            System.out.println(enemy.getName() + " (HP: " + enemy.getHp() + ")");
            System.out.println("========================");

            // --- 1. プレイヤーのターン ---
            System.out.println("どうする？ (1:攻撃, 2:スキル)");
            System.out.print("> ");
            int choice = scanner.nextInt();
            
            if (choice == 1) {
                player.attack(enemy);
            } else if (choice == 2) {
                // ※本来はスキルインターフェース等で整理しますが、今回は簡易的にキャストします
                if (player instanceof Swordsman) {
                    ((Swordsman) player).slash(enemy);
                } else {
                    System.out.println("スキルを覚えていない！通常攻撃になった！");
                    player.attack(enemy);
                }
            } else {
                System.out.println("ミス！行動できなかった...");
            }

            // 敵が死んだらループを抜ける
            if (!enemy.isAlive()) {
                System.out.println("\n🏆 " + enemy.getName() + " を倒した！");
                break;
            }

            // --- 2. 敵のターン ---
            System.out.println("---");
            enemy.attack(player);

            // プレイヤーが死んだらループを抜ける
            if (!player.isAlive()) {
                System.out.println("\n💀 " + player.getName() + " は力尽きた...");
                break;
            }
        }
    }
}