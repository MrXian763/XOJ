package com.zicai.xojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.codesandbox.JudgeInfo;
import com.zicai.xojbackendmodel.model.dto.question.JudgeCase;
import com.zicai.xojbackendmodel.model.dto.question.JudgeConfig;
import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext 上下文
     * @return 判题结果
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        Question question = judgeContext.getQuestion();

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMemory(memory);
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getText());

        // 如果输出结果数量不等于输入结果数量，说明代码有问题
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getText());
            return judgeInfoResponse;
        }

        // 逐一比对代码的实际输出与预期输出是否一致
        for (int i = 0; i < inputList.size(); i++) {
            if (!judgeCaseList.get(i).getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getText());
                return judgeInfoResponse;
            }
        }

        // 判断答案是否超出限制（时间、空间）
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        if (judgeConfig == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题配置错误");
        }
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long timeLimit = judgeConfig.getTimeLimit();
        if (memory > memoryLimit) { // 超出空间限制
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getText());
            return judgeInfoResponse;
        }
        if (time > timeLimit) { // 超出时间限制
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getText());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getText());
        return judgeInfoResponse;
    }
}
