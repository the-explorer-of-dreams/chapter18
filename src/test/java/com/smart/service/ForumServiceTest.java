package com.smart.service;

import com.smart.domain.*;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.Map;

public class ForumServiceTest extends BaseServiceTest{
    @SpringBean("userService")
    private UserService userService;
    @SpringBean("forumService")
    private ForumService forumService;

    /**
     * 清空二级缓存
     */
    @BeforeMethod
    public void init(){
        SessionFactory sessionFactory = super.hibernateTemplate.getSessionFactory();
        Map<String ,CollectionMetadata> roleMap = sessionFactory.getAllCollectionMetadata();
        for (String roleName:roleMap.keySet()) {
            sessionFactory.evictCollection(roleName);
        }
        Map<String ,ClassMetadata> entityMap = sessionFactory.getAllClassMetadata();
        for (String entityName:entityMap.keySet()) {
            sessionFactory.evictEntity(entityName);
        }
        sessionFactory.evictQueries();
    }

    /**
     * 新增一般版块
     */
    @Test
    public void addBoard() throws Exception{
        Board board = XlsDataSetBeanFactory.createBean(this.getClass(),"XiaoChun.DataSet.xls","t_board",Board.class);
        forumService.addBoard(board);

        Board boardFromDb = forumService.getBoardById(board.getBoardId());
        Assert.assertEquals(boardFromDb.getBoardName(),"育儿");
    }

    /**
     * add a mew topic
     */
    @Test
//    @DataSet("XiaoChun.DataSet.xls")
    public void addTopic() throws Exception{
        Topic topic = XlsDataSetBeanFactory.createBean(this.getClass(),"XiaoChun.DataSet.xls","t_topic",Topic.class);
        User user = XlsDataSetBeanFactory.createBean(this.getClass(),"XiaoChun.DataSet.xls","t_user",User.class);
        MainPost mainPost = XlsDataSetBeanFactory.createBean(this.getClass(),"XiaoChun.DataSet.xls","t_post",MainPost.class);

        topic.setUser(user);
        topic.setMainPost(mainPost);
        this.forumService.addTopic(topic);

        Board boardDb = this.forumService.getBoardById(topic.getBoardId());
        User userDb = this.userService.getUserByUserName("tom");
        Assert.assertEquals(boardDb.getTopicNum(),new Integer(1));
        Assert.assertEquals(userDb.getCredit(),110);
        Assert.assertEquals(topic.getTopicId() > 0,true);
    }

    /**
     * 测试添加回复帖子
     *
     */
    @Test
    @DataSet("XiaoChun.DataSet.xls")
    public void addPost() throws Exception {
        Post post = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
                "XiaoChun.DataSet.xls", "t_post", Post.class);
        User user = XlsDataSetBeanFactory.createBean(ForumServiceTest.class,
                "XiaoChun.DataSet.xls", "t_user", User.class);
        Topic topic = new Topic();
        topic.setTopicId(new Integer(1));
        post.setUser(user);
        post.setTopic(topic);
        forumService.addPost(post);

        User userDb = userService.getUserByUserName("tom");

        Topic topicDb = forumService.getTopicByTopicId(1);
        Assert.assertEquals(post.getPostId()>1, true);
        Assert.assertEquals(userDb.getCredit(), 105);
        Assert.assertEquals((int)topicDb.getTopicReplies(), 2);
    }

    /**
     * 测试删除回复帖子的方法
     */
    @Test
    @DataSet("XiaoChun.DataSet.xls")
    public void removePost()
    {
        forumService.removePost(1);

        Post postDb = forumService.getPostByPostId(1);
        User userDb = userService.getUserByUserName("tom");
        Topic topicDb = forumService.getTopicByTopicId(1);

        Assert.assertNull(postDb);
        Assert.assertEquals(userDb.getCredit(), 80);
        Assert.assertEquals((int)topicDb.getTopicReplies(), 0);
    }

    /**
     * 测试置精华主题帖的服务方法
     */
    @Test
    @DataSet("XiaoChun.DataSet.xls")
    public void makeDigestTopic()throws Exception
    {
        forumService.makeDigestTopic(1);
        User userDb = userService.getUserByUserName("tom");
        Topic topicDb = forumService.getTopicByTopicId(1);
        Assert.assertEquals(userDb.getCredit(), 200);
        Assert.assertEquals((int)topicDb.getDigest(), Topic.DIGEST_TOPIC);
    }

    @Test
    @DataSet("XiaoChun.DataSet.xls")
    public void addBoardManager(){
        forumService.addBoardManager(1,"tom");
        User userDb = userService.getUserByUserName("tom");
        Assert.assertEquals(userDb.getManBoards().size()>0, true);
    }


}
