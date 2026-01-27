package com.rpg.models;

/**
 * 全ての登場キャラクター（味方・敵）が共通して持つ振る舞い
 **/

public interface Character {
    String getName();      // 名前の取得
    int getHp();           // 現在HPの取得
    void takeDamage(int damage); // ダメージを受ける処理
    boolean isAlive();     // 生存確認
}