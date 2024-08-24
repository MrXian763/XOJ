package com.zicai.xojbackendmodel.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 第三方代码沙箱请求体
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionRequest {

    /**
     * 代码
     */
    private String source_code;

    /**
     * 语言
     */
    private int language_id;

    /**
     * 输入
     */
    private String stdin;

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }
}