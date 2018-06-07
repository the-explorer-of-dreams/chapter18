package com.sample;


public class TTopic {

  private long topicId;
  private long boardId;
  private String topicTitle;
  private long userId;
  private java.sql.Date createTime;
  private java.sql.Date lastPost;
  private long topicViews;
  private long topicReplies;
  private long digest;


  public long getTopicId() {
    return topicId;
  }

  public void setTopicId(long topicId) {
    this.topicId = topicId;
  }


  public long getBoardId() {
    return boardId;
  }

  public void setBoardId(long boardId) {
    this.boardId = boardId;
  }


  public String getTopicTitle() {
    return topicTitle;
  }

  public void setTopicTitle(String topicTitle) {
    this.topicTitle = topicTitle;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public java.sql.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Date createTime) {
    this.createTime = createTime;
  }


  public java.sql.Date getLastPost() {
    return lastPost;
  }

  public void setLastPost(java.sql.Date lastPost) {
    this.lastPost = lastPost;
  }


  public long getTopicViews() {
    return topicViews;
  }

  public void setTopicViews(long topicViews) {
    this.topicViews = topicViews;
  }


  public long getTopicReplies() {
    return topicReplies;
  }

  public void setTopicReplies(long topicReplies) {
    this.topicReplies = topicReplies;
  }


  public long getDigest() {
    return digest;
  }

  public void setDigest(long digest) {
    this.digest = digest;
  }

}
