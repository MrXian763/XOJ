package com.zicai.xojbackendquestionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zicai.xojbackendmodel.model.entity.UserSolutionDetailsFavorite;

/**
 * @author xianzicai
 * @description 针对表【user_solution_details_favorite(用户题解收藏)】的数据库操作Service
 * @createDate 2024-09-06 19:54:42
 */
public interface UserSolutionDetailsFavoriteService extends IService<UserSolutionDetailsFavorite> {

    /**
     * 添加收藏记录
     * @param solutionDetailsId 题解 id
     * @param userId 用户 id
     */
    void addFavoriteRecord(Long solutionDetailsId, Long userId);

    /**
     * 移除收藏记录
     * @param solutionDetailsId 题解 id
     * @param userId 用户 id
     */
    void removeFavoriteRecord(Long solutionDetailsId, Long userId);

    /**
     * 查询当前用户题解收藏记录数量
     * @param userId 用户 id
     * @param solutionDetailsId 题解 id
     * @return 收藏记录数量
     */
    long countByUserIdAndSolutionDetailsId(Long userId, Long solutionDetailsId);
}
