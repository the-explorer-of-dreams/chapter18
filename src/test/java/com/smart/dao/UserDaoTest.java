package com.smart.dao;

import com.smart.domain.User;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

public class UserDaoTest extends BaseDaoTest {
    @SpringBean("userDao")
    UserDao userDao;

    /**
     *测试按用户名查找用户
     */

    @Test
    @DataSet("XiaoChun.Users.xls")
    public void findUserByUserName(){
        User user = userDao.getUserByUserName("tony");
        Assert.assertNull(user,"user not existed");

        user = userDao.getUserByUserName("jan");
        Assert.assertNotNull(user);
        Assert.assertEquals("jan",user.getUserName());
        Assert.assertEquals("123456",user.getPassword());
        Assert.assertEquals(10,user.getCredit());

    }

    /**
     * 验证数据库保存的正确性
     */
    @Test
    @DataSet("XiaoChun.SaveUser.xls")
    @ExpectedDataSet("XiaoChun.ExpectedSaveUser.xls")// 准备验证数据
    public void saveUser() throws Exception{
        User u = XlsDataSetBeanFactory.createBean(this.getClass(), "XiaoChun.SaveUser.xls", "t_user", User.class);
        userDao.update(u);
    }

    @Test
    public void saveUsers() throws Exception{
        List<User> users = XlsDataSetBeanFactory.createBeans(this.getClass(),"XiaoChun.SaveUsers.xls","t_user",User.class);
        for (User u : users) {
            userDao.save(u);
        }
    }


}
