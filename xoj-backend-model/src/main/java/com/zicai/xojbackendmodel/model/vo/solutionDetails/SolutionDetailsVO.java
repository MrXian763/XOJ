package com.zicai.xojbackendmodel.model.vo.solutionDetails;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 题解响应体
 * @author zicai
 * @date 2024-09-5
 */
@Data
public class SolutionDetailsVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 题目 id
     */
    private Long problemId;

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
     * 创建者昵称
     */
    private String userNickName;

    /**
     * 创建者头像
     */
    private String userAvatarUrl;

    /**
     * 当前用户是否点赞
     */
    private Boolean isLike;

    /**
     * 当前用户是否收藏
     */
    private Boolean isFavorite;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}