package com.rpg.controllers;

import com.rpg.engine.BattleManager;
import com.rpg.models.ActionResult;
import com.rpg.models.BattleLog;          // 追加
import com.rpg.models.enemies.*;
import com.rpg.models.players.*;
import com.rpg.repository.BattleLogRepository; // 追加
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired // Spring Bootが勝手にRepositoryを用意してくれます
    private BattleLogRepository repository;

    @GetMapping("/battle")
    public List<String> startBattle(@RequestParam(defaultValue = "ゲスト") String name) {
        // ... (パーティー作成などのコードはそのまま) ...
        List<Player> party = new ArrayList<>();
        party.add(new Swordsman(name));
        Enemy enemy = new Dragon();

        BattleManager manager = new BattleManager();
        manager.startBattle(party, enemy);

        // ★★★ ここでDBに保存！ ★★★
        String winner = party.get(0).isAlive() ? name : enemy.getName();
        BattleLog log = new BattleLog(enemy.getName(), winner);
        repository.save(log); // INSERT INTO BattleLog ... が実行される！

        return manager.getBattleLogs();
    }
    
    // ★ 保存された履歴を見るための新しいURL
    @GetMapping("/history")
    public List<BattleLog> getHistory() {
        return repository.findAll(); // SELECT * FROM BattleLog が実行される！
    }
}