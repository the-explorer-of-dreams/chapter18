package com.smart.dao;

import com.smart.domain.Topic;
import com.smart.domain.User;
import com.smart.test.dataset.util.XlsDataSetBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import java.util.List;

public class TopicDaoTest extends BaseDaoTest {
    @SpringBean("topicDao")
    TopicDao topicDao;

    @Test
    @ExpectedDataSet("XiaoChun.ExpectedTopics.xls")
    public void save() throws Exception{
        List<Topic> topicList = XlsDataSetBeanFactory.createBeans(TopicDaoTest.class,"XiaoChun.SaveTopics.xls","t_topic",Topic.class);
        for (Topic topic : topicList) {
            User user = new User();
            user.setUserId(1);
            topic.setUser(user);
            topicDao.save(topic);

        }
//        topicDao.getHibernateTemplate().
//        System.out.println("===================");
//        Topic queryTopic = topicDao.get(1);
//        System.out.println("topic name:"+queryTopic.getTopicTitle());
    }

//    @Test
//    @DataSet("XiaoChun.SaveTopics.xls")
//    @ExpectedDataSet("XiaoChun.ExpectedTopics.xls")
//    public void dataSetSave(){
//
//    }
}
