package com.zicai.xojbackendmodel.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱请求体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {

    /**
     * 输入用例组
     */
    private List<String> inputList;

    /**
     * 代码
     */
    private String code;

    /**
     * 代码语言
     */
    private String language;

}
