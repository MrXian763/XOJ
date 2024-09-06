package com.zicai.xojbackendquestionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.entity.UserSolutionDetailsFavorite;
import com.zicai.xojbackendquestionservice.mapper.UserSolutionDetailsFavoriteMapper;
import com.zicai.xojbackendquestionservice.service.UserSolutionDetailsFavoriteService;
import org.springframework.stereotype.Service;

/**
 * @author xianzicai
 * @description 针对表【user_solution_details_favorite(用户题解收藏)】的数据库操作Service实现
 * @createDate 2024-09-06 19:54:42
 */
@Service
public class UserSolutionDetailsFavoriteServiceImpl extends ServiceImpl<UserSolutionDetailsFavoriteMapper, UserSolutionDetailsFavorite>
        implements UserSolutionDetailsFavoriteService {

    @Override
    public void addFavoriteRecord(Long solutionDetailsId, Long userId) {
        if (solutionDetailsId == null || userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserSolutionDetailsFavorite userSolutionDetailsFavorite = new UserSolutionDetailsFavorite();
        userSolutionDetailsFavorite.setSolutionDetailsId(solutionDetailsId);
        userSolutionDetailsFavorite.setUserId(userId);
        this.save(userSolutionDetailsFavorite);
    }

    @Override
    public void removeFavoriteRecord(Long solutionDetailsId, Long userId) {
        if (solutionDetailsId == null || userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserSolutionDetailsFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solutionDetailsId", solutionDetailsId);
        queryWrapper.eq("userId", userId);
        this.remove(queryWrapper);
    }

    @Override
    public long countByUserIdAndSolutionDetailsId(Long userId, Long solutionDetailsId) {
        if (userId == null || solutionDetailsId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserSolutionDetailsFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solutionDetailsId", solutionDetailsId);
        queryWrapper.eq("userId", userId);
        return this.count(queryWrapper);
    }
}




