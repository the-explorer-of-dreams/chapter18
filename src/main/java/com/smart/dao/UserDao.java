package com.smart.dao;

import com.smart.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User DAO类
 */
@Repository
public class UserDao extends BaseDao<User>{
    public static final String GET_USER_BY_NAME = "from User u where u.userName = ?";
    public static final String QUERY_USER_BY_USERNAME = "from User u where u.userName like ?";

    /**
     *按指定用户名查找USER
     * @param userName 用户名查询条件
     * @return user
     */
    public User getUserByUserName(String userName){
        List<User> usersList = (List<User>)getHibernateTemplate().find(GET_USER_BY_NAME,userName);
        if( usersList == null || ((List) usersList).size() == 0){
            return null;
        }else{
            return usersList.get(0);
        }
    }

    /**
     * 指定用户名进行模糊查询
     * @param userName 用户名查询条件
     * @return user
     */
    public List<User> queryUserByUserName(String userName){
        List<User> usersList = (List<User>)getHibernateTemplate().find(QUERY_USER_BY_USERNAME,userName+"%");
        return usersList;
    }
}
