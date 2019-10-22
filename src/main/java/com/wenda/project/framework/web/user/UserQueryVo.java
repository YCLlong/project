package com.wenda.project.framework.web.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserQueryVo {
    private String id;
    private String name;
    private String mobile;
    private String email;
    private String vchat;
}
