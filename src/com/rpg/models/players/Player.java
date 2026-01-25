package com.rpg.models.players;

import com.rpg.models.Attacker; // ★追加: インターフェースを読み込む
import com.rpg.models.BaseCharacter;

// ★変更: "implements Attacker" を追加
public abstract class Player extends BaseCharacter implements Attacker {
    private int mp;
    private int maxMp;
    private String jobName;

    public Player(String name, String jobName, int hp, int mp, int attackPower) {
        super(name, hp, attackPower);
        this.jobName = jobName;
        this.maxMp = mp;
        this.mp = mp;
    }

    public String getJobName() { return jobName; }
    public int getMp() { return mp; }
    public int getMaxMp() { return maxMp; }

    public void setMp(int mp) {
        if (mp < 0) {
            this.mp = 0;
        } else if (mp > this.maxMp) {
            this.mp = this.maxMp;
        } else {
            this.mp = mp;
        }
    }

    // ★追加: Player共通の「通常攻撃」
    // Swordsmanなど個別に書きたい場合はOverrideすればOKですが、
    // ここに書いておくと将来「魔法使い」を作った時にattackを書かなくても動くようになります。
    @Override
    public void attack(BaseCharacter target) {
        System.out.println(this.getName() + " の攻撃！");
        target.takeDamage(this.getAttackPower());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (HP: %d/%d, MP: %d/%d)", 
            jobName, getName(), getHp(), getMaxHp(), mp, maxMp);
    }
}