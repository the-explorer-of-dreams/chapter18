package com.smart.service;

import com.smart.domain.Board;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
}
