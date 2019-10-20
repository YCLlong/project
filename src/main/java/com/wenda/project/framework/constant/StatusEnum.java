package com.wenda.project.framework.constant;

public enum StatusEnum{

    NORMAL(2000, "正常"),
    DISABLE(0, "禁用"),
    DELETE(-1, "删除");

    private int id;
    private String name;

    private StatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String getNameById(Integer id) {
        if (id != null) {
            for (StatusEnum t : StatusEnum.values()) {
                if (t.getId() == id) {
                    return t.getName();
                }
            }
        }
        return "";
    }

    public static StatusEnum toEnum(Integer id) {
        StatusEnum ret = null;
        if (id != null) {
            for (StatusEnum t : StatusEnum.values()) {
                if (t.getId() == id) {
                    ret = t;
                    break;
                }
            }
        }
        return ret;
    }
}
