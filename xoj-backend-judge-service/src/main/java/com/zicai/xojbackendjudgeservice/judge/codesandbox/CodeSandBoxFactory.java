package com.zicai.xojbackendjudgeservice.judge.codesandbox;

import com.zicai.xojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandBoxImpl;
import com.zicai.xojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandBoxImpl;
import com.zicai.xojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandBoxImpl;
import com.zicai.xojbackendmodel.model.codesandbox.CodeSandBoxNameEnum;

/**
 * 代码沙箱工厂（根据参数创建指定的代码沙箱示例）
 */
public class CodeSandBoxFactory {

    /**
     * 根据参数创建指定的代码沙箱
     * @param codeSandBoxName 代码沙箱类型
     * @return 代码沙箱实例
     */
    public static CodeSandBox newInstance(String codeSandBoxName) {
        CodeSandBoxNameEnum codeSandBoxNameEnum = CodeSandBoxNameEnum.getEnumByValue(codeSandBoxName);
        switch (codeSandBoxNameEnum) {
            case EXAMPLE:
                return new ExampleCodeSandBoxImpl();
            case REMOTE:
                return new RemoteCodeSandBoxImpl();
            case THIRD_PARTY:
                return new ThirdPartyCodeSandBoxImpl();
            default:
                return new ExampleCodeSandBoxImpl();
        }
    }

}
