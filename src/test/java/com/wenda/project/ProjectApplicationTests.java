package com.wenda.project;

import com.wenda.project.entity.TbUser;
import com.wenda.project.framework.web.base.dao.CommonDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ProjectApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testEntitySelectList(){
        CommonDao<TbUser> userDao = new CommonDao(TbUser.class);
        List<TbUser> userList = userDao.findList();
        System.out.println(userList);
    }

    @Test
    void testSelectList(){
        CommonDao<TbUser> userDao = new CommonDao(TbUser.class);
        List<TbUser> userList = userDao.findList("select * from tb_account where user_name like '2%'",null,TbUser.class);
        System.out.println(userList);
    }

    @Test
    void testSelectListByCondition(){
        CommonDao<TbUser> userDao = new CommonDao(TbUser.class);
        List<TbUser> userList = userDao.findList(new String[]{"email = ?","status = ?"},new Object[]{"251121753@QQ.COM",2000});
        System.out.println(userList);
    }

    @Test
    void testEntityAdd() throws Exception {
        CommonDao<TbUser> userDao = new CommonDao(TbUser.class);
        TbUser account = new TbUser();
        account.setId("111222");
        account.setUserName("testAdd");
        userDao.add(account);
    }

    @Test
    void testEntityUpdate() throws Exception {
        CommonDao<TbUser> userDao = new CommonDao(TbUser.class);
        TbUser account = new TbUser();
        account.setId("111222");
        account.setUserName("testAdd22");
        account.setStatus(2000);
        account.setModifyTime(new Date());
        userDao.update(account);
    }
}
