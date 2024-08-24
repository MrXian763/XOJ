package com.zicai.xojbackendmodel.model.codesandbox;

import lombok.Getter;

/**
 * 代码沙箱名称枚举类
 */
@Getter
public enum CodeSandBoxNameEnum {
    THIRD_PARTY("thirdParty"),
    REMOTE("remote"),
    EXAMPLE("example");

    private final String name;

    CodeSandBoxNameEnum(String name) {
        this.name = name;
    }

    public static CodeSandBoxNameEnum getEnumByValue(String value) {
        if (value == null) {
            return null;
        }
        for (CodeSandBoxNameEnum anEnum : CodeSandBoxNameEnum.values()) {
            if (anEnum.name.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
