package com.smart.service;

import com.smart.dao.UserDao;

import com.smart.domain.User;
import com.smart.exception.UserExistException;
import com.sun.javaws.JnlpxArgs;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//import static org.mockito.Mockito.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
public class UserServiceTest extends BaseServiceTest{
    private UserDao userDao;
    private UserService userService;

    @BeforeClass
    public void init(){
        this.userDao = mock(UserDao.class);
        this.userService =  new UserService();
        ReflectionTestUtils.setField(this.userService,"userDao",this.userDao);
    }


    @Test
    public void register() throws UserExistException {
        final User user = new User();
        user.setUserName("william");
        user.setPassword("123456");

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
               Object[] args = invocationOnMock.getArguments();
               User  userMock = (User)args[0];
               if(userMock !=  null){
                   userMock.setUserId(1);
               }
               return null;
            }
        }).when(this.userDao).save(user);
        userService.register(user);
        assertEquals((int)user.getUserId(),1);
        Mockito.verify(this.userDao,Mockito.times(1)).save(user);
    }

    /**
     * 测试根据用户名模糊查询用户列表
     */
    public void getUserByUserName(){
        //prepare the test context
        User user = new User();
        user.setUserName("tom");
        user.setPassword("123456");
        user.setCredit(100);
        doReturn(user).when(this.userDao).getUserByUserName("tom");

        //lauch the test
        User u = userService.getUserByUserName("tom");
        //assert the result
        assertNotNull(u);
        assertEquals(((User) u).getUserName(),user.getUserName());
        verify(userDao,times(1)).getUserByUserName("tom");
    }

    /**
     * 测试锁定用户的服务方法
     */
    @Test
    public void lockUser() {
        User user = new User();
        user.setUserName("tom");
        user.setPassword("1234");
        doReturn(user).when(userDao).getUserByUserName("tom");
        doNothing().when(userDao).update(user);

        userService.lockUser("tom");
        User u = userService.getUserByUserName("tom");

        assertEquals(User.USER_LOCK, u.getLocked());
    }

    @Test
    public void unlockUser() {

        User user = new User();
        user.setUserName("tom");
        user.setPassword("1234");
        user.setLocked(User.USER_LOCK);
        doReturn(user).when(userDao).getUserByUserName("tom");
        doNothing().when(userDao).update(user);

        userService.unlockUser("tom");
        User u = userService.getUserByUserName("tom");
        assertEquals(User.USER_UNLOCK, u.getLocked());
    }





}
