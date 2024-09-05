package com.zicai.xojbackendquestionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsAddDTO;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsUpdateDTO;
import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.entity.SolutionDetails;
import com.zicai.xojbackendmodel.model.entity.User;
import com.zicai.xojbackendmodel.model.vo.SolutionDetailsVO;
import com.zicai.xojbackendquestionservice.mapper.SolutionDetailsMapper;
import com.zicai.xojbackendquestionservice.service.QuestionSolutionDetailsService;
import com.zicai.xojbackendserviceclient.service.QuestionFeignClient;
import com.zicai.xojbackendserviceclient.service.UserFeignClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public String addSolutionDetails(SolutionDetailsAddDTO solutionDetailsAddDTO, HttpServletRequest request) {
        // 参数校验
        if (solutionDetailsAddDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        Long problemId = solutionDetailsAddDTO.getProblemId();
        Question question = questionFeignClient.getQuestionById(problemId);
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目不存在");
        }
        // 插入数据
        SolutionDetails solutionDetails = new SolutionDetails();
        BeanUtils.copyProperties(solutionDetailsAddDTO, solutionDetails);
        solutionDetails.setUserId(loginUser.getId());
        boolean saveResult = this.save(solutionDetails);
        return saveResult ? "新增成功" : "新增失败";
    }

    @Override
    public SolutionDetailsVO updateSolutionDetails(SolutionDetailsUpdateDTO solutionDetailsUpdateDTO) {
        // 参数校验
        if (solutionDetailsUpdateDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = solutionDetailsUpdateDTO.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        SolutionDetails oldSolutionDetails = this.getById(id);
        if (oldSolutionDetails == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题解不存在");
        }
        // 执行更新
        SolutionDetails newSolutionDetails = new SolutionDetails();
        BeanUtils.copyProperties(solutionDetailsUpdateDTO, newSolutionDetails);
        boolean updateResult = this.updateById(newSolutionDetails);
        // 返回数据
        if (!updateResult) { // 更新失败返回旧数据
            return this.getSolutionDetailsVO(oldSolutionDetails);
        }
        newSolutionDetails.setUserId(oldSolutionDetails.getUserId());
        newSolutionDetails.setProblemId(oldSolutionDetails.getProblemId());
        return this.getSolutionDetailsVO(newSolutionDetails);
    }

    /**
     * 封装返回对象
     * @param solutionDetails 原始数据
     * @return 封装后的对象
     */
    private SolutionDetailsVO getSolutionDetailsVO(SolutionDetails solutionDetails) {
        SolutionDetailsVO solutionDetailsVO = new SolutionDetailsVO();
        BeanUtils.copyProperties(solutionDetails, solutionDetailsVO);
        return solutionDetailsVO;
    }
}




