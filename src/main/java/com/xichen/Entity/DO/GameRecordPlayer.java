package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 玩家参与对局的游戏记录
 */
@Data
@TableName("game_record_player")
public class GameRecordPlayer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recordId;
    private Long userId;
    private Boolean isWin;
}
