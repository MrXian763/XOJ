package com.zicai.xojbackendquestionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsDto;
import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.entity.SolutionDetails;
import com.zicai.xojbackendmodel.model.entity.User;
import com.zicai.xojbackendquestionservice.mapper.SolutionDetailsMapper;
import com.zicai.xojbackendquestionservice.service.QuestionSolutionDetailsService;
import com.zicai.xojbackendserviceclient.service.QuestionFeignClient;
import com.zicai.xojbackendserviceclient.service.UserFeignClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author xianzicai
* @description 针对表【solution_details(题解)】的数据库操作Service实现
* @createDate 2024-09-03 11:20:47
*/
@Service
public class QuestionQuestionSolutionDetailsServiceImpl extends ServiceImpl<SolutionDetailsMapper, SolutionDetails>
    implements QuestionSolutionDetailsService {

    @Resource
    private SolutionDetailsMapper solutionDetailsMapper;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Override
    public String addSolutionDetails(SolutionDetailsDto solutionDetailsDto) {
        // 参数校验
        if (solutionDetailsDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long userId = solutionDetailsDto.getUserId();
        User user = userFeignClient.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        Long problemId = solutionDetailsDto.getProblemId();
        Question question = questionFeignClient.getQuestionById(problemId);
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        }
        // 插入数据
        SolutionDetails solutionDetails = new SolutionDetails();
        BeanUtils.copyProperties(solutionDetailsDto, solutionDetails);
        int insertResult = solutionDetailsMapper.insert(solutionDetails);
        return insertResult == 1 ? "新增成功" : "新增失败";
    }
}




