package com.rpg.models.players;

import com.rpg.models.BaseCharacter;
import com.rpg.models.Attacker;
import com.rpg.models.ActionResult;

public abstract class Player extends BaseCharacter implements Attacker {
    private String jobName;
    private int mp;
    private int maxMp;

    public Player(String name, String jobName, int hp, int mp, int attackPower) {
        super(name, hp, attackPower);
        this.jobName = jobName;
        this.mp = mp;
        this.maxMp = mp;
    }

    @Override
    public ActionResult attack(BaseCharacter target) {
        target.takeDamage(this.getAttackPower());
        return new ActionResult(
            this.getName() + " の攻撃！",
            this.getAttackPower(),
            target.getName()
        );
    }

    // ★追加: 各職業で必ず実装させるスキルの抽象メソッド
    public abstract ActionResult useSkill(BaseCharacter target);

    public String getJobName() { return jobName; }
    public int getMp() { return mp; }
    public void setMp(int mp) { this.mp = Math.max(0, Math.min(mp, maxMp)); }
    public int getMaxMp() { return maxMp; }
}