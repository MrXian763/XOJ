package com.zicai.xojbackendquestionservice.controller;

import com.zicai.xojbackendcommon.common.BaseResponse;
import com.zicai.xojbackendcommon.common.ResultUtils;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsDto;
import com.zicai.xojbackendquestionservice.service.QuestionSolutionDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 题解接口
 * @author zicai
 * @date 2024/9/3
 */
@RestController
@RequestMapping("/solution-details")
@Slf4j
public class QuestionSolutionDetailsController {

    @Resource
    private QuestionSolutionDetailsService questionSolutionDetailsService;

    /**
     * 创建题解
     * @param solutionDetailsDto 题解信息
     * @return 创建结果
     */
    @PostMapping("/add")
    public BaseResponse<String> addSolutionDetails(@RequestBody SolutionDetailsDto solutionDetailsDto) {
        String addResult = questionSolutionDetailsService.addSolutionDetails(solutionDetailsDto);
        return ResultUtils.success(addResult);
    }
}
