package com.smart.service;

import com.smart.dao.BoardDao;
import com.smart.dao.PostDao;
import com.smart.dao.TopicDao;
import com.smart.dao.UserDao;
import com.smart.domain.Board;
import com.smart.domain.MainPost;
import com.smart.domain.Topic;
import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ForumService {
    private TopicDao topicDao;
    private UserDao userDao;
    private BoardDao boardDao;
    private PostDao postDao;

    @Autowired
    public void setTopicDao(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setBoardDao(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Autowired
    public void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }

    /**
     * add a topic,add a mainpost,the credits of the user +10,the topic number of the board +1
     */
    public void addTopic(Topic topic){
        //the topic number of the board +1
        Board board = this.boardDao.getBoardNum(topic.getBoardId());
        board.setTopicNum(board.getTopicNum()+1);

        //add a mainpost
        MainPost mainPost = topic.getMainPost();
        mainPost.setCreateTime(new Date());
        mainPost.setUser(topic.getUser());T
                mainPost.setPostTitle(topic.getopicTitle());
        mainPost.setBoardId(topic.getBoardId());
        this.postDao.save(mainPost);

        //the credits of the user +10
        User user = topic.getUser();
        user.setCredit(user.getCredit() + 10);
        this.userDao.update(user);

        //save the topic
        this.topicDao.save(topic);
    }

    /**
     *
     */
    /
}
