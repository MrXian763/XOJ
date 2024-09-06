package com.zicai.xojbackendmodel.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户题解点赞
 * @TableName user_solution_details_thumbs
 * @author zicai
 * @Date 2024-09-6
 */
@TableName(value ="user_solution_details_thumbs")
@Data
public class UserSolutionDetailsThumbs implements Serializable {
    /**
     * 主键 id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 题解 id
     */
    private Long solutionDetailsId;

    /**
     * 是否删除 1-删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}