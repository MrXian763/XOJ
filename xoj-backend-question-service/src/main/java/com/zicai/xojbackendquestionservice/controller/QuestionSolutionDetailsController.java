package com.zicai.xojbackendquestionservice.controller;

import com.zicai.xojbackendcommon.common.BaseResponse;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.common.ResultUtils;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsAddDTO;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsUpdateDTO;
import com.zicai.xojbackendmodel.model.vo.solutionDetails.SolutionDetailsUpdateVO;
import com.zicai.xojbackendmodel.model.vo.solutionDetails.SolutionDetailsVO;
import com.zicai.xojbackendquestionservice.service.QuestionSolutionDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 题解接口
 *
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
     *
     * @param solutionDetailsAddDTO 题解信息
     * @return 创建结果
     */
    @PostMapping("/add")
    public BaseResponse<String> addSolutionDetails(@RequestBody SolutionDetailsAddDTO solutionDetailsAddDTO, HttpServletRequest request) {
        String addResult = questionSolutionDetailsService.addSolutionDetails(solutionDetailsAddDTO, request);
        return ResultUtils.success(addResult);
    }

    /**
     * 删除题解
     *
     * @param id 题解 id
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteSolutionDetails(@RequestParam Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean removeResult = questionSolutionDetailsService.removeById(id);
        return ResultUtils.success(removeResult ? "删除成功" : "删除失败");
    }

    /**
     * 更新题解
     *
     * @param solutionDetailsUpdateDTO 题解信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public BaseResponse<SolutionDetailsUpdateVO> updateSolutionDetails(@RequestBody SolutionDetailsUpdateDTO solutionDetailsUpdateDTO) {
        SolutionDetailsUpdateVO solutionDetailsUpdateVO = questionSolutionDetailsService.updateSolutionDetails(solutionDetailsUpdateDTO);
        return ResultUtils.success(solutionDetailsUpdateVO);
    }

    /**
     * 根据 id 获取题解
     *
     * @param id 题解 id
     * @return 单条题解
     */
    @GetMapping("/get")
    public BaseResponse<SolutionDetailsVO> getSolutionDetailsById(@RequestParam Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SolutionDetailsVO solutionDetailsVO = questionSolutionDetailsService.getSolutionDetailsById(id);
        return ResultUtils.success(solutionDetailsVO);
    }

    /**
     * 根据 problemId 获取题解列表
     *
     * @param problemId 题目 id
     * @return 题解列表
     */
    @GetMapping("/list/problemId")
    public BaseResponse<List<SolutionDetailsVO>> listSolutionDetailsByProblemId(@RequestParam Long problemId) {
        if (problemId == null || problemId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<SolutionDetailsVO> solutionDetailsVOList = questionSolutionDetailsService.listSolutionDetailsByProblemId(problemId);
        return ResultUtils.success(solutionDetailsVOList);
    }

    /**
     * 获取当前用登录户创建的题解列表
     *
     * @param request 当前登录信息
     * @return 题解列表
     */
    @GetMapping("/list/my")
    public BaseResponse<List<SolutionDetailsVO>> listMySolutionDetails(HttpServletRequest request) {
        List<SolutionDetailsVO> solutionDetailsVOList = questionSolutionDetailsService.listMySolutionDetails(request);
        return ResultUtils.success(solutionDetailsVOList);
    }

    /**
     * 获取题目的题解数量
     *
     * @param problemId 题目 id
     * @return 题目题解数量
     */
    @GetMapping("/count")
    public BaseResponse<Long> countByProblemId(@RequestParam Long problemId) {
        Long count = questionSolutionDetailsService.countByProblemId(problemId);
        return ResultUtils.success(count);
    }

    /**
     * 点赞题解
     *
     * @param id 题解 id
     * @param isLike 是否点赞
     * @return 点赞结果
     */
    @GetMapping("/like-count/update")
    public BaseResponse<String> likeSolutionDetails(@RequestParam Long id, Boolean isLike, HttpServletRequest request) {
        String likeResult = questionSolutionDetailsService.likeSolutionDetails(id, isLike, request);
        return ResultUtils.success(likeResult);
    }

}
