package com.zicai.xojbackendjudgeservice.judge;

import com.zicai.xojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId 题目提交id
     * @return 判题结果
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
