package com.zicai.xojbackendquestionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsAddDTO;
import com.zicai.xojbackendmodel.model.dto.question.solutionDetails.SolutionDetailsUpdateDTO;
import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.entity.SolutionDetails;
import com.zicai.xojbackendmodel.model.entity.User;
import com.zicai.xojbackendmodel.model.vo.solutionDetails.SolutionDetailsUpdateVO;
import com.zicai.xojbackendmodel.model.vo.solutionDetails.SolutionDetailsVO;
import com.zicai.xojbackendquestionservice.mapper.SolutionDetailsMapper;
import com.zicai.xojbackendquestionservice.service.QuestionSolutionDetailsService;
import com.zicai.xojbackendquestionservice.service.UserSolutionDetailsThumbsService ;
import com.zicai.xojbackendserviceclient.service.QuestionFeignClient;
import com.zicai.xojbackendserviceclient.service.UserFeignClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xianzicai
 * @description 针对表【solution_details(题解)】的数据库操作Service实现
 * @createDate 2024-09-03 11:20:47
 */
@Service
public class QuestionSolutionDetailsServiceImpl extends ServiceImpl<SolutionDetailsMapper, SolutionDetails>
        implements QuestionSolutionDetailsService {

    @Resource
    private SolutionDetailsMapper solutionDetailsMapper;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private UserSolutionDetailsThumbsService userSolutionDetailsThumbsService;

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
    public SolutionDetailsUpdateVO updateSolutionDetails(SolutionDetailsUpdateDTO solutionDetailsUpdateDTO) {
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
            return this.getSolutionDetailsUpdateVO(oldSolutionDetails);
        }
        newSolutionDetails.setUserId(oldSolutionDetails.getUserId());
        newSolutionDetails.setProblemId(oldSolutionDetails.getProblemId());
        return this.getSolutionDetailsUpdateVO(newSolutionDetails);
    }

    @Override
    public SolutionDetailsVO getSolutionDetailsById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SolutionDetails solutionDetails = this.getById(id);
        if (solutionDetails == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题解不存在");
        }
        return this.getSolutionDetailsVO(solutionDetails);
    }

    @Override
    public List<SolutionDetailsVO> listSolutionDetailsByProblemId(Long problemId) {
        if (problemId == null || problemId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionFeignClient.getQuestionById(problemId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        QueryWrapper<SolutionDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problemId", problemId);
        List<SolutionDetails> solutionDetailsList = this.list(queryWrapper);
        if (solutionDetailsList == null) {
            return new ArrayList<>();
        }
        List<SolutionDetailsVO> solutionDetailsVOList = solutionDetailsList.stream()
                .map(this::getSolutionDetailsVO)
                .collect(Collectors.toList());
        solutionDetailsVOList.stream().forEach(solutionDetailsVO -> {
            Long userId = solutionDetailsVO.getUserId();
            long count = userSolutionDetailsThumbsService.countByUserIdAndSolutionDetailsId(userId, solutionDetailsVO.getId());
            if (count <= 0) {
                solutionDetailsVO.setIsLike(false);
            }
            User user = userFeignClient.getById(userId);
            solutionDetailsVO.setUserNickName(user.getUserName());
            solutionDetailsVO.setUserAvatarUrl(user.getUserAvatar());
            if (solutionDetailsVO.getContent() == null || solutionDetailsVO.getContent().trim().isEmpty()) {
                solutionDetailsVO.setContent("该用户暂未填写思路");
            }
        });
        return solutionDetailsVOList;
    }

    @Override
    public List<SolutionDetailsVO> listMySolutionDetails(HttpServletRequest request) {
        // 获取当前登录用户
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 根据用户 id 查询题解
        Long loginUserId = loginUser.getId();
        QueryWrapper<SolutionDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", loginUserId);
        List<SolutionDetails> solutionDetailsList = this.list(queryWrapper);
        // 返回数据
        if (solutionDetailsList == null) {
            return Collections.emptyList();
        }
        return solutionDetailsList.stream().map(this::getSolutionDetailsVO).collect(Collectors.toList());
    }

    @Override
    public Long countByProblemId(Long problemId) {
        if (problemId == null || problemId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<SolutionDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problemId", problemId);
        return this.count(queryWrapper);
    }

    @Override
    public String likeSolutionDetails(Long id, Boolean isLike, HttpServletRequest request) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userFeignClient.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 更新题解点赞数
        if (isLike) {
            solutionDetailsMapper.incLikeCounts(id);
        } else {
            solutionDetailsMapper.decLikeCounts(id);
        }
        // 更新用户题解点赞表
        if (isLike) {
            userSolutionDetailsThumbsService.addLikeRecord(id, loginUser.getId());
        } else {
            userSolutionDetailsThumbsService.removeLikeRecord(id, loginUser.getId());
        }
        return "操作成功";
    }

    /**
     * 封装返回对象
     *
     * @param solutionDetails 原始数据
     * @return 封装后的对象
     */
    private SolutionDetailsUpdateVO getSolutionDetailsUpdateVO(SolutionDetails solutionDetails) {
        SolutionDetailsUpdateVO solutionDetailsUpdateVO = new SolutionDetailsUpdateVO();
        BeanUtils.copyProperties(solutionDetails, solutionDetailsUpdateVO);
        return solutionDetailsUpdateVO;
    }

    /**
     * 封装返回对象
     *
     * @param solutionDetails 原始数据
     * @return 封装后的对象
     */
    private SolutionDetailsVO getSolutionDetailsVO(SolutionDetails solutionDetails) {
        SolutionDetailsVO solutionDetailsVO = new SolutionDetailsVO();
        BeanUtils.copyProperties(solutionDetails, solutionDetailsVO);
        return solutionDetailsVO;
    }
}




