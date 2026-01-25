package com.rpg.models.players;

import com.rpg.models.Attacker;
import com.rpg.models.BaseCharacter;

public class Swordsman extends Player implements Attacker {

    // コンストラクタ
    public Swordsman(String name) {
        // 親クラス(Player)のコンストラクタを呼ぶ
        // 設定値: Job名="剣士", HP=120, MP=20, 攻撃力=15
        super(name, "剣士", 120, 20, 15);
    }

    @Override
    public void attack(BaseCharacter target) {
        // 自分の攻撃力を取得
        int damage = this.getAttackPower();
        
        // 攻撃の演出（コンソール出力）
        System.out.println(this.getName() + " は剣で斬りかかった！");
        
        // 相手にダメージを与える
        target.takeDamage(damage);
    }
    
    // 剣士独自のスキルなどがあればここから追加していく
    public void slash(BaseCharacter target) {
        if (this.getMp() >= 5) {
            this.setMp(this.getMp() - 5);
            System.out.println(this.getName() + " の全力斬り！");
            target.takeDamage(this.getAttackPower() * 2); // 2倍ダメージ
        } else {
            System.out.println("MPが足りない!!");
        }
    }
}