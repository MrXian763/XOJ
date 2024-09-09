package com.zicai.xojbackenduserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zicai.xojbackendmodel.model.entity.FollowRelationship;

import javax.servlet.http.HttpServletRequest;

/**
* @author xianzicai
* @description 针对表【follow_relationship(用户关注粉丝)】的数据库操作Service
* @createDate 2024-09-09 21:01:04
*/
public interface FollowRelationshipService extends IService<FollowRelationship> {

    /**
     * 关注、取关用户
     * @param followerId 被关注者 id
     * @param isFollow true-关注; false-取关操作
     * @param request 请求信息
     * @return 结果
     */
    String doFollow(Long followerId, Boolean isFollow, HttpServletRequest request);
}
