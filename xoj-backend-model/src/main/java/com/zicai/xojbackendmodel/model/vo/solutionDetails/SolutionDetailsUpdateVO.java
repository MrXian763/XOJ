package com.zicai.xojbackendmodel.model.vo.solutionDetails;

import com.baomidou.mybatisplus.annotation.*;
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
public class SolutionDetailsUpdateVO implements Serializable {

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
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}