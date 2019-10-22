package com.wenda.project.framework.web.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PwdVo {
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;
    private String userName;
}
