package com.smart.service;

import com.smart.dao.*;
import com.smart.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
     * add a new board
     *
     */
    public void addBoard(Board board) {
        boardDao.save(board);
    }

    /**
     * remove a board
     */
    public void removeBoard(int boardId){
        Board board = boardDao.get(boardId);
        boardDao.remove(board);
    }

    /**
     * 根据boardId获取Board对象
     *
     * @param boardId
     */
    public Board getBoardById(int boardId) {
        return this.boardDao.get(boardId);
    }

    /**
     * load all the boards
     *
     */
    public List<Board> getAllBoard(){
        return  this.boardDao.loadAll();
    }

    /**
     * add a topic,add a mainpost,the credits of the user +10,the topic number of the board +1
     */
    public void addTopic(Topic topic){
        //the topic number of the board +1
        Board board = this.boardDao.get(topic.getBoardId());
        board.setTopicNum(board.getTopicNum()+1);

        //add a mainpost
        MainPost mainPost = topic.getMainPost();
        mainPost.setCreateTime(new Date());
        mainPost.setUser(topic.getUser());
        mainPost.setPostTitle(topic.getTopicTitle());
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
     * 更改主题帖
     * @param topic
     */
    public void updateTopic(Topic topic){
        topicDao.update(topic);
    }

    /**
     *remove the topic
     */
    public void removeTopic(int topicId) {
        Topic topic = this.topicDao.get(topicId);

        // 将论坛版块的主题帖数减1
        Board board = boardDao.get(topic.getBoardId());
        board.setTopicNum(board.getTopicNum() - 1);

        // 发表该主题帖用户扣除50积分
        User user = topic.getUser();
        user.setCredit(user.getCredit() - 50);

        // 删除主题帖及其关联的帖子
        this.topicDao.remove(topic);
        this.postDao.deleteTopicPosts(topicId);
    }

    /**
     * make digest topic
     */
    public void makeDigestTopic(int topicId){
        Topic topic = this.topicDao.get(topicId);
        topic.setDigest(Topic.DIGEST_TOPIC);
        User user = topic.getUser();
        user.setCredit(user.getCredit() + 100);
    }

    /**
     * get the paged topidcs of a board,order by reply time desc
     */
    public Page getPagedTopic(int boardId,int pageNo,int pageSize){
        return this.topicDao.getPagedTopics(boardId,pageNo,pageSize);
    }

    /**
     * 查找出所有包括标题包含title的主题帖
     *
     * @param title
     *            标题查询条件
     * @return 标题包含title的主题帖
     */
    public Page queryTopicByTitle(String title,int pageNo,int pageSize) {
        return this.topicDao.queryTopicByTitle(title,pageNo,pageSize);
    }
    /**
     * 根据topicId获取Topic对象
     * @param topicId
     * @return Topic
     */
    public Topic getTopicByTopicId(int topicId) {
        return topicDao.get(topicId);
    }


    /**
     * add a post:the credit of the user +5;the post number of the topic +1
     * and refresh the last post time
     */
    public void addPost(Post post){
        //add the post
        this.postDao.save(post);
        //the credit of the user +5
        User user = post.getUser();
        user.setCredit(user.getCredit() + 5);
        this.userDao.update(user);
        //the post number of the topic +1 and refresh the last post time
        Topic topic = this.topicDao.get(post.getTopic());
        topic.setTopicReplies(topic.getTopicReplies() + 1);
        topic.setLastPost(new Date());
        this.topicDao.update(topic);
    }

    /**
     * remove a post:the credit of the user -20;the replies of the topic -1
     */
    public void removePost(int postId){
        //remove a post
        Post post = this.postDao.get(postId);
        this.postDao.remove(post);
        //the credit of the user -20
        User user = post.getUser();
        user.setCredit(user.getCredit() - 20);
        this.userDao.update(user);
        //the replies of the topic -1
        Topic topic  = this.topicDao.get(post.getTopic());
        topic.setTopicReplies(topic.getTopicReplies() - 1);
        this.topicDao.update(topic);
    }

    /**
     * 更改回复帖子的内容
     * @param post
     */
    public void updatePost(Post post){
        postDao.update(post);
    }


    /**
     * get paged posts
     */
    public Page getPagedPosts(int topicId,int pageNo,int pageSize){
        return this.postDao.getPagedPosts(topicId, pageNo, pageSize);
    }


    /**
     * 获取回复帖子的对象
     * @param postId
     * @return 回复帖子的对象
     */
    public Post getPostByPostId(int postId){
        return postDao.get(postId);
    }


    /**
     * set the manager of the board
     */
    public void addBoardManager(int boardId,String userName){
        User user = this.userDao.getUserByUserName(userName);
        if(user == null){
            throw new RuntimeException("user not existed");
        }else{
           Board board = this.boardDao.get(boardId);
           user.getManBoards().add(board);
           this.userDao.update(user);
        }
    }





}
