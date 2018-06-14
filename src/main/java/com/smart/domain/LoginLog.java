package com.smart.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="t_login_log")
public class LoginLog extends BaseDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="login_datetime")
    private Integer loginLogId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Integer userId;

    @Column(name = "ip")
    private String ip;

    @Column(name="login_datetime")
    private String loginDateTime;

    public Integer getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(Integer loginLogId) {
        this.loginLogId = loginLogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(String loginDateTime) {
        this.loginDateTime = loginDateTime;
    }
}
