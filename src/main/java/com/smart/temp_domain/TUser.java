package com.sample;


public class TUser {

  private long userId;
  private String userName;
  private String password;
  private long userType;
  private long locked;
  private long credit;
  private java.sql.Timestamp lastVisit;
  private String lastIp;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public long getUserType() {
    return userType;
  }

  public void setUserType(long userType) {
    this.userType = userType;
  }


  public long getLocked() {
    return locked;
  }

  public void setLocked(long locked) {
    this.locked = locked;
  }


  public long getCredit() {
    return credit;
  }

  public void setCredit(long credit) {
    this.credit = credit;
  }


  public java.sql.Timestamp getLastVisit() {
    return lastVisit;
  }

  public void setLastVisit(java.sql.Timestamp lastVisit) {
    this.lastVisit = lastVisit;
  }


  public String getLastIp() {
    return lastIp;
  }

  public void setLastIp(String lastIp) {
    this.lastIp = lastIp;
  }

}
