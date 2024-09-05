package com.zicai.xojbackendquestionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsDto;
import com.zicai.xojbackendmodel.model.entity.SolutionDetails;

/**
* @author xianzicai
* @description 针对表【solution_details(题解)】的数据库操作Service
* @createDate 2024-09-03 11:20:47
*/
public interface QuestionSolutionDetailsService extends IService<SolutionDetails> {

    /**
     * 创建题解
     * @param solutionDetailsDto 题解信息
     * @return 创建结果
     */
    String addSolutionDetails(SolutionDetailsDto solutionDetailsDto);
}
