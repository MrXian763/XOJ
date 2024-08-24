package com.zicai.xojbackendmodel.model.dto.questionSubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 提交用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}