package com.wenda.project.framework.web.user;

import com.wenda.project.entity.TbUser;
import com.wenda.project.framework.constant.StatusEnum;
import com.wenda.project.framework.constant.UserTypeEnum;
import com.wenda.project.framework.utils.CodeUtil;
import com.wenda.project.framework.web.StringPool;
import com.wenda.project.framework.web.base.dao.CommonDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServer {
    @Autowired
    private Environment environment;

    public void changePwd(PwdVo pwdVo, String userName) throws Exception {
        TbUser user = findByName(userName);
        if (user == null) {
            throw new Exception("用户不存在,无法修改");
        }
        if (!user.getPassword().equals(pwdVo.getOldPwd())) {
            throw new Exception("旧密码错误,修改失败");
        }
        user.setModifyTime(new Date());
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        dao.update(user);
    }

    public TbUser findByName(String userName) throws Exception {
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        return dao.find(new String[]{"user_name=?","status=?"},new Object[]{userName, StatusEnum.NORMAL.getId()});
    }

    public TbUser findById(String id) throws Exception {
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        return dao.find(new String[]{"id=?","status=?"},new Object[]{id, StatusEnum.NORMAL.getId()});
    }

    public LoginVo login(String userName) throws Exception {
        TbUser user = findByName(userName);
        if (user == null) {
            return null;
        }
        LoginVo vo = new LoginVo();
        vo.setId(user.getId());
        vo.setEmail(user.getEmail());
        vo.setName(user.getUserName());
        vo.setType(user.getUserType());
        return vo;
    }

    /**
     * 用户列表
     *
     * @param query
     * @return
     */
    public List<TbUser> findList(UserQueryVo query) {
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        Set<String> conditions = new HashSet<>();
        Set<Object> values  = new HashSet<>();
        if(query != null){
            if(StringUtils.isNotBlank(query.getName())){
                conditions.add("user_name like %" + query.getName() + "%");
            }
            if(StringUtils.isNotBlank(query.getEmail())){
                conditions.add("email like %" + query.getEmail() + "%");
            }
            if(StringUtils.isNotBlank(query.getMobile())){
                conditions.add("mobile = ?");
                values.add(query.getMobile());
            }
            if(StringUtils.isNotBlank(query.getVchat())){
                conditions.add("email like %" + query.getVchat() + "%");
            }
        }
        List<TbUser> dataList = dao.findList(conditions,values);
        return dataList;
    }


    @Transactional(rollbackFor = Exception.class)
    public TbUser add(TbUser user) throws Exception {
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        TbUser a = findByName(user.getUserName());
        if (a != null) {
            throw new Exception("添加失败，用户【" + a.getUserName() + "】已经存在");
        }
        String id = CodeUtil.genId();

        Date nowDate = new Date();
        user.setCreateTime(nowDate);
        user.setModifyTime(nowDate);
        user.setStatus(StatusEnum.NORMAL.getId());
        user.setUserType(UserTypeEnum.USER.getId());
        user.setPassword(StringPool.INIT_PWD);
        user.setId(id);
        dao.add(user);

        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(TbUser user) throws Exception {
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        dao.update(user);
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        dao.deleteById(id,false);
    }

    @Transactional(rollbackFor = Exception.class)
    public TbUser initPwd(String id) throws Exception {
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        TbUser user = new TbUser();
        user.setId(id);
        user.setPassword(StringPool.INIT_PWD);
        user.setModifyTime(new Date());

       try{
         return dao.update(user);
       }catch (Exception e){
           e.printStackTrace();
           throw new Exception("重置密码失败，该用户不存在");
       }
    }
}
