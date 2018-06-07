package com.sample;


public class TLoginLog {

  private long loginLogId;
  private long userId;
  private String ip;
  private String loginDatetime;


  public long getLoginLogId() {
    return loginLogId;
  }

  public void setLoginLogId(long loginLogId) {
    this.loginLogId = loginLogId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }


  public String getLoginDatetime() {
    return loginDatetime;
  }

  public void setLoginDatetime(String loginDatetime) {
    this.loginDatetime = loginDatetime;
  }

}
