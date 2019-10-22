package com.wenda.project.framework.web.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenda.project.entity.TbUser;
import com.wenda.project.framework.web.StringPool;
import com.wenda.project.framework.web.base.ResponseInfo;
import com.wenda.project.framework.web.base.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("sys")
public class UserController extends BaseController {

    @Autowired
    private ObjectMapper json;

    @Autowired
    private UserServer userServer;

    @RequestMapping("login")
    public String loginIndex() {
        return "user";
    }

    @RequestMapping("loginForm")
    public String loginForm(String userName, String password, Model model, HttpSession session) throws Exception {
        model.addAttribute("userName", userName);
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            model.addAttribute("errMsg", "用户名或者密码不能为空");
            return loginIndex();
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            subject.login(token);
        } catch (Exception e) {
            model.addAttribute("errMsg", e.getMessage());
            return loginIndex();
        }
        session.setAttribute(StringPool.USER_KEY, userServer.login(userName));
        return "redirect:/sys/index";
    }

    @RequestMapping("exist")
    public String exist() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null) {
                subject.logout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user";
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping("changePwd")
    @ResponseBody
    public ResponseInfo changePwd(PwdVo pwdVo, HttpSession session, Model model) {
        model.addAttribute("pwdVo", pwdVo);
        try {
            LoginVo loginVo = (LoginVo) session.getAttribute("userKey");
            userServer.changePwd(pwdVo, loginVo.getName());
        } catch (Exception e) {
            return new ResponseInfo(100, e.getMessage(), null);
        }
        return new ResponseInfo(0, null, null);
    }

    @RequestMapping("index")
    public String index(UserQueryVo query, Model model) {
        model.addAttribute("query", query);
        model.addAttribute("list",userServer.findList(query));
        return "index";
    }


    @RequestMapping("userAddIndex")
    @RequiresPermissions("/sys/user/add")
    public String addIndex(Model model) {
        return "/sys/user/add";
    }

    @RequestMapping("userEditIndex")
    @RequiresPermissions("/sys/user/edit")
    public String editIndex(String id, Model model) throws Exception {
        TbUser obj = userServer.findById(id);
        if (obj == null) {
            throw new Exception("请勿非法操作");
        }
        model.addAttribute("obj", obj);
        return "/sys/user/edit";
    }

    @RequestMapping("userAdd")
    @RequiresPermissions("/sys/user/add")
    public String add(TbUser obj, Model model) throws Exception {
        if (obj == null || StringUtils.isBlank(obj.getUserName()) || StringUtils.isBlank(obj.getEmail())) {
            throw new Exception("请勿非法操作");
        }
        try {
            obj = userServer.add(obj);
        } catch (Exception e) {
            model.addAttribute("obj", obj);
            failMsg(model, e.getMessage());
            return addIndex(model);
        }
        successMsg(model, "用户【" + obj.getUserName() + "】注册成功,用户账号和初始化密码已经成功通知到用户邮箱【" + obj.getEmail() + "】");
        return index(new UserQueryVo(), model);
    }

    @RequestMapping("userEdit")
    @RequiresPermissions("/sys/user/edit")
    public String edit(TbUser obj, Model model) throws Exception {
        if (obj == null) {
            throw new Exception("请勿非法操作");
        }
        try {
            userServer.edit(obj);
        } catch (Exception e) {
            model.addAttribute("name", obj.getUserName());
            failMsg(model, e.getMessage());
            return editIndex(null, model);
        }
        model.addAttribute("obj", obj);
        successMsg(model, "操作成功");
        return index(new UserQueryVo(),model);
    }

    @RequestMapping("userDelete")
    @RequiresPermissions("/sys/user/delete")
    public String delete(String id, Model model) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new Exception("请勿非法操作");
        }
        userServer.delete(id);
        successMsg(model, "删除成功");
        return index(new UserQueryVo(),  model);
    }

    /**
     * 重置用户密码密码
     *
     * @return
     */
    @RequestMapping("userResetPwd")
    @RequiresPermissions("/sys/user/resetPwd")
    public String resetPwd(String id, Model model) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new Exception("请勿非法操作");
        }
        try {
            TbUser account = userServer.initPwd(id);
            successMsg(model, "用户【" + account.getUserName() + "】的密码重置成功,重置密码已经成功通知到用户邮箱【" + account.getEmail() + "】");
        } catch (Exception e) {
            failMsg(model, "重置密码失败，详情：" + e.toString());
        }
        return index(new UserQueryVo(), model);
    }
}
