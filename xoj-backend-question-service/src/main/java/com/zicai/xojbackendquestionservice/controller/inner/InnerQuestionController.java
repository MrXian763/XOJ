package com.zicai.xojbackendquestionservice.controller.inner;

import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.entity.QuestionSubmit;
import com.zicai.xojbackendquestionservice.service.QuestionService;
import com.zicai.xojbackendquestionservice.service.QuestionSubmitService;
import com.zicai.xojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("/inner")
public class InnerQuestionController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @Override
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
