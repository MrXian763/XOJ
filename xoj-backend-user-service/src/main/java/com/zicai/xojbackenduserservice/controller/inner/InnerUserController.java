package com.zicai.xojbackenduserservice.controller.inner;

import com.zicai.xojbackendcommon.common.BaseResponse;
import com.zicai.xojbackendcommon.common.ResultUtils;
import com.zicai.xojbackendmodel.model.entity.User;
import com.zicai.xojbackendserviceclient.service.UserFeignClient;
import com.zicai.xojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zicai.xojbackenduserservice.service.FollowRelationshipService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * 服务内部调用接口实现类
 */
@RestController
@RequestMapping("/inner")
public class InnerUserController implements UserFeignClient {

    @Resource
    private UserService userService;
    @Resource
    private FollowRelationshipService followRelationshipService;

    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }

    @GetMapping("/follower/do")
    public BaseResponse<String> doFollow(@RequestParam Long followerId, Boolean isFollow, HttpServletRequest request) {
        return ResultUtils.success(followRelationshipService.doFollow(followerId, isFollow, request));
    }
}
