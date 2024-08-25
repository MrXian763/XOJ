package com.zicai.xojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.zicai.xojbackendjudgeservice.judge.codesandbox.CodeSandBoxFactory;
import com.zicai.xojbackendjudgeservice.judge.codesandbox.CodeSandBoxProxy;
import com.zicai.xojbackendjudgeservice.judge.strategy.JudgeContext;
import com.zicai.xojbackendmodel.model.codesandbox.CodeSandBoxNameEnum;
import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.zicai.xojbackendmodel.model.codesandbox.JudgeInfo;
import com.zicai.xojbackendmodel.model.dto.question.JudgeCase;
import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.entity.QuestionSubmit;
import com.zicai.xojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.zicai.xojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.zicai.xojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManger judgeManger;

    @Value("${codesandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 根据题目提交 id 获取题目相关信息
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 如果题目状态不是等待中，不能判题
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在判题中");
        }

        // 更新题目提交状态为判题中
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.JUDGING.getValue());
        boolean updateResult = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目状态失败");
        }

        // 调用代码沙箱，获取执行结果
        CodeSandBoxNameEnum codeSandBoxNameEnum = CodeSandBoxNameEnum.getEnumByValue(type);
        if (codeSandBoxNameEnum == null) { // 默认使用示例代码沙箱
            codeSandBoxNameEnum = CodeSandBoxNameEnum.EXAMPLE;
        }
        // 根据配置获取对应的代码沙箱
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(codeSandBoxNameEnum.getName());
        codeSandBox = new CodeSandBoxProxy(codeSandBox); // 使用代理模式实例化代码沙箱
        String code = questionSubmit.getCode();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(questionSubmit.getLanguage());
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }

        // 调用代码沙箱获取代码执行结果
        String language = languageEnum.getValue();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        // 根据沙箱执行结果构造判题信息
        if (executeCodeResponse == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题失败");
        }

        // 根据语言动态创建判题策略判题
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManger.doJudge(judgeContext);

        // 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        updateResult = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目状态失败");
        }

        return questionFeignClient.getQuestionSubmitById(questionSubmitId);
    }
}
