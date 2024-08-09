package com.lfq.tts.tools.netty.eunm;

/**
 * 返回状态枚举
 */
public enum Reseunm {

    success("成功","0000"),
    fail("失败","9999"),
    erorr("异常","4444");

    /**
     * 根据类型的名称，返回类型的枚举实例。
     * @param typeName 类型名称
     */
    public static Reseunm fromName(String typeName) {
        for (Reseunm type : Reseunm.values()) {
            if (type.getName().equals(typeName)) {
                return type;
            }
        }
        return null;
    }



    private String name;
    private String value;

    Reseunm(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
