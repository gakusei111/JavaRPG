package com.rpg.repository;

import com.rpg.models.BattleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleLogRepository extends JpaRepository<BattleLog, Long> {
    // これだけで「保存(save)」「全件取得(findAll)」などが使えるようになります！
    // 魔法のようです。
}