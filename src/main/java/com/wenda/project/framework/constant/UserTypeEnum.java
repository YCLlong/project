package com.wenda.project.framework.constant;


public enum  UserTypeEnum implements IConvertable{
    USER(10, "普通用户"),
    ADMIN(20, "超级管理员");

    private int id;
    private String name;

    private UserTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public static String getNameById(Integer id) {
        if (id != null) {
            for (UserTypeEnum t : UserTypeEnum.values()) {
                if (t.getId() == id) {
                    return t.getName();
                }
            }
        }
        return "";
    }

    public static UserTypeEnum toEnum(Integer id) {
        UserTypeEnum ret = null;
        if (id != null) {
            for (UserTypeEnum t : UserTypeEnum.values()) {
                if (t.getId() == id) {
                    ret = t;
                    break;
                }
            }
        }
        return ret;
    }
}
