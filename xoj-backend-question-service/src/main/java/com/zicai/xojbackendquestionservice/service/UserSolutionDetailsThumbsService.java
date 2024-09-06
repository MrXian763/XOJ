package com.zicai.xojbackendquestionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zicai.xojbackendmodel.model.entity.UserSolutionDetailsThumbs;

/**
* @author xianzicai
* @description 针对表【user_solution_details_thumbs(用户题解点赞)】的数据库操作Service
* @createDate 2024-09-06 11:17:24
*/
public interface UserSolutionDetailsThumbsService extends IService<UserSolutionDetailsThumbs> {

    /**
     * 根据用户 id 和题解 id 查询点赞记录
     * @param userId 用户 id
     * @param solutionDetailsId 题解 id
     * @return 点赞记录数量
     */
    long countByUserIdAndSolutionDetailsId(Long userId, Long solutionDetailsId);

    /**
     * 添加点赞记录
     * @param solutionDetailsId 题解 id
     * @param userId 用户 id
     */
    void addLikeRecord(Long solutionDetailsId, Long userId);

    /**
     * 移除点赞记录
     * @param solutionDetailsId 题解 id
     * @param userId 用户 id
     */
    void removeLikeRecord(Long solutionDetailsId, Long userId);
}
