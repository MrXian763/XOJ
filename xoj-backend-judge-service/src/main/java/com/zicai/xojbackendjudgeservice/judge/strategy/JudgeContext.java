package com.zicai.xojbackendjudgeservice.judge.strategy;

import com.zicai.xojbackendmodel.model.codesandbox.JudgeInfo;
import com.zicai.xojbackendmodel.model.dto.question.JudgeCase;
import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    /**
     * 代码沙箱执行信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 代码沙箱输入用例列表
     */
    private List<String> inputList;

    /**
     * 代码沙箱输出用例列表
     */
    private List<String> outputList;

    /**
     * 题目用例列表
     */
    private List<JudgeCase> judgeCaseList;

    /**
     * 题目信息
     */
    private Question question;

    /**
     * 题目提交信息
     */
    private QuestionSubmit questionSubmit;
}
