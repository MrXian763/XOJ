package com.zicai.xojbackendmodel.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户关注粉丝
 * @TableName follow_relationship
 */
@TableName(value ="follow_relationship")
@Data
public class FollowRelationship implements Serializable {
    /**
     * 主键 id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关注者 id
     */
    private Long followerId;

    /**
     * 被关注者 id
     */
    private Long followedId;

    /**
     * 关注时间
     */
    private LocalDateTime createTime;

    /**
     * 是否删除 1-删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}