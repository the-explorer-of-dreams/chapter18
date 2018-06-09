package com.smart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name="t_user")
public class User extends BaseDomain {
    /**
     *锁定用户对应的状态值
     */
    public static final int USER_LOCK = 1;
    /**
     * 用户解锁对应的状态值
     */
    public static final int USER_UNLOCK = 0;
    /**
     * 管理员类型
     */
    public static final int FORUM_ADMIN = 2;
    /**
     * 普通用户类型
     */
    public static final int NORMAL_USER = 1;

    @id
    private int userId;


    private String userName;


    private int userType = NORMAL_USER;


    private String lastIp;


    private Date lastVisit;

    private String password;

    private int locked ;

    private int credit;

    private Set<Board> manBoards = new HashSet<Board>();

}
