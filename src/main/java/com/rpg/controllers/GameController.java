package com.rpg.controllers;

import com.rpg.models.players.*;
import com.rpg.models.enemies.*;
import com.rpg.engine.BattleManager;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class GameController {

    @GetMapping("/battle")
    // URLの例: http://localhost:8080/api/battle?name=勇者
    public List<String> startBattle(@RequestParam(defaultValue = "ゲスト") String name) {
        // 1. パーティー作成（Scannerの代わりにURLの引数を使う）
        List<Player> party = new ArrayList<>();
        party.add(new Swordsman(name));
        party.add(new Mage("仲間A"));

        // 2. 敵の作成
        Enemy enemy = new Dragon();

        // 3. バトル実行
        BattleManager manager = new BattleManager();
        manager.startBattle(party, enemy);

        // 4. 戦闘ログ（List<String>）をそのままブラウザに返す！
        return manager.getBattleLogs();
    }
}