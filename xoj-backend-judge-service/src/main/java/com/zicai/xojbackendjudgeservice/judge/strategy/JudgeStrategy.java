package com.zicai.xojbackendjudgeservice.judge.strategy;

import com.zicai.xojbackendmodel.model.codesandbox.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext 上下文
     * @return 题目执行信息
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
