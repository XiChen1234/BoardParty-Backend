package com.xichen.Entity.DO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("game_tag")
public class GameTag {
    @TableId
    private Long gameId;
    private Long tagId;
}