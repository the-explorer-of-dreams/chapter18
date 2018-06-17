package com.smart.service;

import com.smart.dao.LoginLogDao;
import com.smart.dao.UserDao;
import com.smart.domain.LoginLog;
import com.smart.domain.User;
import com.smart.exception.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private UserDao userDao;
    private LoginLogDao loginLogDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }

    /**
     * register a new user
     */
    public void register(User user) throws UserExistException{
        User u = this.userDao.getUserByUserName(user.getUserName());
        if(u != null){
            throw new UserExistException("用户名已存在");
        }else{
            user.setCredit(100);
            user.setUserType(1);
            this.userDao.save(user);
        }
    }

    /**
     * update a user
     */
    public void update(User user){
        this.userDao.update(user);
    }


    /**
     * lock a user
     */
    public void lockUser(String userName){
        User user = userDao.getUserByUserName(userName);
        user.setLocked(User.USER_LOCK);
        this.userDao.update(user);
    }

    /**
     * unlock a user
     */
    public void unlockUser(String userName){
        User user = userDao.getUserByUserName(userName);
        user.setLocked(User.USER_UNLOCK);
        this.userDao.update(user);
    }

    /**
     *get a user by username
     */
    public User getUserByUserName(String userName){
        return this.userDao.getUserByUserName(userName);
    }

    /**
     * get a user by userid
     */
    public User getUserByUserId(Integer userId){
        return this.userDao.get(userId);
    }

    /**
     * get users by username
     */
    public List<User> queryUserByUserName(String userName){
        return this.userDao.queryUserByUserName(userName);
    }

    /**
     * get all users
     */
    public List<User> getAllUsers(){
        return this.userDao.loadAll();
    }

    /**
     * user login succeed
     */
    public void loginSuccess(User user){
        user.setCredit(5 + user.getCredit());
        LoginLog loginLog = new LoginLog();
        loginLog.setUser(user);
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDateTime(new Date());
        this.userDao.update(user);
        this.loginLogDao.save(loginLog);
    }



}
