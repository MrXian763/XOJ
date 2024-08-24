package com.zicai.xojbackendquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zicai.xojbackendmodel.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.zicai.xojbackendmodel.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.zicai.xojbackendmodel.model.entity.QuestionSubmit;
import com.zicai.xojbackendmodel.model.entity.User;
import com.zicai.xojbackendmodel.model.vo.QuestionSubmitVO;

/**
 * @author xianziye
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2024-07-11 11:52:38
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 题目提交添加信息
     * @param loginUser                当前登录用户
     * @return 题目id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 构造查询条件
     * <p>
     * 用户根据哪些字段查询，根据前端传来的请求对象，得到 MyBatis-Plus 框架支持的查询 QueryWrapper 类
     *
     * @param questionSubmitQueryRequest 题目提交查询条件
     * @return 构造好的查询条件
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit 题目提交信息
     * @param loginUser      登录用户
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitVOPage 分页题目提交信息
     * @param loginUser            登录用户
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitVOPage, User loginUser);

}
