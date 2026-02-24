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
    /**
 * 戦闘初期化APIのレスポンス拡張
 * ログだけでなく、フロントエンドでの描画に必要なメタデータをMap形式で返却する。
 */
@GetMapping("/battle/init")
public Map<String, Object> initBattle(
        @RequestParam String name, @RequestParam String job1,
        @RequestParam String job2, @RequestParam String job3,
        HttpSession session) {
    
    List<Player> party = new ArrayList<>();
    party.add(createPlayer(name, job1));
    party.add(createPlayer("仲間A", job2));
    party.add(createPlayer("仲間B", job3));

    // ランダムで敵を抽選
    Enemy enemy = new Random().nextBoolean() ? new Slime() : new Goblin();

    session.setAttribute("party", party);
    session.setAttribute("enemy", enemy);
    session.setAttribute("turn", 1);

    // フロントエンドに渡すデータの構築
    Map<String, Object> response = new HashMap<>();
    List<String> logs = new ArrayList<>();
    logs.add("戦闘開始！ " + enemy.getName() + " が現れた！");

    response.put("logs", logs);
    // 画像切り替え用のキー（すべて小文字で統一）
// クラス名を取得して小文字にする（Slime → slime）
    response.put("enemyKey", enemy.getClass().getSimpleName().toLowerCase());    response.put("heroKey", job1.toLowerCase());
    response.put("memberAKey", job2.toLowerCase());
    response.put("memberBKey", job3.toLowerCase());

    // 敵の最新ステータス情報を抽出してMapに詰める
        Map<String, Object> enemyStatus = new HashMap<>();
        enemyStatus.put("name", enemy.getName());
        enemyStatus.put("hp", enemy.getHp());
        enemyStatus.put("maxHp", enemy.getMaxHp());
        enemyStatus.put("attackPower", enemy.getAttackPower());
        // レスポンスに追加
        response.put("enemyStatus", enemyStatus);

    // フロントエンドに最新のステータス情報を渡す
        response.put("partyStatus", getPartyStatus(party));
    return response;
}

    // 2. コマンドの実行（セッションからデータを読み込んでターンを進める）
    // 変更前: @RequestParam String action
    // 変更後: @RequestParam List<String> actions
    @GetMapping("/battle/command")
    public Map<String, Object> executeCommand(@RequestParam List<String> actions, HttpSession session) {
        // セッションからデータを取り出す
        @SuppressWarnings("unchecked")
        List<Player> party = (List<Player>) session.getAttribute("party");
        Enemy enemy = (Enemy) session.getAttribute("enemy");
        Integer turn = (Integer) session.getAttribute("turn");

        BattleManager manager = new BattleManager();
        // 変更: actionsリストをそのままBattleManagerに渡す
        manager.executeTurn(party, enemy, actions, turn);

        // ... 以下、既存のターン数保存や終了判定などのコードはそのまま

        // ターン数を進めて保存
        session.setAttribute("turn", turn + 1);

        // 終了判定
        boolean isEnemyDead = !enemy.isAlive();
        boolean isPartyDead = party.stream().noneMatch(Player::isAlive);
        boolean isGameOver = isEnemyDead || isPartyDead;

if (isGameOver) {
            String winner = isPartyDead ? enemy.getName() : "勇者一行";
            repository.save(new BattleLog(enemy.getName(), winner));
            /* * 同一パーティーでの連戦機能を実装したため、セッションの破棄処理を削除。
             * 完全な初期化はフロントエンドからのページリロードに委ねる。
             */
            // session.invalidate(); 
        }

        // フロントエンドに返すデータをMapで構築
        Map<String, Object> response = new HashMap<>();
        response.put("logs", manager.getBattleLogs());
        response.put("isGameOver", isGameOver);

        // 敵の最新ステータス情報を抽出してMapに詰める
        Map<String, Object> enemyStatus = new HashMap<>();
        enemyStatus.put("name", enemy.getName());
        enemyStatus.put("hp", enemy.getHp());
        enemyStatus.put("maxHp", enemy.getMaxHp());
        enemyStatus.put("attackPower", enemy.getAttackPower());
        // レスポンスに追加
        response.put("enemyStatus", enemyStatus);

        // フロントエンドに最新のステータス情報を渡す
        response.put("partyStatus", getPartyStatus(party));
        return response;
    }


    

    /**
     * 同一パーティーでの戦闘再開エンドポイント
     * セッション内のPlayer状態（HP/MP）を初期値まで回復させ、新たなEnemyを生成してセッションを更新する。
     */
    @GetMapping("/battle/restart")
    public Map<String, Object> restartBattle(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Player> party = (List<Player>) session.getAttribute("party");
        
        // メンバーのステータスを最大値にリセット
        for (Player p : party) {
            p.setHp(p.getMaxHp());
            p.setMp(p.getMaxMp());
        }

        // 新規エネミーの生成とセッション状態の上書き
        Enemy enemy = new Random().nextBoolean() ? new Slime() : new Goblin();
        session.setAttribute("enemy", enemy);
        session.setAttribute("turn", 1);

        List<String> logs = new ArrayList<>();
        logs.add("=== 次のバトルが始まった ===");
        logs.add(enemy.getName() + " が現れた！");

        Map<String, Object> response = new HashMap<>();
        response.put("logs", logs);
        response.put("isGameOver", false);

// 敵の最新ステータス情報を抽出してMapに詰める
        Map<String, Object> enemyStatus = new HashMap<>();
        enemyStatus.put("name", enemy.getName());
        enemyStatus.put("hp", enemy.getHp());
        enemyStatus.put("maxHp", enemy.getMaxHp());
        enemyStatus.put("attackPower", enemy.getAttackPower());
        // レスポンスに追加
        response.put("enemyStatus", enemyStatus);

        response.put("enemyKey", enemy.getClass().getSimpleName().toLowerCase());

        // フロントエンドに最新のステータス情報を渡す
        response.put("partyStatus", getPartyStatus(party));
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

    /**
     * パーティーの現在のステータスを抽出するヘルパーメソッド
     */
    private List<Map<String, Object>> getPartyStatus(List<Player> party) {
        List<Map<String, Object>> statusList = new ArrayList<>();
        for (Player p : party) {
            Map<String, Object> status = new HashMap<>();
            status.put("name", p.getName());
            status.put("hp", p.getHp());
            status.put("maxHp", p.getMaxHp());
            status.put("mp", p.getMp());
            status.put("maxMp", p.getMaxMp());
            status.put("attackPower", p.getAttackPower());
            statusList.add(status);
        }
        return statusList;
    }

}