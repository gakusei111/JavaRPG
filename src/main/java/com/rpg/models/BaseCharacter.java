package com.rpg.models;

public abstract class BaseCharacter implements Character {
    private String name;
    private int hp;
    private int maxHp;
    private int attackPower;

    public BaseCharacter(String name, int hp, int attackPower) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp; // 最初は満タン
        this.attackPower = attackPower;
    }

    // Getter群
    @Override public String getName() { return name; }
    @Override public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }

    // Setter (カプセル化の恩恵：不正な値を防ぐ)
    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else if (hp > this.maxHp) {
            this.hp = this.maxHp;
        } else {
            this.hp = hp;
        }
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public void takeDamage(int damage) {
        // 直接変数をいじらず、Setterを経由させる
        setHp(this.hp - damage);
        System.out.println(name + " は " + damage + " のダメージを受けた！ (残りHP: " + getHp() + ")");
    }
}