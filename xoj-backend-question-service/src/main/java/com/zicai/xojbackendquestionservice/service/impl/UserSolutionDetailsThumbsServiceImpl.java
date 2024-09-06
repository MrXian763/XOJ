package com.zicai.xojbackendquestionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.entity.UserSolutionDetailsThumbs;
import com.zicai.xojbackendquestionservice.mapper.UserSolutionDetailsThumbsMapper;
import com.zicai.xojbackendquestionservice.service.UserSolutionDetailsThumbsService;
import org.springframework.stereotype.Service;

/**
 * @author xianzicai
 * @description 针对表【user_solution_details_thumbs(用户题解点赞)】的数据库操作Service实现
 * @createDate 2024-09-06 11:17:24
 */
@Service
public class UserSolutionDetailsThumbsServiceImpl extends ServiceImpl<UserSolutionDetailsThumbsMapper, UserSolutionDetailsThumbs>
        implements UserSolutionDetailsThumbsService {

    @Override
    public long countByUserIdAndSolutionDetailsId(Long userId, Long solutionDetailsId) {
        if (userId == null || solutionDetailsId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserSolutionDetailsThumbs> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("solutionDetailsId", solutionDetailsId);
        return this.count(queryWrapper);
    }

    @Override
    public void addLikeRecord(Long solutionDetailsId, Long userId) {
        if (solutionDetailsId == null || userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserSolutionDetailsThumbs userSolutionDetailsThumbs = new UserSolutionDetailsThumbs();
        userSolutionDetailsThumbs.setSolutionDetailsId(solutionDetailsId);
        userSolutionDetailsThumbs.setUserId(userId);
        this.save(userSolutionDetailsThumbs);
    }

    @Override
    public void removeLikeRecord(Long solutionDetailsId, Long userId) {
        if (solutionDetailsId == null || userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserSolutionDetailsThumbs> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solutionDetailsId", solutionDetailsId);
        queryWrapper.eq("userId", userId);
        this.remove(queryWrapper);
    }
}




