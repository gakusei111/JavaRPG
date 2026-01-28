package com.rpg.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity // これをつけるとDBのテーブルに対応するクラスになる
public class BattleLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // ID（自動連番）

    private String enemyName; // 戦った敵の名前
    private String winner;    // 勝者
    private LocalDateTime createdAt; // 戦った日時

    // 空のコンストラクタ（JPAに必須）
    public BattleLog() {}

    // データをセットするためのコンストラクタ
    public BattleLog(String enemyName, String winner) {
        this.enemyName = enemyName;
        this.winner = winner;
        this.createdAt = LocalDateTime.now();
    }

    // Getter (データを取得するために必要)
    public String getEnemyName() { return enemyName; }
    public String getWinner() { return winner; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}