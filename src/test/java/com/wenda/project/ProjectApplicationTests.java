package com.wenda.project;

import com.wenda.project.entity.TbAccount;
import com.wenda.project.framework.web.base.dao.CommonDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProjectApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void testEntitySelectList(){
        CommonDao<TbAccount> userDao = new CommonDao(TbAccount.class);
        List<TbAccount> userList = userDao.findList();
        System.out.println(userList);
    }

    @Test
    void testEntityAdd() throws Exception {
        CommonDao<TbAccount> userDao = new CommonDao(TbAccount.class);
        TbAccount account = new TbAccount();
        account.setId("111222");
        account.setUserName("testAdd");
        userDao.add(account);
    }


}
