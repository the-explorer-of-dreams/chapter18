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

//    @Test
//    public void register() throws UserExistException{
//        User user = new User();
//        user.setUserName("testwww");
//        user.setPassword("1234");
//
//        doAnswer(new Answer<User>() {
//            public User answer(InvocationOnMock invocation) {
//                Object[] args = invocation.getArguments();
//                User user = (User) args[0];
//                if (user != null) {
//                    user.setUserId(1);
//                }
//                return user;
//            }
//        }).when(userDao).save(user);
//
//        userService.register(user);
//        assertEquals((int)user.getUserId(), 1);
//        verify(userDao, times(1)).save(user);
//    }

    @Test
    public void register() throws UserExistException {
        final User user = new User();
        user.setUserName("william");
        user.setPassword("123456");

//        doAnswer(new Answer<User>() {
//            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
//               Object[] args = invocationOnMock.getArguments();
//               User  userMock = (User)args[0];
//               if(userMock !=  null){
//                   userMock.setUserId(1);
//               }
//               User userTemp = new User();
//               userTemp.setUserId(new Integer(100));
//                return userTemp;
//            }
//        }).when(this.userDao).save(user);
        doReturn(user).when(userDao).save(user);
        userService.register(user);
        assertEquals((int)user.getUserId(),1);
        Mockito.verify(this.userDao,Mockito.times(1)).save(user);
    }





}
