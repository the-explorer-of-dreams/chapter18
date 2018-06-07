package com.sample;


public class TPost {

  private long postId;
  private long boardId;
  private long topicId;
  private long userId;
  private long postType;
  private String postTitle;
  private String postText;
  private java.sql.Date createTime;


  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }


  public long getBoardId() {
    return boardId;
  }

  public void setBoardId(long boardId) {
    this.boardId = boardId;
  }


  public long getTopicId() {
    return topicId;
  }

  public void setTopicId(long topicId) {
    this.topicId = topicId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getPostType() {
    return postType;
  }

  public void setPostType(long postType) {
    this.postType = postType;
  }


  public String getPostTitle() {
    return postTitle;
  }

  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }


  public String getPostText() {
    return postText;
  }

  public void setPostText(String postText) {
    this.postText = postText;
  }


  public java.sql.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Date createTime) {
    this.createTime = createTime;
  }

}
