package com.rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.rpg.models.players.*;
import com.rpg.models.enemies.*; // すべての敵をインポート
import com.rpg.engine.BattleManager;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // --- 1. パーティー作成（以前と同じ） ---
        System.out.println("=== ⚔️ パーティー結成 ⚔️ ===");
        List<Player> party = new ArrayList<>();
        
        // 主人公作成
        System.out.print("主人公の名前 > ");
        String heroName = scanner.nextLine();
        System.out.println("職業: 1.剣士  2.魔法使い  3.騎士  4.ヒーラー");
        System.out.print("> ");
        party.add(createCharacter(Integer.parseInt(scanner.nextLine()), heroName));

        // 仲間作成（ループ）
        while (party.size() < 3) {
            int idx = party.size() + 1;
            System.out.println("仲間" + idx + "の職業: 1.剣士  2.魔法使い  3.騎士  4.ヒーラー");
            System.out.print("> ");
            party.add(createCharacter(Integer.parseInt(scanner.nextLine()), "仲間" + (idx-1)));
        }

        // --- 2. ダンジョン（敵リスト）の作成 ---
        List<Enemy> dungeon = new ArrayList<>();
        dungeon.add(new Slime());        // 第1戦
        dungeon.add(new Goblin());       // 第2戦
        dungeon.add(new GreatGoblin());  // 第3戦
        dungeon.add(new Gatekeeper());   // 第4戦
        dungeon.add(new Dragon());       // BOSS

        // --- 3. ステージ進行 ---
        BattleManager bm = new BattleManager();
        int stageCount = 1;

        System.out.println("\n💀 魔王の城へようこそ... 全5ステージの試練が始まる！");

        for (Enemy enemy : dungeon) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("STAGE " + stageCount + ": " + enemy.getName() + " が現れた！");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // バトル開始
            bm.startBattle(party, enemy);

            // バトル終了後の判定
            if (!isPartyAlive(party)) {
                System.out.println("\n⚰️ 全滅しました... ここまでの記録: STAGE " + stageCount);
                break; // ゲームオーバー（ループを抜ける）
            } else {
                // 勝利した場合の処理（回復など）
                System.out.println("\n🎉 ステージクリア！");
                recoverParty(party); // 少し回復させる（下で定義）
                stageCount++;
                
                System.out.println("準備ができたらエンターキーを押して進んでください...");
                scanner.nextLine();
            }
        }

        // --- 4. 完全制覇の判定 ---
        if (isPartyAlive(party)) {
            System.out.println("\n🏆 おめでとうございます！ ダンジョンを完全制覇しました！！ 🏆");
        }
    }

    // --- 補助メソッド ---

    // キャラ作成
    public static Player createCharacter(int jobType, String name) {
        switch (jobType) {
            case 1: return new Swordsman(name);
            case 2: return new Mage(name);
            case 3: return new Knight(name);
            case 4: return new Healer(name);
            default: return new Swordsman(name);
        }
    }

    // 全滅チェック
    public static boolean isPartyAlive(List<Player> party) {
        for (Player p : party) {
            if (p.isAlive()) return true;
        }
        return false;
    }

// ステージクリア後の回復処理（現在値+回復量 表示版）
    public static void recoverParty(List<Player> party) {
        System.out.println("\n=========== 🏕️ 休息ポイント 🏕️ ===========");
        
        // ヘッダー（少し幅を広げます）
        System.out.printf("   %-10s | %-15s | %-15s%n", "名前", "HP(回復)", "MP(回復)");
        System.out.println("---------------------------------------------------");

        for (Player p : party) {
            if (p.isAlive()) {
                // --- HP回復処理 ---
                int oldHp = p.getHp();
                int healHpAmount = (int)(p.getMaxHp() * 0.2);
                p.setHp(oldHp + healHpAmount);
                int actualHp = p.getHp() - oldHp;

                // --- MP回復処理 ---
                int oldMp = p.getMp();
                int healMpAmount = 10;
                p.setMp(oldMp + healMpAmount);
                int actualMp = p.getMp() - oldMp;

                // --- 表示用文字列の作成 ---
                // String.formatを使って "HP 120(+24)" のような文字列を作ります
                String hpText = String.format("HP %d(+%d)", p.getHp(), actualHp);
                String mpText = String.format("MP %d(+%d)", p.getMp(), actualMp);

                // --- 整形して表示 ---
                // %-15s : 15文字分の幅を確保
                System.out.printf("👤 %-10s | %-15s | %-15s%n", 
                    p.getName(), hpText, mpText);
            }
        }
        System.out.println("===================================================");
    }
}