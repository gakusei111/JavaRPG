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
import java.util.Random;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired // Spring Bootが勝手にRepositoryを用意する
    private BattleLogRepository repository;

    @GetMapping("/battle")
    public List<String> startBattle(
@RequestParam(defaultValue = "勇者") String name,
            @RequestParam(defaultValue = "Swordsman") String job1, // 主人公のジョブ
            @RequestParam(defaultValue = "Mage") String job2,      // 仲間A
            @RequestParam(defaultValue = "Healer") String job3     // 仲間B
    ) {
        List<Player> party = new ArrayList<>();

        // ジョブに応じたプレイヤーを作成するヘルパーメソッド
        party.add(createPlayer(name, job1));
        party.add(createPlayer("仲間A", job2));
        party.add(createPlayer("仲間B", job3));

        Enemy enemy;
        Random random = new Random();
        if (random.nextBoolean()) { // 50%の確率
            enemy = new Slime();   // 弱い
        } else {
            enemy = new Goblin();  // ちょっと強い
        }


        BattleManager manager = new BattleManager();
        manager.startBattle(party, enemy);

// 結果保存（勝利判定：パーティの誰か一人でも生きていれば勝ち）
        boolean isWin = party.stream().anyMatch(p -> p.isAlive());
        String winner = isWin ? "勇者一行" : enemy.getName();
        
        BattleLog log = new BattleLog(enemy.getName(), winner);
        repository.save(log);

        return manager.getBattleLogs();
    }

    // 文字列からクラスを作る便利メソッド（ファクトリーメソッド）
    private Player createPlayer(String name, String job) {
        switch (job) {
            case "Mage": return new Mage(name);
            case "Healer": return new Healer(name);
            case "Knight": return new Knight(name);
            case "Swordsman": 
            default: return new Swordsman(name);
        }
    }

    
    // ★ 保存された履歴を見るための新しいURL
    @GetMapping("/history")
    public List<BattleLog> getHistory() {
        return repository.findAll(); // SELECT * FROM BattleLog が実行される！
    }
}