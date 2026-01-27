package com.rpg.models.players;

public class Knight extends Player {
    public Knight(String name) {
        // HPを高めに設定
        super(name, "騎士", 200, 10, 12);
    }
    
    // 騎士固有の守備的な振る舞いは、後のバトルシステム拡張で活かせます
}