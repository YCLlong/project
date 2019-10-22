package com.wenda.project.framework.web.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginVo {
    private String id;
    private String name;
    private Integer type;
    private String email;
}
