package com.rpg.controllers;

import com.rpg.engine.BattleManager;
import com.rpg.models.BattleLog;
import com.rpg.models.enemies.*;
import com.rpg.models.players.*;
import com.rpg.repository.BattleLogRepository;
import jakarta.servlet.http.HttpSession; // Spring Boot 3の場合
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class GameController {

    @Autowired
    private BattleLogRepository repository;

    // 1. 戦闘の初期化（セッションにデータを保存）
    @GetMapping("/battle/init")
    public List<String> initBattle(
            @RequestParam String name, @RequestParam String job1,
            @RequestParam String job2, @RequestParam String job3,
            HttpSession session) {
        
        List<Player> party = new ArrayList<>();
        party.add(createPlayer(name, job1));
        party.add(createPlayer("仲間A", job2));
        party.add(createPlayer("仲間B", job3));

        Enemy enemy = new Random().nextBoolean() ? new Slime() : new Goblin();

        // セッション（サーバーのメモリ）に現在の状態を保存
        session.setAttribute("party", party);
        session.setAttribute("enemy", enemy);
        session.setAttribute("turn", 1);

        List<String> initLogs = new ArrayList<>();
        initLogs.add("戦闘開始！ 3人で " + enemy.getName() + " に挑む！");
        return initLogs;
    }

    // 2. コマンドの実行（セッションからデータを読み込んでターンを進める）
    @GetMapping("/battle/command")
    public Map<String, Object> executeCommand(@RequestParam String action, HttpSession session) {
        // セッションからデータを取り出す
        List<Player> party = (List<Player>) session.getAttribute("party");
        Enemy enemy = (Enemy) session.getAttribute("enemy");
        Integer turn = (Integer) session.getAttribute("turn");

        BattleManager manager = new BattleManager();
        manager.executeTurn(party, enemy, action, turn);

        // ターン数を進めて保存
        session.setAttribute("turn", turn + 1);

        // 終了判定
        boolean isEnemyDead = !enemy.isAlive();
        boolean isPartyDead = party.stream().noneMatch(Player::isAlive);
        boolean isGameOver = isEnemyDead || isPartyDead;

        if (isGameOver) {
            String winner = isPartyDead ? enemy.getName() : "勇者一行";
            repository.save(new BattleLog(enemy.getName(), winner));
            session.invalidate(); // 戦闘終了時にセッションを破棄（メモリ解放）
        }

        // フロントエンドに返すデータをMapで構築
        Map<String, Object> response = new HashMap<>();
        response.put("logs", manager.getBattleLogs());
        response.put("isGameOver", isGameOver);
        return response;
    }

    private Player createPlayer(String name, String job) {
        switch (job) {
            case "Mage": return new Mage(name);
            case "Healer": return new Healer(name);
            case "Knight": return new Knight(name);
            case "Swordsman": default: return new Swordsman(name);
        }
    }
}