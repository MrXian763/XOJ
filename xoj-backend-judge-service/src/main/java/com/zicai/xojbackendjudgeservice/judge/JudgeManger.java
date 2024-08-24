package com.zicai.xojbackendjudgeservice.judge;

import com.zicai.xojbackendjudgeservice.judge.strategy.*;
import com.zicai.xojbackendmodel.model.codesandbox.JudgeInfo;
import com.zicai.xojbackendmodel.model.entity.QuestionSubmit;
import com.zicai.xojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import org.springframework.stereotype.Service;

/**
 * 自动根据语言选择判题策略
 */
@Service
public class JudgeManger {

    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            return judgeStrategy.doJudge(judgeContext);
        }
        switch (languageEnum) {
            case JAVA:
                judgeStrategy = new JavaJudgeStrategy();
                break;
            case CPLUSPLUS:
                judgeStrategy = new CPPJudgeStrategy();
                break;
            case C:
                judgeStrategy = new CJudgeStrategy();
                break;
            case GOLANG:
                judgeStrategy = new GoLangJudgeStrategy();
                break;
            case PYTHON:
                judgeStrategy = new PythonJudgeStrategy();
                break;
            default:
                judgeStrategy = new DefaultJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
