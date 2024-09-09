package com.zicai.xojbackendserviceclient.service;

import com.zicai.xojbackendcommon.common.BaseResponse;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendmodel.model.entity.User;
import com.zicai.xojbackendmodel.model.enums.UserRoleEnum;
import com.zicai.xojbackendmodel.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.zicai.xojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 */
@FeignClient(name = "xoj-backend-user-service", path = "/api/user/inner")
public interface UserFeignClient {

    /**
     * 根据id获取用户
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据id列表获取用户列表
     *
     * @param idList id列表
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 关注、取关用户
     *
     * @param followerId 被关注者 id
     * @param isFollow   true-关注; false-取关操作
     * @param request    请求信息
     * @return 结果
     */
    @GetMapping("/follower/do")
    BaseResponse<String> doFollow(@RequestParam Long followerId, Boolean isFollow, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

}
