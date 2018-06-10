package com.smart.dao;

import org.springframework.stereotype.Repository;

/**
 * Post DAO类
 */
@Repository
public class PostDao extends BaseDao<Post>{
    private static final String GET_PAGED_POSTS = "from Post where topic.topicId =? order by createTime desc";

    private static final String DELETE_TOPIC_POSTS = "delete from Post where topic.topicId=?";

    /**
     * 获取指定TOPIC的所有回复
     * @params topicId 主题ID
     */
    public Page getPagedPosts(int topicId,int pageNo,int pageSize){
        return pagedQuery(GET_PAGED_POSTS,pageNo,pageSize,topicId);
    }

    /**
     * 删除指定主题下所有帖子
     */
    public void deleteTopicPosts(int topicId){
        getHibernateTemplate().bulkUpdate(DELETE_TOPIC_POSTS, topicId);
    }
}
