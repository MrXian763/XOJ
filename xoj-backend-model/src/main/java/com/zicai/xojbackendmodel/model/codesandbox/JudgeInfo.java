package com.zicai.xojbackendmodel.model.codesandbox;

import lombok.Data;

/**
 * 题目执行信息
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗时间（ms）
     */
    private Long time;

    /**
     * 内存消耗（kb）
     */
    private Long memory;

}
