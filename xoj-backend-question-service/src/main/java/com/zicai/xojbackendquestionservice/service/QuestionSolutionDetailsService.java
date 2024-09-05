package com.zicai.xojbackendquestionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsAddDTO;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsUpdateDTO;
import com.zicai.xojbackendmodel.model.entity.SolutionDetails;
import com.zicai.xojbackendmodel.model.vo.solutionDetails.SolutionDetailsUpdateVO;
import com.zicai.xojbackendmodel.model.vo.solutionDetails.SolutionDetailsVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author xianzicai
* @description 针对表【solution_details(题解)】的数据库操作Service
* @createDate 2024-09-03 11:20:47
*/
public interface QuestionSolutionDetailsService extends IService<SolutionDetails> {

    /**
     * 创建题解
     * @param solutionDetailsAddDTO 题解信息
     * @return 创建结果
     */
    String addSolutionDetails(SolutionDetailsAddDTO solutionDetailsAddDTO, HttpServletRequest request);

    /**
     * 更新题解
     * @param solutionDetailsUpdateDTO 题解信息
     * @return 更新结果
     */
    SolutionDetailsUpdateVO updateSolutionDetails(SolutionDetailsUpdateDTO solutionDetailsUpdateDTO);

    /**
     * 根据 id 获取题解
     * @param id 题解 id
     * @return 单条题解
     */
    SolutionDetailsVO getSolutionDetailsById(long id);
}
