package com.zicai.xojbackenduserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.entity.FollowRelationship;
import com.zicai.xojbackendmodel.model.entity.User;
import com.zicai.xojbackenduserservice.mapper.FollowRelationshipMapper;
import com.zicai.xojbackenduserservice.service.FollowRelationshipService;
import com.zicai.xojbackenduserservice.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xianzicai
 * @description 针对表【follow_relationship(用户关注粉丝)】的数据库操作Service实现
 * @createDate 2024-09-09 21:01:04
 */
@Service
public class FollowRelationshipServiceImpl extends ServiceImpl<FollowRelationshipMapper, FollowRelationship>
        implements FollowRelationshipService {

    @Resource
    private FollowRelationshipMapper followRelationshipMapper;
    @Resource
    private UserService userService;

    @Override
    public String doFollow(Long followerId, Boolean isFollow, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (followerId == null || followerId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (isFollow) {
            return this.followUser(followerId, loginUser); // 关注
        }
        return this.cancelFollow(followerId, loginUser); // 取关
        // todo 更新用户粉丝数
    }

    private String followUser(Long followerId, User loginUser) {
        User followedUser = userService.getById(followerId);
        if (followedUser == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (loginUser.getId().equals(followedUser.getId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能关注自己");
        }
        FollowRelationship followRelationship = new FollowRelationship();
        followRelationship.setFollowerId(loginUser.getId());
        followRelationship.setFollowedId(followerId);
        boolean saveResult = this.save(followRelationship);
        return saveResult ? "关注成功" : "关注失败";
    }

    private String cancelFollow(Long followerId, User loginUser) {
        QueryWrapper<FollowRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("followerId", loginUser.getId());
        queryWrapper.eq("followedId", followerId);
        return this.remove(queryWrapper) ? "取消关注成功" : "取消关注失败";
    }

}




