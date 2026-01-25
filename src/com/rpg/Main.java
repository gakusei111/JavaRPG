package com.rpg;

import com.rpg.models.players.Swordsman;
import com.rpg.models.enemies.Slime;
import com.rpg.engine.BattleManager; // 追加

public class Main {
    public static void main(String[] args) {
        // 1. キャラ生成
        Swordsman hero = new Swordsman("勇者");
        Slime slime = new Slime();

        // 2. バトルマネージャーの準備
        BattleManager bm = new BattleManager();

        // 3. バトル開始！
        bm.startBattle(hero, slime);
    }
}