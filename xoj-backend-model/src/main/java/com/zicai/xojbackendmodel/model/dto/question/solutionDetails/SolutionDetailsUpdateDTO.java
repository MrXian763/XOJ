package com.zicai.xojbackendmodel.model.dto.question.solutionDetails;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新题解请求体
 * @author zicai
 * @date 2024-09-5
 */
@Data
public class SolutionDetailsUpdateDTO implements Serializable {

    /**
     * 主键 id
     */
    private Long id;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 代码语言
     */
    private String codeLanguage;

    /**
     * 题解描述
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}