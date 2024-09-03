package com.zicai.xojbackendmodel.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 题解
 * @TableName solution_details
 * @author zicai
 * @date 2024-09-03
 */
@TableName(value ="solution_details")
@Data
public class SolutionDetails implements Serializable {
    /**
     * 主键 id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户 id
     */
    private Integer userId;

    /**
     * 题目 id
     */
    private Integer problemId;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 代码语言
     */
    private String codeLanguage;

    /**
     * 题解描述
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likesCount;

    /**
     * 收藏数
     */
    private Integer favoritesCount;

    /**
     * 评论数
     */
    private Integer commentsCount;

    /**
     * 查看数
     */
    private Integer viewsCount;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 是否删除 1-删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}