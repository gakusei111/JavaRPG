package com.rpg.models;

public interface Attacker {
    ActionResult attack(BaseCharacter target); // 戻り値がActionResultであることを確認
}