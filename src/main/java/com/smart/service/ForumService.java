package com.smart.service;

import com.smart.dao.BoardDao;
import com.smart.dao.PostDao;
import com.smart.dao.TopicDao;
import com.smart.dao.UserDao;
import com.smart.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumService {
    private BoardDao boardDao;
    private TopicDao topicDao;
    private PostDao postDao;
    private UserDao userDao;

    @Autowired
    public void setBoardDao(BoardDao boardDao) {
        this.boardDao = boardDao;
    }
    @Autowired
    public void setTopicDao(TopicDao topicDao) {
        this.topicDao = topicDao;
    }
    @Autowired
    public void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * add a new board
     */
    public void addBoard(Board board){
        this.boardDao.save(board);
    }

    /**
     * remove a board
     */
    public void removeBoard(Board board){
        this.boardDao.remove(board);
    }

    /**
     * add a topic
     */
    public void addTopic(Topic topic){
        //update board
        Board board = (Board)boardDao.get(topic.getBoardId());
        board.setTopicNum(1 + board.getTopicNum());
        this.topicDao.save(topic);

        //if mainPost is initiated
        //add mainpost
        MainPost mainPost = topic.getMainPost();
        mainPost.setTopic(topic);
        mainPost.setUser(topic.getUser());
        mainPost.setPostTitle(topic.getTopicTitle());
        mainPost.setBoardId(topic.getBoardId());
        this.postDao.save(mainPost);

        //update user
        User user = topic.getUser();
        user.setCredit(user.getCredit() + 10);
        this.userDao.update(user);
    }

    /**
     * remove a topic
     */
    public void removeTopic(int topicId){
        Topic topic = this.topicDao.get(topicId);

        //update board
        int boardId = topic.getBoardId();
        Board board = this.boardDao.get(boardId);
        board.setTopicNum(board.getTopicNum() - 1);
        //update user
        User user = topic.getUser();
        user.setCredit(user.getCredit() - 50);
        //remove topic and post
        this.topicDao.remove(topic);
        this.postDao.deleteTopicPosts(topicId);
    }

    /**
     * add a post
     */
    public void addPost(Post post){
        this.postDao.save(post);
    }

}
