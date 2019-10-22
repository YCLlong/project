package com.wenda.project.framework.web.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 统一异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String Exception(Exception ex, Model model) {
        logger.error("出错啦", ex);
        model.addAttribute("errMsg", ex.getMessage());
        return "/error";
    }

    public void successMsg(Model model, String msg) {
        model.addAttribute("successMsg", msg);
    }

    public void failMsg(Model model, String msg) {
        model.addAttribute("failMsg", msg);
    }
}
