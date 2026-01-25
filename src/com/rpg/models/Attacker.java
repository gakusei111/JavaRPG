package com.rpg.models;

public interface Attacker {
    // 攻撃メソッド：誰か(target)を攻撃する
    void attack(BaseCharacter target);
}