package com.sample;


public class TBoard {

  private long boardId;
  private String boardName;
  private String boardDesc;
  private long topicNum;


  public long getBoardId() {
    return boardId;
  }

  public void setBoardId(long boardId) {
    this.boardId = boardId;
  }


  public String getBoardName() {
    return boardName;
  }

  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }


  public String getBoardDesc() {
    return boardDesc;
  }

  public void setBoardDesc(String boardDesc) {
    this.boardDesc = boardDesc;
  }


  public long getTopicNum() {
    return topicNum;
  }

  public void setTopicNum(long topicNum) {
    this.topicNum = topicNum;
  }

}
