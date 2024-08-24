package com.zicai.xojbackendjudgeservice.judge.codesandbox;

import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.zicai.xojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口
 */
public interface CodeSandBox {

    /**
     * 执行代码
     * @param executeCodeRequest 请求数据
     * @return 运行结果数据
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
