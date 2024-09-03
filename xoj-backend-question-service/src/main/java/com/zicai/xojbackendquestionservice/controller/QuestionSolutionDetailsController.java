package com.zicai.xojbackendquestionservice.controller;

import com.zicai.xojbackendquestionservice.service.QuestionSolutionDetailsService;
import lombok.extern.slf4j.Slf4j;
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
}
