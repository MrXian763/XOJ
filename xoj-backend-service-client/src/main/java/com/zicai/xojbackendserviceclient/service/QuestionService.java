package com.zicai.xojbackendserviceclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zicai.xojbackendmodel.model.dto.question.QuestionQueryRequest;
import com.zicai.xojbackendmodel.model.entity.Question;
import com.zicai.xojbackendmodel.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xianziye
 * @description 针对表【question(题目)】的数据库操作Service
 * @createDate 2024-07-11 11:52:20
 */
public interface QuestionService extends IService<Question> {

    /**
     * 校验
     *
     * @param question 题目信息
     * @param add 是否是创建题目
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest 题目查询条件
     * @return 构造好的查询条件
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取题目封装
     *
     * @param question 原始题目数据
     * @param request 登录信息
     * @return 封装好的题目数据
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage 题目分页数据
     * @param request 登录信息
     * @return 脱敏后的数据
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

}
