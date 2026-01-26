package com.rpg;

import java.util.ArrayList; // 追加
import java.util.List;      // 追加
import com.rpg.models.players.*;
import com.rpg.models.enemies.Slime;
import com.rpg.engine.BattleManager;

public class Main {
    public static void main(String[] args) {
        // 1. パーティー作成（ArrayList）
        List<Player> party = new ArrayList<>();
        
        // 好きな組み合わせで3人追加
        party.add(new Swordsman("勇者アルス"));
        party.add(new Mage("魔女リサ"));
        party.add(new Healer("僧侶クリフト"));

        // 2. 敵の出現
        Slime slime = new Slime();

        // 3. バトル開始（リストを渡す）
        BattleManager bm = new BattleManager();
        bm.startBattle(party, slime);
    }
}