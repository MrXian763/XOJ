package com.zicai.xojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zicai.xojbackendcommon.common.ErrorCode;
import com.zicai.xojbackendcommon.exception.BusinessException;
import com.zicai.xojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱（实际调用远程接口的沙箱）
 */
public class RemoteCodeSandBoxImpl implements CodeSandBox {

    // 代码沙箱鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://localhost:8080/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET) // 请求头携带秘钥
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "代码沙箱接口调用异常");
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
