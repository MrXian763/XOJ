package com.zicai.xojbackendjudgeservice.controller.inner;

import com.zicai.xojbackendjudgeservice.judge.JudgeService;
import com.zicai.xojbackendmodel.model.entity.QuestionSubmit;
import com.zicai.xojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("/inner")
public class InnerJudgeController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
