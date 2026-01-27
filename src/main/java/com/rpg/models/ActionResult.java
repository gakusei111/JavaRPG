package com.rpg.models;

public class ActionResult {
    private String message;   // 「勇者の攻撃！」などのメッセージ
    private int damage;      // 与えたダメージ量
    private String targetName; // 誰に当てたか

    public ActionResult(String message, int damage, String targetName) {
        this.message = message;
        this.damage = damage;
        this.targetName = targetName;
    }

    // Getterのみ（データは不変にしたいのでSetterはなし）
    public String getMessage() { return message; }
    public int getDamage() { return damage; }
    public String getTargetName() { return targetName; }

    // API用やログ用に文字列を整形するメソッド
    public String getFullLog() {
        return String.format("%s (%sに %d のダメージ！)", message, targetName, damage);
    }
}